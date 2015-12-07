package com.hadlink.library.net;

import android.app.Application;

import com.hadlink.library.util.JsonPrinter;

/**
 * Created by zhouml on 2015/12/7.
 * 网络配置
 */
public class NetConfig {
    public String LOG_TAG = "lays";
    String RESPONSE_CACHE = "lays_net_cache";
    int RESPONSE_CACHE_SIZE = 5000;
    int HTTP_CONNECT_TIMEOUT = 8000;
    int HTTP_READ_TIMEOUT = 5000;
    boolean PRINT_BODY = true;
    boolean LOG = true;
    Application app = null;

    public NetConfig(String RESPONSE_CACHE, int RESPONSE_CACHE_SIZE, int HTTP_CONNECT_TIMEOUT, int HTTP_READ_TIMEOUT, boolean PRINT_BODY, boolean LOG, String LOG_TAG, Application app) {
        this.RESPONSE_CACHE = RESPONSE_CACHE;
        this.RESPONSE_CACHE_SIZE = RESPONSE_CACHE_SIZE;
        this.HTTP_CONNECT_TIMEOUT = HTTP_CONNECT_TIMEOUT;
        this.HTTP_READ_TIMEOUT = HTTP_READ_TIMEOUT;
        this.PRINT_BODY = PRINT_BODY;
        this.LOG = LOG;
        this.LOG_TAG = LOG_TAG;
        this.app = app;
        JsonPrinter.TAG = this.LOG_TAG;
    }


}
