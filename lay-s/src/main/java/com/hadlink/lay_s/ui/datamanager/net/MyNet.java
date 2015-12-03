package com.hadlink.lay_s.ui.datamanager.net;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.library.net.proxy.Net;

/**
 * Created by zhouml on 2015/12/1.
 */
public class MyNet {
    public static ApiOverview get(){
        return new Net<ApiOverview>().RequestUtil(ApiOverview.class, C.Host.host);
    }
}
