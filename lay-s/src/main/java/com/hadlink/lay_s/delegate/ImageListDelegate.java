package com.hadlink.lay_s.delegate;

import com.hadlink.lay_s.R;
import com.hadlink.library.base.view.AppDelegate;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListDelegate extends AppDelegate {
    XRecyclerView rv;

    @Override public int getRootLayoutId() {
        return R.layout.delegate_image_list;
    }

    @Override public void initWidget() {
        rv = get(R.id.rv);
        rv.setArrowImageView(R.mipmap.iconfont_downgrey);
        rv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
    }
}
