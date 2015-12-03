package com.hadlink.library.databasenew;

import com.hadlink.library.databasenew.tabinfo.Table;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouml on 2015/12/3.
 */
public class DB {

    public static final ConcurrentHashMap<Class<?>,Table> tables = new ConcurrentHashMap<Class<?>,Table>();

    public Table getTable(Class<?> clazz){
        return null;
    }

//    pbulic

}
