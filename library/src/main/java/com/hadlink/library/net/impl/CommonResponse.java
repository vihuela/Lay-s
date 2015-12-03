package com.hadlink.library.net.impl;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public interface CommonResponse {

    public abstract <T> T getResult();
    public abstract void setResult(Object t);
}
