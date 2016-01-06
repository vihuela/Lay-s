package com.hadlink.lay_s.datamanager.net.response;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonResponse;

/**
 * @author Created by lyao on 2016/1/4.
 * @update
 * @description
 */
public class FreshEventDetailResponse implements CommonResponse {

    /**
     * status : ok
     * previous_url : http://jandan.net/2016/01/04/apollo-astronaut-life-insurance.html
     * next_url : http://jandan.net/2016/01/04/under-100000-pounds.html
     */

    public String status;
    /**
     * id : 73580

     */

    public PostEntity post;
    public String previous_url;
    public String next_url;

    @Override public Object getResult() {
        return null;
    }

    @Override public void setResult(Object o) {

    }

    @Override public boolean isValid() {
        return TextUtils.equals("ok", status);
    }

    public static class PostEntity {
        public int id;
        public String content;
    }
}
