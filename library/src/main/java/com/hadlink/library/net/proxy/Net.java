package com.hadlink.library.net.proxy;

import com.hadlink.library.conf.NetSetter;

import java.lang.reflect.Proxy;

/**
 * Created by zhouml on 2015/12/1.
 */
@SuppressWarnings("all")
@Deprecated
public class Net<T> {


    public T RequestUtil(Class apiOverViewClass, String host, NetSetter netSetter){
        NetProcess real = new NetProcess(apiOverViewClass,host,netSetter);
        T proxySubject = (T) Proxy.newProxyInstance(apiOverViewClass.getClassLoader(),
                new Class[]{apiOverViewClass},
                new NetProxy(real));
        return proxySubject;
    }
    public static <T> T get(Class<T> clazz,String host,NetSetter netSetter){
        return new Net<T>().RequestUtil(clazz, host,netSetter);
    }


}
