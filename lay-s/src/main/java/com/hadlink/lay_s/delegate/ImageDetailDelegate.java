package com.hadlink.lay_s.delegate;

import android.view.View;

import com.hadlink.lay_s.R;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.SmoothImageView;
import com.liulishuo.qiniuimageloader.utils.PicassoLoader;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Created by lyao on 2016/3/7.
 */
public class ImageDetailDelegate extends AppDelegate {

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

    public void setOriginalInfo(String mImageUrl, int mWidth, int mHeight, int mLocationX, int mLocationY, Runnable r) {
        smoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        smoothImageView.transformIn();

        PicassoLoader.createLoader(smoothImageView, mImageUrl)
                .attach();

        smoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    if (r != null) r.run();
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
}
