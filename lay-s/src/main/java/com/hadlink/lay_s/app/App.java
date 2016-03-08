/*
 *    Copyright (c) 2016, lyao. lomoliger@hotmail.com
 *
 *     Part of the code from the open source community,
 *     thanks stackOverflow & gitHub .
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

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
