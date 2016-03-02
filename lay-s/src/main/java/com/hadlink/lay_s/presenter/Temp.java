package com.hadlink.lay_s.presenter;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hadlink.easynet.util.NetUtils;
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

public class Temp extends BaseActivity<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
        initRxBus();
    }

    private void initRxBus() {
        RxBus.getDefault().take(Event.class)
                .subscribe(event -> {
                    Toast.makeText(context, event.getAction(), Toast.LENGTH_SHORT).show();
                });
    }

    private void requestList(boolean refresh) {
        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList("美女", 1);
        imageList
                .compose(this.bindToLifecycle())
                .compose(NetUtils.applySchedulers())
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>() {
                    @Override public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        List<ImageDetail> result = imageDetailImageListResponse.getResult();
                        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onDispatchError(Error error, Object message) {
                        super.onDispatchError(error, message);
                    }
                });

    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }

    @Override protected void onUseIconifySetMenuItem(Menu menu) {
       /* setMenuItem(MaterialIcons.md_face, R.color.white, menu.findItem(R.id.xiao));
        setMenuItem(FontAwesomeIcons.fa_share, R.color.white, menu.findItem(R.id.ming));
        setMenuItem(SimpleLineIconsIcons.icon_arrow_right, R.color.white, menu.findItem(R.id.ge));*/
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {

        Event event = new Event();
        event.setAction(item.getTitle().toString());
        RxBus.getDefault().post(event);
        return true;
    }
}
