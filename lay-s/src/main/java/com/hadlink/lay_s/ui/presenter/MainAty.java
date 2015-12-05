package com.hadlink.lay_s.ui.presenter;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.lay_s.ui.datamanager.net.MyNet;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseListResponse;
import com.hadlink.lay_s.ui.delegate.CommonRVDelegate;
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
            BaseListResponse waitingAskBeanBaseListResponse = (BaseListResponse) busEvent.obj;
            List<WaitingAskBean> list = waitingAskBeanBaseListResponse.getResult();
            WaitingAskBean bean = list.get(1);
        }
    }

    private void requestList(boolean refresh) {
        MyNet.get().getCertificates(190);
//        MyNet.get().getWaitReplyList(107,viewDelegate.getCurrentPageNum(),C.List.numPerPage);
    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }
}
