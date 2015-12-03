package com.hadlink.library.databasenew;

import android.database.sqlite.SQLiteDatabase;

import com.hadlink.library.databasenew.help.exception.DBException;
import com.hadlink.library.databasenew.sqlite.condition.SQLCondition;

import java.util.List;

/**
 * Created by zhouml on 2015/12/2.
 */
public interface DaoManager {

    SQLiteDatabase getDatabase();
    Class getDBClass();

     <A> List<A> queryAll() throws DBException;
     <A> List<A> queryList(SQLCondition condition) throws DBException;
     <A> A query(SQLCondition condition) throws DBException;
     <A> A queryByKey(Object o)throws DBException;

    <A> boolean save(A a) throws DBException;
    <A> boolean saveAll(List<A> list) throws DBException;

    boolean deleteByKey(Object o)throws DBException;
    <A>boolean delete(A a) throws DBException;
    boolean deleteAll() throws DBException;

}
