package com.hadlink.lay_s.delegate;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;

import com.hadlink.lay_s.R;
import com.hadlink.library.base.view.AppDelegate;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class CommonRVDelegate extends AppDelegate {

    public static final int LOAD_MORE_START_PAGE = 1;
    public static final int REFRESH_START_PAGE = 0;
    public final static int REFRESH = 0;
    public final static int LOADMORE = 1;
    public LoadingCallBack loadingCallBack;

    /*RecyclerMultiAdapter adapter;*/
    XRecyclerView rv;
    private Handler handler = new Handler(Looper.myLooper());
    private int currentLoadNum = LOAD_MORE_START_PAGE;//加载更多默认页是从第二页开始

    @Override public int getRootLayoutId() {
        return R.layout.aty_main;
    }

    @Override public int getOptionsMenuId() {
        return R.menu.menu_main;
    }

    @Override public void initWidget() {
        rv = get(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
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

    }

    public void setDatas(List datas) {
        /*adapter.setItems(datas);*/
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                rv.refreshComplete();
            }
        }, 1000);
        currentLoadNum = LOAD_MORE_START_PAGE;//重置加载更多页

    }

    public void addDatas(List datas) {
        /*adapter.addItems(datas);*/
        ++currentLoadNum;
        rv.loadMoreComplete();
    }

    public void error() {
        rv.refreshComplete();
        rv.loadMoreComplete();
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public int getCurrentPageNum(boolean refresh) {
        return refresh ? REFRESH_START_PAGE : currentLoadNum;
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();
    }
}
