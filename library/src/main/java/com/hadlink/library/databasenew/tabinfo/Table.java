package com.hadlink.library.databasenew.tabinfo;

import com.hadlink.library.databasenew.help.exception.DBException;
import com.hadlink.library.databasenew.sqlite.SQLInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouml on 2015/12/2.
 */
public class Table {

    public List<Cloumn> Keys = new ArrayList<Cloumn>();
    public List<Cloumn> AllCloumn = new ArrayList<Cloumn>();

    public ConcurrentHashMap<String,SQLInfo> sqlInfos = new ConcurrentHashMap<>();

    /**
     * 判断表字段是否配置好，如果没有配置跑出异常
     * @return
     */
    public void checkCloumnConfig() throws DBException{

    }


    public SQLInfo getSQLInfo(String key){
        return null;
    }



}
