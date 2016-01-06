package com.hadlink.lay_s.presenter;

import android.view.MenuItem;
import android.widget.Toast;

import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.MyNet;
import com.hadlink.lay_s.datamanager.net.netcallback.MyNetCallBack;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.delegate.CommonRVDelegate;
import com.hadlink.lay_s.model.Event;
import com.hadlink.library.base.BaseActivity;
import com.hadlink.library.util.rx.RxBus;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainAty extends BaseActivity<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
        initRxbus();
    }

    private void initRxbus() {
        RxBus.getDefault().take(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        Toast.makeText(mContext, event.getAction(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void requestList(boolean refresh) {
        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList("美女", 1);
        imageList
                .compose(this.bindToLifecycle())
                .compose(applySchedulers())
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>() {
                    @Override public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        List<ImageDetail> result = imageDetailImageListResponse.getResult();
                        Toast.makeText(mContext, "ok", Toast.LENGTH_SHORT).show();
                    }
                });

        /*Observable<FreshEventResponse<FreshEvent>> freshList = MyNet.get().getFreshList(1);
        NetUtils.getMainThreadObservable(freshList)
                .subscribe(new MyNetCallBack<FreshEventResponse<FreshEvent>>() {
                    @Override public void onSuccess(FreshEventResponse<FreshEvent> freshEventFreshEventResponse) {
                        List<FreshEvent> result = freshEventFreshEventResponse.getResult();
                    }
                });*/

    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        Event event = new Event();
        event.setAction(item.getTitle().toString());
        RxBus.getDefault().post(event);
        return true;
    }
}
