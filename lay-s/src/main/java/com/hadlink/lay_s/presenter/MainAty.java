package com.hadlink.lay_s.presenter;

import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.datamanager.bean.FreshEvent;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.MyNet;
import com.hadlink.lay_s.datamanager.net.netcallback.MyNetCallBack;
import com.hadlink.lay_s.datamanager.net.response.FreshEventResponse;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.delegate.CommonRVDelegate;
import com.hadlink.library.base.presenter.ActivityPresenter;

import java.util.List;

import rx.Observable;

public class MainAty extends ActivityPresenter<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
    }

    private void requestList(boolean refresh) {
        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList("美女", 1);
        NetUtils.getMainThreadObservable(imageList)
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>() {
                    @Override public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        List<ImageDetail> result = imageDetailImageListResponse.getResult();
                    }
                });
        Observable<FreshEventResponse<FreshEvent>> freshList = MyNet.get().getFreshList(1);
        NetUtils.getMainThreadObservable(freshList)
                .subscribe(new MyNetCallBack<FreshEventResponse<FreshEvent>>() {
                    @Override public void onSuccess(FreshEventResponse<FreshEvent> freshEventFreshEventResponse) {
                        List<FreshEvent> result = freshEventFreshEventResponse.getResult();
                    }
                });

    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }


}
