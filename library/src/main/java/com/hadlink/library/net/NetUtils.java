package com.hadlink.library.net;

import com.hadlink.library.net.impl.DispatchRequestImpl;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyao on 2015/8/27.
 */
public abstract class NetUtils {
    public static NetConfig netConfig ;
    public static void setNetConfig(NetConfig netConf){
        NetUtils.netConfig = netConf;
    }
    public static <T> T createApi(Class<T> cls, String host) {
        return RetrofitUtils.createApi(netConfig.app, cls, host);
    }

    public static <T> Observable<T> getObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * call noNeed non200 check
     */
    public static abstract class callBack<T> extends DispatchRequestImpl<T> {

        public callBack() {
            super();
        }
    }


}
