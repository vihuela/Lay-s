package com.hadlink.library.databasenew;

import android.database.sqlite.SQLiteDatabase;

import com.hadlink.library.databasenew.help.exception.DBException;
import com.hadlink.library.databasenew.sqlite.condition.SQLCondition;
import com.hadlink.library.databasenew.tabinfo.Table;

import java.util.List;

/**
 * Created by zhouml on 2015/12/2.
 */
public class DaoManagerImpl implements DaoManager {

    Table table;

    @Override public SQLiteDatabase getDatabase() {
        return null;
    }

    @Override public Class getDBClass() {
        return null;
    }

    @Override public <A> List<A> queryAll() throws DBException {
        return null;
    }

    @Override public <A> List<A> queryList(SQLCondition condition) throws DBException {
        return null;
    }

    @Override public <A> A query(SQLCondition condition) throws DBException {
        return null;
    }

    @Override public <A> A queryByKey(Object o) throws DBException {
        return null;
    }

    @Override public <A> boolean save(A a) throws DBException {
        return false;
    }

    @Override public <A> boolean saveAll(List<A> list) throws DBException {
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
}
