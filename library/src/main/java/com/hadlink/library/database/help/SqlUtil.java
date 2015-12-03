package com.hadlink.library.database.help;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JiangSong on 2015/3/7.
 */
public class SqlUtil {
    /**
     * 构造String数组
     * @param arg
     * @return
     */
    public static String[] newString(Object... arg) {
        String[] strings = new String[arg.length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = arg[i].toString();
        }
        return strings;
    }

    public static Double[] newDouble(double... ds) {
        Double[] out = new Double[ds.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = ds[i];
        }
        return out;
    }

    public static boolean isExistsTable(String tableName, SQLiteDatabase db) {
        boolean exists;
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", newString(tableName));
        exists = cursor.moveToNext();
        cursor.close();
        return exists;
    }
}
