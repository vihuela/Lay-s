package com.hadlink.lay_s.datamanager.net;


import com.hadlink.lay_s.datamanager.bean.FreshEvent;
import com.hadlink.lay_s.datamanager.bean.ImageDetail;
import com.hadlink.lay_s.datamanager.net.response.FreshEventDetailResponse;
import com.hadlink.lay_s.datamanager.net.response.FreshEventResponse;
import com.hadlink.lay_s.datamanager.net.response.ImageListResponse;
import com.hadlink.lay_s.utils.UriHelper;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public interface ApiOverview {



    /**
     * 新鲜事列表
     */
    @GET("http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1")
    Observable<FreshEventResponse<FreshEvent>> getFreshList(@Query("page") int startIndex);

    /**
     * 新鲜事详情
     */
    @GET("http://jandan.net/?oxwlxojflwblxbsapi=get_post&include=content&id=73580?oxwlxojflwblxbsapi=get_post&include=content")
    Observable<FreshEventDetailResponse> getFreshDetail(@Query("id") int id);

    /**
     * 图片列表
     */
    @GET("http://image.baidu.com/data/imgs?tag=全部&rn="+UriHelper.PAGE_LIMIT+"&from=1")
    Observable<ImageListResponse<ImageDetail>> getImageList(@Query("col") String classify,@Query("pn") int startIndex);
}
