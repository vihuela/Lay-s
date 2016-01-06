package com.hadlink.lay_s.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

/**
 * Created by zhouml on 2015/12/3.
 */
public class BaseBeanResponse<T> implements CommonResponse<T> {

    public T data;
    public long code;
    public String message;


    @Override public T getResult() {
        return data;
    }

    @Override public void setResult(T t) {
        data = t;
    }

    @Override public boolean isValid() {
        return code==200;
    }


}
