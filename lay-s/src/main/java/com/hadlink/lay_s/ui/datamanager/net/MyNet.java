package com.hadlink.lay_s.ui.datamanager.net;

import com.hadlink.lay_s.ui.conf.C;
import com.hadlink.library.conf.NetSetter;
import com.hadlink.library.net.ApiUtils;
import com.hadlink.library.net.proxy.Net;

/**
 * Created by zhouml on 2015/12/1.
 */
public class MyNet {
    public static ApiOverview get() {
        return Net.get(ApiOverview.class, C.Host.host, null);
    }

    public static ApiOverview get(int requestCode) {
        NetSetter netSetter = new NetSetter();
        netSetter.requestCode = requestCode;
        return Net.get(ApiOverview.class, C.Host.host, netSetter);
    }

    public static ApiOverview getOrigin() {
        return ApiUtils.createApi(ApiOverview.class, C.Host.host);
    }
}
