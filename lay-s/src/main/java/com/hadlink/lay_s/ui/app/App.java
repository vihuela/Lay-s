package com.hadlink.lay_s.ui.app;

import android.support.v4.util.ArrayMap;

import com.hadlink.easynet.conf.NetConfigBuilder;
import com.hadlink.easynet.util.NetConfig;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.BuildConfig;
import com.hadlink.library.application.CommonApplication;

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
        ArrayMap<String, String> header = new ArrayMap<>();
        header.put("User-Agent", "android");
        final NetConfig netConfig = new NetConfigBuilder()
                .context(this)
                .log(true)
                .logTag("you_tag_name")
                .printResponseBody(false)
                .header(header)
                .createNetConfig();

        NetUtils.setNetConfig(netConfig);
    }
}
