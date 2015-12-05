package com.hadlink.library.net.impl;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public interface CommonResponse<T> {

      T getResult();
    void setResult(T t);

//    List<T> getListResult();
//    void setListReslut(List<T> list);
}
