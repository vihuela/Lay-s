package com.hadlink.lay_s.presenter;

import android.os.Bundle;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.MyNet;
import com.hadlink.lay_s.datamanager.net.netcallback.MyNetCallBack;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.delegate.ImageListDelegate;
import com.hadlink.library.base.presenter.FragmentPresenter;

import rx.Observable;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListFrgPresenter extends FragmentPresenter<ImageListDelegate> {

    boolean refresh;

    @Override protected boolean bindBus() {
        return true;
    }

    @Override protected void onNetError(ErrorInfo obj) {
        super.onNetError(obj);
        viewDelegate.setError(this.refresh);
        varyViewHelper.showErrorView();
    }

    @Override protected Class<ImageListDelegate> getDelegateClass() {
        return ImageListDelegate.class;
    }

    @Override protected void onArguments(Bundle arguments) {

        netTag = arguments.getString("title");
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(new ImageListDelegate.LoadingCallBack() {
            @Override public void onRefresh() {
                requestList(true);
            }

            @Override public void onLoadMore() {
                requestList(false);
            }
        });
    }

    @Override protected void onRetryListener() {
        loadData();
    }

    @Override protected void onFirstUserVisible() {
        loadData();
    }

    private void loadData() {
        varyViewHelper.showLoadingView();
        requestList(true);
    }

    private void requestList(boolean refresh) {

        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList(netTag, viewDelegate.getCurrentPageNum(refresh));
        imageList
                .compose(this.bindToLifecycle())
                .compose(NetUtils.applySchedulers())
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>(netTag) {
                    @Override public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        viewDelegate.setDatas(ImageListFrgPresenter.this.refresh, imageDetailImageListResponse);
                        varyViewHelper.showDataView();
                    }
                });

    }
}
