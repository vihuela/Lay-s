package com.hadlink.lay_s.ui.conf;

import com.hadlink.library.net.ApiUtils;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public final class ApiManager {

    public static ApiOverview AskModule() {

        return ApiUtils.createApi(ApiOverview.class, Constance.Host.host);
    }
}
