package com.hadlink.library.net.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by zhouml on 2015/12/1.
 */
public class Net<T> {

    public T RequestUtil(Class clazz,String URL){
        NetProcess real = new NetProcess(clazz,URL);
        T proxySubject = (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new NetProxy(real));
        return proxySubject;
    }

}
