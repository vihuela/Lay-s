package com.hadlink.lay_s.ui.delegate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.ui.adapter.MessageView;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.library.adapter.SmartAdapter;
import com.hadlink.library.adapter.adapters.RecyclerMultiAdapter;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.conf.CommonEvent;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class CommonRVDelegate extends AppDelegate {

    public LoadingCallBack loadingCallBack;
    RecyclerMultiAdapter adapter;
    XRecyclerView rv;
    private int currentPageNum = 1;//当前加载页
    private CommonEvent.IEvent currentEventTag;

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
                currentPageNum = 1;
                currentEventTag = new CommonEvent.ListEvent(CommonEvent.ListEvent.Refresh);
                if (loadingCallBack != null) loadingCallBack.onRefresh();
            }

            @Override public void onLoadMore() {
                currentPageNum++;
                currentEventTag = new CommonEvent.ListEvent(CommonEvent.ListEvent.LoadMore);
                if (loadingCallBack != null) loadingCallBack.onLoadMore();
            }
        });

        adapter = SmartAdapter
                .empty()
                .map(WaitingAskBean.class, MessageView.class)
                .into(rv);


    }

    @Override public boolean bindEvent() {
        return true;
    }

    public void setDatas(List datas) {
        adapter.setItems(datas);
        rv.refreshComplete();
    }

    public void addDatas(List datas) {
        adapter.addItems(datas);
        rv.loadMoreComplete();
    }

    public void setCallBack(LoadingCallBack loadingCallBack) {
        this.loadingCallBack = loadingCallBack;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public CommonEvent.IEvent getCurrentEventTag() {
        return currentEventTag;
    }

    @Override public void onEvent(CommonEvent.IEvent iEvent) {
        super.onEvent(iEvent);
        if (iEvent instanceof CommonEvent.ListEvent) {
            switch (((CommonEvent.ListEvent) iEvent).currentEvent) {
                case CommonEvent.ListEvent.Refresh:
                    rv.refreshComplete();
                    break;
                case CommonEvent.ListEvent.LoadMore:
                    rv.loadMoreComplete();
                    currentPageNum--;
                    break;
            }
        }
    }

    public interface LoadingCallBack {
        void onRefresh();

        void onLoadMore();
    }
}
