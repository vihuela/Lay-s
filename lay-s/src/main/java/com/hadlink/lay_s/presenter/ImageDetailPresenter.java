package com.hadlink.lay_s.presenter;

import android.os.Bundle;

import com.hadlink.lay_s.delegate.ImageDetailDelegate;
import com.hadlink.library.base.BaseActivity;

/**
 * @author Created by lyao on 2016/3/7.
 */
public class ImageDetailPresenter extends BaseActivity<ImageDetailDelegate> {
    public static final String INTENT_IMAGE_URL_TAG = "INTENT_IMAGE_URL_TAG";
    public static final String INTENT_IMAGE_X_TAG = "INTENT_IMAGE_X_TAG";
    public static final String INTENT_IMAGE_Y_TAG = "INTENT_IMAGE_Y_TAG";
    public static final String INTENT_IMAGE_W_TAG = "INTENT_IMAGE_W_TAG";
    public static final String INTENT_IMAGE_H_TAG = "INTENT_IMAGE_H_TAG";


    @Override protected boolean getToolbarAvailable() {
        return false;
    }

    @Override protected Class<ImageDetailDelegate> getDelegateClass() {
        return ImageDetailDelegate.class;
    }

    @Override protected void getBundleExtras(Bundle extras) {
        String mImageUrl = extras.getString(INTENT_IMAGE_URL_TAG);
        int mLocationX = extras.getInt(INTENT_IMAGE_X_TAG);
        int mLocationY = extras.getInt(INTENT_IMAGE_Y_TAG);
        int mWidth = extras.getInt(INTENT_IMAGE_W_TAG);
        int mHeight = extras.getInt(INTENT_IMAGE_H_TAG);

        varyViewHelper.showLoadingView();
        viewDelegate.setOriginalInfo(mImageUrl, mWidth, mHeight, mLocationX, mLocationY, new Runnable() {
            @Override public void run() {
                //点击图片时
                finish();
            }
        },new Runnable(){
            @Override public void run() {
                varyViewHelper.showDataView();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (viewDelegate.isLoadSuccess())
            viewDelegate.transformOut();
        else
            finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

}
