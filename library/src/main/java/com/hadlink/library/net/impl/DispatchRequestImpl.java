package com.hadlink.library.net.impl;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.hadlink.library.application.CommonApplication;
import com.hadlink.library.conf.CommonEvent;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.HttpException;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscriber;

/**
 * @author Created by lyao on 2015/11/29.
 */
public abstract class DispatchRequestImpl<T> extends Subscriber<T> implements Callback<T>, CommonDispatchRequest<T> {

    //default event
    public CommonEvent.IEvent eventTag = new CommonEvent.ToastEvent();

    public DispatchRequestImpl() {
    }

    public DispatchRequestImpl(CommonEvent.IEvent eventTag) {
        this.eventTag = eventTag;
    }

    /**
     * rx
     */
    @Override public final void onCompleted() {

    }

    @Override public final void onError(Throwable e) {
        if (e != null && e instanceof HttpException) {
            onDispatchError(Error.Server, ((HttpException) e).code() + "," + ((HttpException) e).message());
        } else if (e != null && (e instanceof UnknownHostException || e instanceof ConnectException || e instanceof SocketTimeoutException)) {
            onDispatchError(Error.NetWork, e.getMessage());
        } else if (e != null && e instanceof JsonSyntaxException) {
            onDispatchError(Error.Internal, e.getMessage());
        } else {
            if (e instanceof IOException && IO_EXCEPTION.equalsIgnoreCase(e.getMessage()) ||
                    e instanceof SocketException && SOCKET_EXCEPTION.equalsIgnoreCase(e.getMessage())) {
                //cancel request
                Logger.e(e.getMessage());
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

        if (t != null && (t instanceof UnknownHostException || t instanceof ConnectException || t instanceof SocketTimeoutException)) {
            onDispatchError(Error.NetWork, t.getMessage());
        } else if (t != null && t instanceof JsonSyntaxException) {
            onDispatchError(Error.Internal, t.getMessage());
        } else {
            if (t instanceof IOException && IO_EXCEPTION.equalsIgnoreCase(t.getMessage()) ||
                    t instanceof SocketException && SOCKET_EXCEPTION.equalsIgnoreCase(t.getMessage())) {
                //cancel request
                Logger.e(t.getMessage());
                return;
            }
            onDispatchError(Error.UnKnow, t != null ? t.getMessage() : "unKnowError");
        }
    }

    /**
     * common
     */
    @Override public final void onDispatchError(Error error, String message) {

        switch (error) {
            case Internal:
                if (CommonApplication.getInstance().getAppDebug()) {
                    onInternalError(message);
                } else {
                    onInternalError("程序小哥开小差了");
                }
                break;
            case Server:
                if (CommonApplication.getInstance().getAppDebug()) {
                    onServerError(message);
                }
                break;
            case NetWork:
                if (CommonApplication.getInstance().getAppDebug()) {
                    onNetWorkError(message);
                } else {
                    onNetWorkError("网络未连接");
                }
                break;
            case UnKnow:
                if (CommonApplication.getInstance().getAppDebug()) {
                    onUnKnowError(message);
                }
                break;
        }
        /**
         * 全局错误处理
         */
        if (eventTag instanceof CommonEvent.ToastEvent) {
            CommonEvent.ToastEvent event = (CommonEvent.ToastEvent) eventTag;
            event.error = error;
            event.message = message;
            EventBus.getDefault().post(event);
        } else if (eventTag instanceof CommonEvent.ListEvent) {
            EventBus.getDefault().post(eventTag);
        }

    }


    @Override public final void onDispatchSuccess(T t) {
        /**
         * 针对不同项目成功的配置
         */
        if (t != null && t instanceof CommonResponse) {
            CommonResponse c = (CommonResponse) t;
            if (c.code == 200) {
                onSuccess(t);
            } else if (!TextUtils.isEmpty(c.message)) {
                onDispatchError(Error.Server, c.message);
            }
        } else if (t != null && t.getClass() != null) {
            Logger.e("Please check " + t.getClass().getSimpleName() + "whether inheritance CommonResponse");
        } else {
            //tis null
            onDispatchError(Error.UnKnow, "response bean is null");
        }
    }


    /**
     * can override
     */
    public void onNetWorkError(String message) {
        Logger.e(message);
    }

    public void onInternalError(String message) {
        Logger.e(message);
    }

    public void onServerError(String message) {
        Logger.e(message);
    }

    public void onUnKnowError(String message) {
        Logger.e(message);
    }

    public abstract void onSuccess(T t);
}
