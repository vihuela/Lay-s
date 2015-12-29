package com.hadlink.lay_s.ui.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.ui.adapter.MessageView;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.library.adapter.SmartAdapter;
import com.hadlink.library.adapter.adapters.RecyclerMultiAdapter;
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

    public static final int LOAD_MORE_START_PAGE = 2;
    public LoadingCallBack loadingCallBack;
    RecyclerMultiAdapter adapter;
    XRecyclerView rv;
    private int currentLoadNum = LOAD_MORE_START_PAGE;//加载更多默认页是从第二页开始

    @Override public int getRootLayoutId() {
        return R.layout.aty_main;
    }

    @Override public int getOptionsMenuId() {
        return R.menu.menu_main;
    }

    @Override public Toolbar getToolbar() {
        Toolbar toolbar = get(R.id.toolbar);
        toolbar.setTitle("Lay's");
        toolbar.setTitleTextAppearance(mContext, R.style.MenuTextStyle);
        return toolbar;
    }

    @Override public void initWidget() {
        rv = get(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
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

        adapter = SmartAdapter
                .empty()
                .map(WaitingAskBean.class, MessageView.class)
                .into(rv);


    }

    public void setDatas(List datas) {
        adapter.setItems(datas);
        rv.refreshComplete();
        currentLoadNum = LOAD_MORE_START_PAGE;//重置加载更多页
    }

    public void addDatas(List datas) {
        adapter.addItems(datas);
        rv.loadMoreComplete();
        ++currentLoadNum;
        com.orhanobut.logger.Logger.d("currentLoadNum:" + currentLoadNum);
    }

    public void error() {
        rv.refreshComplete();
        rv.loadMoreComplete();
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public int getCurrentPageNum(boolean refresh) {
        return refresh ? 1 : currentLoadNum;
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();
    }
}
