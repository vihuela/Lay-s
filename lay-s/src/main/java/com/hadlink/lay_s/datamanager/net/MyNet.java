package com.hadlink.lay_s.datamanager.net;

import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.conf.C;

/**
 * Created by zhouml on 2015/12/1.
 */
public class MyNet {
    public static ApiOverview get() {
        return NetUtils.createApi(ApiOverview.class, C.Host.host);
    }
}
