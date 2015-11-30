package com.hadlink.library.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * Created by lyao on 2015/8/27.
 */
public class GsonUtils {
    public static Gson newInstance() {
        GsonBuilder builder = new GsonBuilder();
        builder
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Date.class, new DateTypeAdapter());

        return builder.create();
    }
}
