package com.hadlink.library.database.help;

/**
 * Created by Administrator on 2015/4/23.
 */
public class SQL_CONS {

    public static final String CREATE_BEGIN = "create table if not exists ";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String DOT = ",";
    public static final String AND = " and ";
    public static final String EQUAL = "=";
    public static final String QUEST = "?";
    public static final String PRIMARY_KEY = "  PRIMARY KEY ";

    public static final String TYPE_INTEGER = " INTEGER";
    public static final String TYPE_REAL = " REAL";
    public static final String TYPE_TEXT = " TEXT";

    public static final String STRING = "class java.lang.String";
    public static final String INTEGER_OB = "class java.lang.Integer";
    public static final String BOOLEAN_OB = "class java.lang.Boolean";
    public static final String BYTE_OB = "class java.lang.Byte";
    public static final String CHARACTER_OB = "class java.lang.Character";
    public static final String DOUBLE_OB = "class java.lang.Double";
    public static final String FLOAT_OB = "class java.lang.Float";
    public static final String LONG_OB = "class java.lang.Long";
    public static final String SHORT_OB = "class java.lang.Short";
    public static final String BOOLEAN = "boolean";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    public static final String CHAR = "char";
    public static final String FLOAT = "float";
    public static final String BYTE = "byte";
    public static final String SHORT = "short";
    public static final String INT = "int";

    /*ShowTime*/
    public static final String SHOWTIME_TYPE = ShowTime.class.toString();
    public static final String SHOWTIME_GET_VALUE = ".getValue()";
    public static final String SHOWTIME_NEW_BEGIN = "new ShowTime(";
    public static final String SHOWTIME_NEW_END = ", true)";
}
