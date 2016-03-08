/*
 *    Copyright (c) 2016, lyao. lomoliger@hotmail.com
 *
 *     Part of the code from the open source community,
 *     thanks stackOverflow & gitHub .
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.hadlink.lay_s.presenter;

import android.os.Bundle;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.MyNet;
import com.hadlink.lay_s.datamanager.net.netcallback.MyNetCallBack;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.delegate.ImageListDelegate;
import com.hadlink.library.base.BaseFragment;

import rx.Observable;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListFrgPresenter extends BaseFragment<ImageListDelegate> {

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

        this.refresh = refresh;

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
