package com.hadlink.lay_s.ui.app;

import com.hadlink.lay_s.BuildConfig;
import com.hadlink.library.application.CommonApplication;
import com.hadlink.library.net.NetUtils;
import com.hadlink.library.net.NetConfig;
import com.hadlink.library.net.NetConfigBuilder;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class App extends CommonApplication {

    @Override protected boolean getLog() {
        return BuildConfig.DEBUG;
    }

    @Override public void onCreate() {
        super.onCreate();

        final NetConfig netConfig = new NetConfigBuilder()
                .appContext(this)
                .responseCacheDir("you_cache_dir_name")
                .log(true)
                .logTag("you_tag_name")
                .printResponseBody(false)
                .createNetConfig();

        NetUtils.setNetConfig(netConfig);
    }
}
