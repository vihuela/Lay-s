package com.hadlink.lay_s.delegate;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.utils.UriHelper;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.XSwipeRefreshLayout;
import com.hadlink.library.widget.pla.PLAImageView;
import com.hadlink.library.widget.pla.PLALoadMoreListView;
import com.hadlink.rvhelpperlib.adapter.AdapterViewAdapter;
import com.hadlink.rvhelpperlib.adapter.ViewHolderHelper;
import com.liulishuo.qiniuimageloader.utils.PicassoLoader;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class ImageListDelegate extends AppDelegate {
    public static final int OFFSET_PAGE = UriHelper.PAGE_LIMIT;
    public static final int REFRESH_START_PAGE = 0;
    public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;

    LoadingCallBack loadingCallBack;
    XSwipeRefreshLayout xSwipeRefreshLayout;
    PLALoadMoreListView plaLoadMoreListView;
    int currentLoadNum = REFRESH_START_PAGE;
    AdapterViewAdapter<ImageDetail> adapter;
    Context ctx;

    @Override public int getRootLayoutId() {
        return R.layout.delegate_image_list;
    }

    @Override public void initWidget() {
        xSwipeRefreshLayout = get(R.id.fragment_images_list_swipe_layout);
        plaLoadMoreListView = get(R.id.fragment_images_list_list_view);
        ctx = plaLoadMoreListView.getContext();

        //config view
        xSwipeRefreshLayout.setColorSchemeColors(
                ctx.getResources().getColor(R.color.gplus_color_1),
                ctx.getResources().getColor(R.color.gplus_color_2),
                ctx.getResources().getColor(R.color.gplus_color_3),
                ctx.getResources().getColor(R.color.gplus_color_4));
        xSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                if (loadingCallBack != null) loadingCallBack.onRefresh();
            }
        });
        plaLoadMoreListView.setOnLoadMoreListener(new PLALoadMoreListView.OnLoadMoreListener() {
            @Override public void onLoadMore() {
                if (loadingCallBack != null) loadingCallBack.onLoadMore();
            }
        });

        //adapter
        adapter = new AdapterViewAdapter<ImageDetail>(ctx, R.layout.item_image) {
            @Override protected void fillData(ViewHolderHelper viewHolderHelper, int position, ImageDetail model) {
                PLAImageView iv = viewHolderHelper.getView(R.id.iv);

                if (!TextUtils.isEmpty(model.thumbnailUrl)) {
                    PicassoLoader.createLoader(iv, model.thumbnailUrl)
                            .attach();
                    iv.setImageWidth(model.thumbnailWidth);
                    iv.setImageHeight(model.thumbnailHeight);
                }
            }
        };
        plaLoadMoreListView.setAdapter(adapter);

        //all ready
        plaLoadMoreListView.postDelayed(new Runnable() {
            @Override public void run() {
                if (loadingCallBack != null) loadingCallBack.onPrepare();
            }
        }, PAGE_LAZY_LOAD_DELAY_TIME_MS);


    }

    public void setDatas(boolean refresh, ImageListResponse<ImageDetail> datas) {
        if (refresh) {
            adapter.setDatas(datas.getResult());
            xSwipeRefreshLayout.setRefreshing(false);
        } else {
            adapter.addMoreDatas(datas.getResult());
            plaLoadMoreListView.onLoadMoreComplete();
        }

        //check load more enable
        if (UriHelper.getInstance().calculateTotalPages(datas.totalNum) > currentLoadNum) {
            plaLoadMoreListView.setCanLoadMore(true);
        } else {
            plaLoadMoreListView.setCanLoadMore(false);
        }
    }

    public int getCurrentPageNum(boolean refresh) {
        if (refresh) {
            currentLoadNum = REFRESH_START_PAGE;
        } else {
            currentLoadNum++;
        }
        return currentLoadNum * OFFSET_PAGE;
    }

    public void setError(boolean refresh) {
        xSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override public void run() {
                xSwipeRefreshLayout.setRefreshing(false);
                plaLoadMoreListView.onLoadMoreComplete();
            }
        }, PAGE_LAZY_LOAD_DELAY_TIME_MS);

        //reset currentPage
        if (!refresh) {
            currentLoadNum--;
        }
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();

        /**
         * lazy load data
         */
        void onPrepare();
    }
}
