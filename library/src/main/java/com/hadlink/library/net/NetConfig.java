package com.hadlink.library.net;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by zhouml on 2015/12/7.
 * 网络配置
 */
public class NetConfig {
    String RESPONSE_CACHE = "lays_net_cache";
    int RESPONSE_CACHE_SIZE = 5000;
    int HTTP_CONNECT_TIMEOUT = 8000;
    int HTTP_READ_TIMEOUT = 5000;
    boolean PRINT_BODY = true;
    boolean LOG = true;
    String LOG_TAG = "lays";

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

        //beauty log
        Logger
                .init(this.LOG_TAG)
                .methodCount(0)
                .methodOffset(0)
                .hideThreadInfo()
                .logLevel(LOG ? com.orhanobut.logger.LogLevel.FULL : com.orhanobut.logger.LogLevel.NONE);
    }


}
