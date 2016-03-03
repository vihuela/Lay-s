package com.hadlink.lay_s.delegate;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.rvhelpperlib.adapter.RecyclerViewAdapter;
import com.hadlink.rvhelpperlib.adapter.ViewHolderHelper;
import com.hadlink.rvhelpperlib.decoration.CommonItemDecoration;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.liulishuo.qiniuimageloader.utils.PicassoLoader;

import java.util.List;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListDelegate extends AppDelegate {
    public static final int OFFSET_PAGE = 20;
    public static final int LOAD_MORE_START_PAGE = OFFSET_PAGE;
    public static final int REFRESH_START_PAGE = 0;
    public LoadingCallBack loadingCallBack;
    XRecyclerView rv;
    private RecyclerViewAdapter<ImageDetail> adapter;
    private int currentLoadNum = LOAD_MORE_START_PAGE;//加载更多默认页是从第二页开始

    @Override public int getRootLayoutId() {
        return R.layout.delegate_image_list;
    }

    @Override public void initWidget() {
        rv = get(R.id.rv);
        rv.setArrowImageView(R.mipmap.iconfont_downgrey);
        rv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override public void onRefresh() {
                if (loadingCallBack != null) loadingCallBack.onRefresh();
            }

            @Override public void onLoadMore() {
                if (loadingCallBack != null) loadingCallBack.onLoadMore();
            }
        });
        rv.addItemDecoration(new CommonItemDecoration(16));
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        adapter = new RecyclerViewAdapter<ImageDetail>(rv, R.layout.item_image) {
            @Override protected void fillData(ViewHolderHelper viewHolderHelper, int position, ImageDetail model) {


                ImageView iv = viewHolderHelper.getIamgeView(R.id.iv);

                /**/
                if (!TextUtils.isEmpty(model.imageUrl))
                    PicassoLoader.createLoader(iv, model.imageUrl)
                            .attach();
                else iv.setImageBitmap(null);

            }
        };
        rv.setAdapter(adapter);


    }

    public void setDatas(boolean refresh, List<ImageDetail> datas) {
        if (refresh) {
            adapter.setDatas(datas);
            rv.postDelayed(new Runnable() {
                @Override public void run() {
                    rv.refreshComplete();
                    currentLoadNum = LOAD_MORE_START_PAGE;//重置加载更多页
                }
            }, 1000);
        } else {
            adapter.addMoreDatas(datas);
            rv.postDelayed(new Runnable() {
                @Override public void run() {
                    rv.loadMoreComplete();
                    currentLoadNum += OFFSET_PAGE;
                }
            }, 1500);
        }
    }

    public int getCurrentPageNum(boolean refresh) {
        return refresh ? REFRESH_START_PAGE : currentLoadNum;
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();
    }
}
