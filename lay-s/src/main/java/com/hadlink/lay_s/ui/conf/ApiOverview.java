package com.hadlink.lay_s.ui.conf;

import com.hadlink.lay_s.ui.pojo.response.WaitReplyListResponse;

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
    @GET("question/getWaitReplyList") Call<WaitReplyListResponse> getWaitReplyList(@Query("expertID") int expertID, @Query("pageNum") int pageNum, @Query("numPerPage") int numPerPage);
}
