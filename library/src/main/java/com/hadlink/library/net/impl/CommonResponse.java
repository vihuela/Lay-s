package com.hadlink.library.net.impl;

/**
 * @author Created by Zhouml on 2015/11/29.
 * @update
 * @description
 * 网络的通用返回接口
 */
public interface CommonResponse<T> {

    /**
     * 接口返回的有效数据方法
     * @return
     */
    T getResult();

    /**
     * 设置具体是什么值是接口返回的有效数据
     * @param t
     */
    void setResult(T t);

    /**
     * 判断接口返回的数据是否有效
     * @return
     */
    boolean isValid();
}
