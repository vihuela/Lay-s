package com.hadlink.lay_s.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.hadlink.lay_s.delegate.ImageListDelegate;
import com.hadlink.library.base.presenter.FragmentPresenter;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListFrgPresenter extends FragmentPresenter<ImageListDelegate> {
    String currentPaperTag;


    @Override protected Class<ImageListDelegate> getDelegateClass() {
        return ImageListDelegate.class;
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        if (arguments != null) {
            currentPaperTag = arguments.getString("title");
        }
    }

    @Override protected void onFirstUserVisible() {
    }

    @Override protected void onUserVisible() {
    }

    @Override protected void onUserInvisible() {
    }

    private void requestList(boolean refresh) {
        /*Observable<ImageListResponse<ImageDetail>> imageList = MyNet.get().getImageList("美女", 1);
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
                });*/

    }
}
