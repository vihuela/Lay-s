package com.hadlink.lay_s.ui.presenter;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.lay_s.ui.datamanager.net.MyNet;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseListResponse;
import com.hadlink.lay_s.ui.datamanager.net.netcallback.MyNetCallBack;
import com.hadlink.lay_s.ui.delegate.CommonRVDelegate;
import com.hadlink.library.base.presenter.ActivityPresenter;

import java.util.List;

import rx.Observable;

public class MainAty extends ActivityPresenter<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
    }

    private void requestList(boolean refresh) {
        final Observable<BaseListResponse<WaitingAskBean>> waitReplyList = MyNet.get().getWaitReplyList(107, viewDelegate.getCurrentPageNum(refresh), C.List.numPerPage);
        waitReplyList.subscribe(new MyNetCallBack<BaseListResponse<WaitingAskBean>>() {
            @Override public void onSuccess(BaseListResponse<WaitingAskBean> response) {
                List<WaitingAskBean> list = response.getResult();
                if (refresh) viewDelegate.setDatas(response.getResult());
                else viewDelegate.addDatas(response.getResult());
            }

            @Override public void onDispatchError(Error error, Object message) {
                super.onDispatchError(error, message);
                /**
                 * 这里可以自定义接口错误处理
                 */
                viewDelegate.error();
            }
        });
    }

    @Override public void onRefresh() {
        requestList(true);
    }

    @Override public void onLoadMore() {
        requestList(false);
    }


}
