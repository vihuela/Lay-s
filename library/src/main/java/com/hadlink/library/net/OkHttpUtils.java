package com.hadlink.library.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hadlink.library.application.CommonApplication;
import com.hadlink.library.conf.Constance;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.Buffer;

/**
 * Created by lyao on 2015/8/27.
 */
public class OkHttpUtils {
    public final static String RESPONSE_CACHE = "chehu_expert_cache";
    public final static int RESPONSE_CACHE_SIZE = 5000;
    public final static int HTTP_CONNECT_TIMEOUT = 8000;
    public final static int HTTP_READ_TIMEOUT = 5000;
    private static OkHttpClient singleton;

    public static OkHttpClient getInstance(final Context context, boolean debug) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient();
                    singleton.setCache(new Cache(new File(context.getCacheDir(), RESPONSE_CACHE), RESPONSE_CACHE_SIZE));
                    singleton.setConnectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                    singleton.setReadTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                    Interceptor interceptor = new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain
                                    .request()
                                    .newBuilder()
                                    .addHeader("User-Agent", "android")
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    };
                    singleton.interceptors().add(interceptor);
                    if (debug)
                        singleton.interceptors().add(new LoggingInterceptor());
                }
            }
        }
        return singleton;
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            String TAG = CommonApplication.getInstance().getAppLogTag();

            Request request = chain.request();
            String param = "post".equalsIgnoreCase(request.method()) ? "---REQ：" + "\n" + "       " + bodyToString(request) + "\n" : "";
            String beautyPrint;
            Response response;
            try {
                response = chain.proceed(request);
            } catch (IOException e) {
                beautyPrint = "--------------REQUEST START------------" + "\n"
                        + String.format("---URL：%s %s Access Error", request.url(), request.method()) + "\n"
                        + param
                        + String.format("---Res：%s", !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName()) + "\n";
                Log.e(TAG, beautyPrint);
                Log.e(TAG, "--------------REQUEST END--------------");
                throw new IOException(e);
            }

            String bodyString = response.body().string();

            long t2 = System.nanoTime();
            if (bodyString.startsWith("{") || bodyString.startsWith("[")) {
                beautyPrint = "--------------REQUEST START------------" + "\n"
                        + String.format("---URL：%s %s in %.1fms", request.url(), request.method(), (t2 - t1) / 1e6d) + "\n"
                        + param
                        + String.format("---RES：%s %d %s", response.protocol().toString(), response.code(), response.message()) + "\n";
                Log.d(TAG, beautyPrint);
                if (Constance.NetLog.printBody)
                    Logger.json(bodyString);
                Log.d(TAG, "--------------REQUEST END--------------");
            } else {
                beautyPrint = "--------------REQUEST START------------" + "\n"
                        + String.format("---URL：%s %s in %.1fms", request.url(), request.method(), (t2 - t1) / 1e6d) + "\n"
                        + param
                        + String.format("---RES：%s %d %s", response.protocol().toString(), response.code(), response.message()) + "\n"
                        + bodyString + "\n"
                        + "--------------REQUEST END--------------";
                Log.d(TAG, beautyPrint);
            }
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
    }
}
