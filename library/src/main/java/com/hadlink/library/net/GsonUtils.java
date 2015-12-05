package com.hadlink.library.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * Created by lyao on 2015/8/27.
 */
public enum  GsonUtils {
    INSTANCE;
    private GsonUtils(){
        GsonBuilder builder = new GsonBuilder();
        builder
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .enableComplexMapKeySerialization()
//                .registerTypeAdapter(Response.class, new MyCommonGsonAdapter());
                .registerTypeAdapter(Date.class, new DateTypeAdapter());
        gson = builder.create();
    }
    public  Gson gson;

    public Gson get(){
        return gson;
    }

}
