package com.hadlink.lay_s.app;

import com.hadlink.easynet.conf.NetConfigBuilder;
import com.hadlink.easynet.util.NetConfig;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.BuildConfig;
import com.hadlink.library.application.CommonApplication;

/**
 * @author Created by lyao on 2015/11/29.
 */
public class App extends CommonApplication {
    public final static String TAG = "lays";
    public final static boolean DEBUG = BuildConfig.DEBUG;

    @Override protected boolean isDebugLog() {
        return DEBUG;
    }

    @Override protected String getLogTag() {
        return TAG;
    }


    @Override public void onCreate() {
        super.onCreate();
        if (defaultProcess) {
            //net
            final NetConfig netConfig = new NetConfigBuilder()
                    .context(this)
                    .log(DEBUG)
                    .logTag(TAG)
                    .printResponseBody(DEBUG)
                    .createNetConfig();

            NetUtils.setNetConfig(netConfig);
            //image
        }

    }
}
