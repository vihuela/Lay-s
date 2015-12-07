package com.hadlink.library.net;

import android.app.Application;

public class NetConfigBuilder {

    private String response_cache= "lays_net_cache";
    private int response_cache_size= 5000;
    private int http_connect_timeout= 8000;
    private int http_read_timeout= 5000;
    private boolean print_body=true;
    private boolean log= true;
    private String log_tag = "lays";
    private Application app;

    public NetConfigBuilder setRESPONSE_CACHE(String response_cache) {
        this.response_cache = response_cache;
        return this;
    }

    public NetConfigBuilder setRESPONSE_CACHE_SIZE(int response_cache_size) {
        this.response_cache_size = response_cache_size;
        return this;
    }

    public NetConfigBuilder setHTTP_CONNECT_TIMEOUT(int http_connect_timeout) {
        this.http_connect_timeout = http_connect_timeout;
        return this;
    }

    public NetConfigBuilder setHTTP_READ_TIMEOUT(int http_read_timeout) {
        this.http_read_timeout = http_read_timeout;
        return this;
    }

    public NetConfigBuilder setPRINT_BODY(boolean print_body) {
        this.print_body = print_body;
        return this;
    }

    public NetConfigBuilder setLOG(boolean log) {
        this.log = log;
        return this;
    }

    public NetConfigBuilder setLOG_TAG(String log_tag) {
        this.log_tag = log_tag;
        return this;
    }

    public NetConfigBuilder setApp(Application app) {
        this.app = app;
        return this;
    }

    public NetConfig createNetConfig() {
        return new NetConfig(response_cache, response_cache_size, http_connect_timeout, http_read_timeout, print_body, log, log_tag, app);
    }
}