package com.hadlink.lay_s.ui.datamanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hadlink.lay_s.ui.app.App;
import com.hadlink.lay_s.ui.datamanager.database.dao.WaitingAskBeanDao;
import com.hadlink.library.database.sql.BaseDbHelper;

/**
 * Created by zhouml on 2015/12/2.
 */
public class DAOHelper extends BaseDbHelper {

    public DAOHelper(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    private static DAOHelper helper;

    public static synchronized DAOHelper getInstance() {
        if (helper == null) {
            helper = new DAOHelper(App.getInstance());
        }
        return helper;
    }
    public static SQLiteDatabase getDb() {
        return getInstance().getWritableDatabase();
    }

    @Override
    public void initDao(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        /**
         * 每一个添加的Dao,都需要在这里初始化
         */
        WaitingAskBeanDao.getInstance().init(db,this);
    }
}
