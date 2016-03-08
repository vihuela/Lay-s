package com.hadlink.lay_s.delegate;

import android.view.View;

import com.hadlink.lay_s.R;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.SmoothImageView;
import com.liulishuo.qiniuimageloader.utils.PicassoLoader;
import com.squareup.picasso.Callback;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Created by lyao on 2016/3/7.
 */
public class ImageDetailDelegate extends AppDelegate {

    private boolean isLoadSuccess;
    private SmoothImageView smoothImageView;

    @Override public int getRootLayoutId() {
        return R.layout.activity_images_detail;
    }

    @Override public void initWidget() {
        smoothImageView = get(R.id.images_detail_smooth_image);
    }

    public void transformOut() {
        smoothImageView.transformOut();
    }

    public void setOriginalInfo(String mImageUrl, int mWidth, int mHeight, int mLocationX, int mLocationY, Runnable completeRun, Runnable varyViewRun) {
        smoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        smoothImageView.transformIn();

        PicassoLoader.createLoader(smoothImageView, mImageUrl)
                .attachCallback(new Callback() {
                    @Override public void onSuccess() {
                        isLoadSuccess = true;
                        if (varyViewRun != null) varyViewRun.run();
                    }

                    @Override public void onError() {
                        isLoadSuccess = false;
                        if (completeRun != null) completeRun.run();
                    }
                })
                .attach();

        smoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    if (completeRun != null) completeRun.run();
                }
            }
        });

        smoothImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v2) {
                smoothImageView.transformOut();
            }
        });
    }

    public boolean isLoadSuccess() {
        return isLoadSuccess;
    }

    @Override public View getLoadingTargetView() {
        return get(R.id.rootView);
    }
}
