package com.hadlink.library.database.help;

import android.util.Log;


import java.util.List;

/**
 * Created by Administrator on 2015/4/24.
 */
public class PrintC2bAndB2c {

    public static boolean needPrint = true;

    public static String c2bPrint(List<BaseSqlParams> list, String tableName) {
        if (!needPrint) {
            return "";
        }
        StringBuilder c2b = new StringBuilder();
        tableName = changeLine(tableName);
        c2b.append(OVERRIDE).append(SPACE).
                append(PUBLIC).append(tableName).append(" c2b").append(SQL_CONS.LEFT_BRACKET).append(CURSOR).append(C).append(SQL_CONS.RIGHT_BRACKET).append(LEFT_K).append(SPACE)
                .append(tableName).append(B).append(SQL_CONS.EQUAL).append(NEW).append(tableName).append(SQL_CONS.LEFT_BRACKET).append(SQL_CONS.RIGHT_BRACKET).append(DIVIDE).append(SPACE);
        for (BaseSqlParams ba : list) {
            c2b.append(B).append(POINT).append(SET).append(changeLine(ba.getName())).append(SQL_CONS.LEFT_BRACKET);
            /*
            ShowTime的话，生成的格式如下；
                b.setTime(new ShowTime(c.getLong(c.getColumnIndex("time")), true));
                b.setDate(             c.getLong(c.getColumnIndex("date"))       );
            和普通的类型生成的格式的区别，自己体会下。。。
            */
            int newShowTimeBegin = c2b.length();
            c2b.append(C).append(POINT).
                    append(GET).append(changeLine(getPrintType(ba.getType()))).append(SQL_CONS.LEFT_BRACKET).append(C).append(POINT).append(GET).append(COLUMN).append(SQL_CONS.LEFT_BRACKET).
                    append(YINHAO).append(ba.getName()).append(YINHAO).append(SQL_CONS.RIGHT_BRACKET).append(SQL_CONS.RIGHT_BRACKET);
            int newShowTimeEnd = c2b.length();
            c2b.append(SQL_CONS.RIGHT_BRACKET).append(DIVIDE).append(SPACE);
            if (ba.getType().equals(SQL_CONS.SHOWTIME_TYPE)) {
                c2b.insert(newShowTimeBegin, SQL_CONS.SHOWTIME_NEW_BEGIN);
                c2b.insert(newShowTimeEnd + SQL_CONS.SHOWTIME_NEW_BEGIN.length(), SQL_CONS.SHOWTIME_NEW_END);
            }
        }
        c2b.append(RETURN).append(B).append(DIVIDE).append(SPACE).append(RIGHT_K);
        Log.d("rd62", c2b.toString());

        return c2b.toString();
    }

    public static String b2cPrint(List<BaseSqlParams> list, String tableName) {
        if (!needPrint) {
            return "";
        }
        StringBuilder b2c = new StringBuilder();
        tableName = changeLine(tableName);
        b2c.append(OVERRIDE).append(SPACE).append(PUBLIC).append(CONTENTVALUES).append(" b2c").append(SQL_CONS.LEFT_BRACKET).append(tableName).append(B).append(SQL_CONS.RIGHT_BRACKET).append(LEFT_K)
                .append(CONTENTVALUES).append(C).append(SQL_CONS.EQUAL).append(NEW).append(CONTENTVALUES).append(SQL_CONS.LEFT_BRACKET).append(SQL_CONS.RIGHT_BRACKET).append(DIVIDE);
        for (BaseSqlParams ba : list) {
            b2c.append(C).append(POINT).append(PUT).append(SQL_CONS.LEFT_BRACKET).append(YINHAO).append(ba.getName()).append(YINHAO).
                    append(SQL_CONS.DOT).append(B).append(POINT).append(GET).append(changeLine(ba.getName())).append(SQL_CONS.LEFT_BRACKET).append(SQL_CONS.RIGHT_BRACKET);
            // 对于ShowTime，要调用getValue()取出实际的值
            if (ba.getType().equals(SQL_CONS.SHOWTIME_TYPE)) {
                b2c.append(SQL_CONS.SHOWTIME_GET_VALUE);
            }
            b2c.append(SQL_CONS.RIGHT_BRACKET).append(DIVIDE);

        }
        b2c.append(RETURN).append(C).append(DIVIDE).append(RIGHT_K);
        Log.d("rd62", b2c.toString());
        return b2c.toString();
    }

    public static String getPrintType(String type) {
        if (type.equals(SQL_CONS.BOOLEAN_OB) || type.equals(SQL_CONS.BOOLEAN)) {
            return SQL_CONS.INT;
        } else if (type.equals(SQL_CONS.BYTE_OB) || type.equals(SQL_CONS.BYTE) ||
                type.equals(SQL_CONS.SHORT_OB)) {
            return SQL_CONS.SHORT;
        } else if (type.equals(SQL_CONS.INTEGER_OB)) {
            return SQL_CONS.INT;
        } else if (type.equals(SQL_CONS.LONG_OB)) {
            return SQL_CONS.LONG;
        } else if (type.equals(SQL_CONS.FLOAT_OB)) {
            return SQL_CONS.FLOAT;
        } else if (type.equals(SQL_CONS.DOUBLE_OB)) {
            return SQL_CONS.DOUBLE;
        } else if (type.equals(SQL_CONS.STRING)) {
            return "String";
        } else if (type.equals(SQL_CONS.SHOWTIME_TYPE)) {// ShowTime在数据库中是INTEGER，取出成long
            return SQL_CONS.LONG;
        } else {
            return type;
        }
    }

    public static String changeLine(String line) {
        line = line.trim();
        String result = line.substring(0, 1).toUpperCase() + line.substring(1);
        return result;
    }

    private static final String OVERRIDE = "@Override";
    private static final String PUBLIC = "public ";
    private static final String LEFT_K = " {";
    private static final String RIGHT_K = " }";
    private static final String CURSOR = "Cursor ";
    private static final String C = " c ";
    private static final String B = " b ";
    private static final String POINT = ".";
    private static final String DIVIDE = ";";
    private static final String NEW = "new ";
    private static final String COLUMN = "ColumnIndex";
    private static final String CONTENTVALUES = "ContentValues ";
    private static final String PUT = "put";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String SPACE = " ";
    private static final String YINHAO = "\"";
    private static final String RETURN = "return ";
}
