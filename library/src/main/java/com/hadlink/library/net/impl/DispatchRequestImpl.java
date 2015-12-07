package com.hadlink.library.net.impl;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.library.net.GsonUtils;
import com.hadlink.library.net.NetUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.HttpException;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscriber;

/**
 * @author Created by lyao on 2015/11/29.
 */
public abstract class DispatchRequestImpl<T> extends Subscriber<T> implements Callback<T>, CommonDispatchRequest<T> {

    private Object result;

    public DispatchRequestImpl() {
    }

    protected Object getResult() {
        return result;
    }

    /**
     * rx
     */
    @Override public final void onCompleted() {

    }

    @Override public final void onError(Throwable e) {
        if (e != null && e instanceof HttpException) {
            onDispatchError(Error.Server, ((HttpException) e).code() + "," + ((HttpException) e).message());
        } else if (e != null && (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof
                SocketTimeoutException)) {
            onDispatchError(Error.NetWork, e.getMessage());
        } else if (e != null && e instanceof JsonSyntaxException) {
            onDispatchError(Error.Internal, e.getMessage());
        } else {
            if (e instanceof IOException && IO_EXCEPTION.equalsIgnoreCase(e.getMessage()) ||
                    e instanceof SocketException && SOCKET_EXCEPTION.equalsIgnoreCase(e.getMessage())) {
                //cancel request
                printLog(e.getMessage());
                return;
            }
            onDispatchError(Error.UnKnow, e != null ? e.getMessage() : "unKnowError");
        }
    }

    @Override public final void onNext(T t) {
        onDispatchSuccess(t);
    }

    /**
     * call
     */
    @Override public final void onResponse(Response<T> response, Retrofit retrofit) {
        T body = response.body();
        if (body == null) {
            onDispatchError(Error.Server, response.raw().code() + "," + response.raw().message());
        } else {
            onDispatchSuccess(body);
        }
    }

    @Override public final void onFailure(Throwable t) {

        if (t != null &&
                (t instanceof UnknownHostException || t instanceof ConnectException || t instanceof SocketTimeoutException ||
                        (t.getCause() != null && t.getCause() instanceof UnknownHostException))) {
            onDispatchError(Error.NetWork, t.getMessage());
        } else if (t != null && t instanceof JsonSyntaxException) {
            onDispatchError(Error.Internal, t.getMessage());
        } else {
            if (t instanceof IOException && IO_EXCEPTION.equalsIgnoreCase(t.getMessage()) ||
                    t instanceof SocketException && SOCKET_EXCEPTION.equalsIgnoreCase(t.getMessage())) {
                //cancel request
                printLog(t.getMessage());
                return;
            }
            onDispatchError(Error.UnKnow, t != null ? t.getMessage() : "unKnowError");
        }
    }

    @Override public abstract void onDispatchError(Error error, Object message);

    @SuppressWarnings("all")
    @Override public final void onDispatchSuccess(T t) {
        this.result = t;
        /**
         * 针对不同项目成功的配置
         */
        if (t != null && t instanceof CommonResponse) {
            CommonResponse c = (CommonResponse) t;
            if (c.isValid()) {
                /**
                 *  有二级List
                 */
                if (c.getResult() != null) {
                    if (List.class.isInstance(c.getResult())) {
                        try {
                            ParameterizedType typeCallOROb = (ParameterizedType) this.getClass().getGenericSuperclass();
                            ParameterizedType typeBeanORList = (ParameterizedType) typeCallOROb.getActualTypeArguments()[0];
                            /**
                             * 二级泛型
                             */
                            Class<?> clazzResult = (Class) (typeBeanORList.getActualTypeArguments()[0]);
                            List l = new ArrayList();
                            List<LinkedTreeMap> list = (List<LinkedTreeMap>) c.getResult();
                            for (LinkedTreeMap map : list) {
                                JSONObject ob = new JSONObject(map);
                                l.add(GsonUtils.INSTANCE.get().fromJson(ob.toString(), clazzResult));
                            }
                            c.setResult(l);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                onSuccess(t);
            } else {
                onDispatchError(Error.Invalid, t);
            }
        } else if (t != null && t.getClass() != null) {
            printLog("check " + t.getClass().getSimpleName() + " whether inheritance CommonResponse Please");
        } else {
            //t is null
            onDispatchError(Error.UnKnow, "response bean is null");
        }
    }

    private void printLog(String s) {
        Log.e(NetUtils.netConfig.LOG_TAG, s);
    }


    public abstract void onSuccess(T t);

}
