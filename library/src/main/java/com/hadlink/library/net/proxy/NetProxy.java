package com.hadlink.library.net.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhouml on 2015/12/1.
 */
public class NetProxy implements InvocationHandler
{
    private NetProcess proxied;

    public NetProxy( NetProcess proxied )
    {
        this.proxied = proxied;
    }

    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
    {
        proxied.Process(method, args);
        return null;
    }

}