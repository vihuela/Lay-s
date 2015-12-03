package com.hadlink.library.database.sql;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public abstract class BaseDbHelper extends SQLiteOpenHelper {
    
    public static final String DATABASE_NAME = "googfit.db";
    // 数据库版本在生成代码时设置：MyBuilder.java
    protected static final int DATABASE_VERSION = 1;

    public BaseDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.beginTransaction();
        try {

        	initDao(db);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
        }
        onCreate(db);// 创建所有表
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("sql", "降级" + oldVersion + "->" + newVersion);
        onCreate(db);
    }
    
    public SQLiteDatabase getDB(){
    	return this.getWritableDatabase();
    }
    
    /**
     * 初始化所有的Dao类
     * @param db
     */
    public abstract void initDao(SQLiteDatabase db);

}
