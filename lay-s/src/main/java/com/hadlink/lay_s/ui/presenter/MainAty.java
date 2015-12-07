package com.hadlink.lay_s.ui.presenter;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.lay_s.ui.datamanager.net.MyNet;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseListResponse;
import com.hadlink.lay_s.ui.delegate.CommonRVDelegate;
import com.hadlink.library.base.presenter.ActivityPresenter;
import com.hadlink.library.event.NetEvent;

public class MainAty extends ActivityPresenter<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
    }

    @Override
    public void onEvent(NetEvent netEvent) {
        if (netEvent.what == C.Request.getWaitReplyList.hashCode()) {
            BaseListResponse response = (BaseListResponse) netEvent.obj;
            switch (netEvent.requestCode) {
                case CommonRVDelegate.REFRESH:
                    viewDelegate.setDatas(response.getResult());
                    break;
                case CommonRVDelegate.LOADMORE:
                    viewDelegate.addDatas(response.getResult());
                    break;
            }
        }
    }

    private void requestList(boolean refresh) {
        /*MyNet.get().getCertificates(190);*/
        int requestCode = refresh ? CommonRVDelegate.REFRESH : CommonRVDelegate.LOADMORE;
        MyNet.get(requestCode).getWaitReplyList(107, viewDelegate.getCurrentPageNum(refresh), C.List.numPerPage);
        /*MyNet.get().getTest1();*/
    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }


}
