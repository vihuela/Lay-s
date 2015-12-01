package com.hadlink.lay_s.ui.presenter;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.lay_s.ui.conf.MyNet;
import com.hadlink.lay_s.ui.delegate.CommonRVDelegate;
import com.hadlink.lay_s.ui.pojo.model.WaitingAskBean;
import com.hadlink.lay_s.ui.pojo.response.BaseListResponse;
import com.hadlink.library.Event.BusEvent;
import com.hadlink.library.base.presenter.ActivityPresenter;

import java.util.List;

public class MainAty extends ActivityPresenter<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
    }

    @Override
    public void onEvent(BusEvent busEvent){
        if(busEvent.what == C.Request.getWaitReplyList.hashCode()){
            BaseListResponse<WaitingAskBean> waitingAskBeanBaseListResponse = (BaseListResponse<WaitingAskBean>) busEvent.obj;
            List<WaitingAskBean> list = waitingAskBeanBaseListResponse.data.pageData;

        }
    }

    private void requestList(boolean refresh) {
        MyNet.get().getWaitReplyList(107, viewDelegate.getCurrentPageNum(), C.List.numPerPage);
    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }
}
