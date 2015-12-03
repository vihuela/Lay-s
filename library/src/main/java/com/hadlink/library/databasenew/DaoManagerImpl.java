package com.hadlink.library.databasenew;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hadlink.library.databasenew.help.exception.DBException;
import com.hadlink.library.databasenew.sqlite.SQLInfo;
import com.hadlink.library.databasenew.sqlite.condition.SQLCondition;
import com.hadlink.library.databasenew.tabinfo.Table;

import java.util.List;

/**
 * Created by zhouml on 2015/12/2.
 */
public class DaoManagerImpl<A> implements DaoManager {

    Class<?> beanClass;

    Table table;
    private SQLiteDatabase database;
    boolean allowTransaction;


    public DaoManagerImpl(){}
    public DaoManagerImpl(Table table){
        this.table = table;
    }

    @Override public SQLiteDatabase getDatabase() {
        return database;
    }

    @Override public Class getDBClass() {
        return null;
    }



    @Override public  List<A> queryAll() throws DBException {
        List<A> result = null;
        SQLInfo sqlInfo = table.getSQLInfo("queryAll");

        return result;
    }

    @Override public  List<A> queryList(SQLCondition condition) throws DBException {
        return null;
    }

    @Override public  A query(SQLCondition condition) throws DBException {
        return null;
    }

    @Override public  A queryByKey(Object o) throws DBException {
        return null;
    }

    @Override public  boolean save(Object a) throws DBException {
        return false;
    }

    @Override public  boolean saveAll(List list) throws DBException {
        return false;
    }

    @Override public boolean deleteByKey(Object o) throws DBException {
        return false;
    }

    @Override public <A> boolean delete(A a) throws DBException {
        return false;
    }

    @Override public boolean deleteAll() throws DBException {
        return false;
    }

    @Override public A c2b(Cursor c) {
        return null;
    }


    ///////////////////////////////////// exec sql /////////////////////////////////////////////////////

    private void beginTransaction() {
        if (allowTransaction) {
            database.beginTransaction();
        }
    }

    private void setTransactionSuccessful() {
        if (allowTransaction) {
            database.setTransactionSuccessful();
        }
    }

    private void endTransaction() {
        if (allowTransaction) {
            database.endTransaction();
        }
    }


//    public void execNonQuery(SQLInfo sqlInfo) throws DBException {
//        try {
//            Object[] bindArgs = sqlInfo.getBindArgs();
//            if (bindArgs != null && bindArgs.length > 0) {
//                database.execSQL(sqlInfo.getSql(), bindArgs);
//            } else {
//                database.execSQL(sqlInfo.getSql());
//            }
//        } catch (Throwable e) {
//            throw new DBException(e);
//        }
//
//    }

    public void execNonQuery(String sql) throws DBException {
        try {
            database.execSQL(sql);
        } catch (Throwable e) {
            throw new DBException(e);
        }
    }

//    public Cursor execQuery(SQLInfo sqlInfo) throws DBException {
//        try {
//            return database.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStrArray());
//        } catch (Throwable e) {
//            throw new DBException(e);
//        }
//    }

    public Cursor execQuery(String sql) throws DBException {
        try {
            return database.rawQuery(sql, null);
        } catch (Throwable e) {
            throw new DBException(e);
        }
    }


}
