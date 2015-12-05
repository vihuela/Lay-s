package com.hadlink.lay_s.ui.app;

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
    }
}
