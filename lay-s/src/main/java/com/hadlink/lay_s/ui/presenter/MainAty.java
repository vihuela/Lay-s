package com.hadlink.lay_s.ui.presenter;

import com.hadlink.lay_s.ui.conf.ApiManager;
import com.hadlink.lay_s.ui.conf.Constance;
import com.hadlink.lay_s.ui.delegate.CommonRVDelegate;
import com.hadlink.lay_s.ui.pojo.model.WaitingAskBean;
import com.hadlink.lay_s.ui.pojo.response.WaitReplyListResponse;
import com.hadlink.library.base.presenter.ActivityPresenter;
import com.hadlink.library.net.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

public class MainAty extends ActivityPresenter<CommonRVDelegate> implements CommonRVDelegate.LoadingCallBack {


    @Override protected Class<CommonRVDelegate> getDelegateClass() {
        return CommonRVDelegate.class;
    }

    @Override protected void bindEvenListener() {
        viewDelegate.setCallBack(this);
        requestList(true);
    }


    private void requestList(boolean refresh) {
        Call<WaitReplyListResponse> waitReplyList = ApiManager.AskModule().getWaitReplyList(107, viewDelegate.getCurrentPageNum(), Constance.List.numPerPage);
        waitReplyList.enqueue(new ApiUtils.callBack1<WaitReplyListResponse>(viewDelegate.getCurrentEventTag()) {
            @Override public void onSuccess(WaitReplyListResponse waitingAsk) {
                List<WaitingAskBean> askBeans = new ArrayList<>();
                for (WaitReplyListResponse.DataEntity.PageDataEntity entity : waitingAsk.data.pageData) {
                    WaitingAskBean askBean = WaitingAskBean.createWaitingAskBean(
                            false,
                            waitingAsk.data.dataTotal,
                            waitingAsk.data.pageTotal,
                            entity.nickName,
                            entity.avatarUrl,
                            entity.awardScore,
                            entity.createTime,
                            entity.gender,
                            entity.msgCount,
                            entity.questionContent,
                            entity.questionID,
                            entity.stdName,
                            entity.tagName,
                            entity.userID
                    );
                    askBeans.add(askBean);
                }
                if (refresh) viewDelegate.setDatas(askBeans);
                else {
                    viewDelegate.addDatas(askBeans);
                }
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
