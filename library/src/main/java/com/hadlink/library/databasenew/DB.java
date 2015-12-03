package com.hadlink.library.databasenew;

import com.hadlink.library.databasenew.tabinfo.Table;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouml on 2015/12/3.
 * 注册
 * 获取Table
 * 获取DB
 */
public class DB {

    public static final ConcurrentHashMap<Class<?>,Table> tables = new ConcurrentHashMap<Class<?>,Table>();

    private Table getTable(Class<?> clazz){
        return null;
    }

    public DaoManagerImpl getDao(Class<?> clazz){
        return new DaoManagerImpl(getTable(clazz));
    }

    @Override
    public void close() throws IOException {
        String dbName = this.daoConfig.getDbName();
        if (daoMap.containsKey(dbName)) {
            daoMap.remove(dbName);
            this.database.close();
        }
    }

//    pbulic

}
