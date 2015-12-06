package com.hadlink.library.net;

import com.hadlink.library.application.CommonApplication;
import com.hadlink.library.net.impl.DispatchRequestImpl;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyao on 2015/8/27.
 */
public abstract class ApiUtils {


    public static <T> T createApi(Class<T> cls, String host) {

        return RetrofitUtils.createApi(CommonApplication.getInstance(), cls, host, CommonApplication.getInstance()
                .getAppDebug());
    }

    public static <T> Observable<T> getObservable(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * call noNeed non200 check
     */
    public static abstract class callBack1<T> extends DispatchRequestImpl<T> {

        public callBack1() {
            super();
        }

    }

    /**
     * rxJava noNeed non200 check
     */
    public static abstract class callBack2<T> extends DispatchRequestImpl<T> {
        public callBack2() {
            super();
        }

    }
}
