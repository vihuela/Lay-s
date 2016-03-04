package com.hadlink.lay_s.presenter;

import android.os.Bundle;

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
    String currentPaperTag;


    @Override protected Class<ImageListDelegate> getDelegateClass() {
        return ImageListDelegate.class;
    }

    @Override protected void onArguments(Bundle arguments) {
        currentPaperTag = arguments.getString("title");
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(new ImageListDelegate.LoadingCallBack() {
            @Override public void onRefresh() {
                requestList(true);
            }

            @Override public void onLoadMore() {
                requestList(false);
            }

            @Override public void onPrepare() {

            }
        });
    }

    @Override protected void onFirstUserVisible() {
        requestList(true);
    }

    private void requestList(boolean refresh) {

        Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList(currentPaperTag, viewDelegate.getCurrentPageNum(refresh));
        imageList
                .compose(this.bindToLifecycle())
                .compose(NetUtils.applySchedulers())
                .subscribe(new MyNetCallBack<ImageListResponse<ImageDetail>>() {
                    @Override public void onSuccess(ImageListResponse<ImageDetail> imageDetailImageListResponse) {
                        viewDelegate.setDatas(refresh, imageDetailImageListResponse);
                    }

                    @Override public void onDispatchError(Error error, Object message) {
                        super.onDispatchError(error, message);
                        viewDelegate.setError(refresh);
                    }
                });

    }
}
