package com.hadlink.lay_s.ui.datamanager.net;


import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.lay_s.ui.datamanager.database.dao.Certificate;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseBeanResponse;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseListResponse;
import com.hadlink.library.net.proxy.ResponseClass;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public interface ApiOverview {

    /**
     * 等待回答列表
     */
    @GET("question/getWaitReplyList")
    @ResponseClass(WaitingAskBean.class)
    Call<BaseListResponse>  getWaitReplyList(@Query("expertID") int expertID, @Query("pageNum") int pageNum, @Query("numPerPage") int numPerPage);

    /**
     * 等待回答列表
     */
    @GET("expertUser/selectCertificates")
    @ResponseClass(Certificate.class)
    Call<BaseBeanResponse>  getCertificates(@Query("expertID") int expertID);


}
