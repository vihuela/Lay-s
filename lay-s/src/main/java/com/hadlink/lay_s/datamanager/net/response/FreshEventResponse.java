package com.hadlink.lay_s.datamanager.net.response;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * @author Created by lyao on 2016/1/4.
 * @update
 * @description
 */
public class FreshEventResponse<T> implements CommonResponse<List<T>> {

    public int count;
    public int count_total;
    public int pages;
    public String status;

    public List<T> posts;


    @Override public List<T> getResult() {
        return posts;
    }

    @Override public void setResult(List<T> ts) {
        this.posts = ts;
    }

    @Override public boolean isValid() {
        return TextUtils.equals("ok", status);
    }


}
