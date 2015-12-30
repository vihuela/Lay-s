package com.hadlink.lay_s.ui.datamanager.net;


import com.hadlink.lay_s.ui.datamanager.bean.Certificaty;
import com.hadlink.lay_s.ui.datamanager.bean.Test;
import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseBeanResponse;
import com.hadlink.lay_s.ui.datamanager.net.baseResponse.BaseListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public interface ApiOverview {


    /**
     * 等待回答列表
     */
    @GET("question/getWaitReplyList") Observable<BaseListResponse<WaitingAskBean>> getWaitReplyList(@Query("expertID") int expertID, @Query("pageNum") int pageNum, @Query("numPerPage") int numPerPage);

    /**
     * 等待回答列表
     */
    @GET("expertUser/selectCertificates") Call<BaseBeanResponse<Certificaty>> getCertificates(@Query("expertID") int expertID);


    @GET("http://192.168.100.61:8090/chehu-expert-api/test/two")
    Call<BaseBeanResponse<Test>> getTest();

    @GET("http://192.168.100.61:8090/chehu-expert-api/test/two")
    Observable<BaseBeanResponse<Test>> getTest1();


}
