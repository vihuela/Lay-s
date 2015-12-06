package com.hadlink.library.net.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by zhouml on 2015/12/1.
 */
@SuppressWarnings("all")
public class Net<T> {

    public T RequestUtil(Class clazz,String host){
        NetProcess real = new NetProcess(clazz,host);
        T proxySubject = (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new NetProxy(real));
        return proxySubject;
    }

    public static <T> T get(Class<T> clazz,String host){
        return new Net<T>().RequestUtil(clazz, host);
    }
}
