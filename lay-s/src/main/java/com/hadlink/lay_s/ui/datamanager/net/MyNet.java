package com.hadlink.lay_s.ui.datamanager.net;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.library.net.NetUtils;

/**
 * Created by zhouml on 2015/12/1.
 */
public class MyNet {
    public static ApiOverview get() {
        return NetUtils.createApi(ApiOverview.class, C.Host.host);
    }
}
