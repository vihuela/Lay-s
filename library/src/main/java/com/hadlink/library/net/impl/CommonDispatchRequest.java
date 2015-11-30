package com.hadlink.library.net.impl;

/**
 * @author Created by lyao on 2015/11/29.
 */
public interface CommonDispatchRequest<T> {
    String IO_EXCEPTION = "Canceled";
    String SOCKET_EXCEPTION = "Socket closed";

    void onDispatchError(Error error, String message);

    void onDispatchSuccess(T t);

    enum Error {
        NetWork, Internal, Server, UnKnow
    }
}
