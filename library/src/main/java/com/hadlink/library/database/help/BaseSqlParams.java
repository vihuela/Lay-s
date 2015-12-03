package com.hadlink.library.database.help;



/**
 * Created by Administrator on 2015/4/23.
 */
public class BaseSqlParams {
    String name;
    String type;
    boolean isKey;
    boolean isUniqueness;

    public BaseSqlParams(String name, String type, boolean isKey, boolean isUniqueness) {
        this.name = name;
        this.type = type;
        this.isKey = isKey;
        this.isUniqueness = isUniqueness;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isKey() {
        return isKey;
    }

    public boolean isUniqueness() {
        return isUniqueness;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public void setUniqueness(boolean isUniqueness) {
        this.isUniqueness = isUniqueness;
    }

    public String getSQLType(){
        if(type.equals(SQL_CONS.BOOLEAN)||type.equals(SQL_CONS.BOOLEAN_OB)
                ||type.equals(SQL_CONS.BYTE)||type.equals(SQL_CONS.BYTE_OB)
                ||type.equals(SQL_CONS.CHAR)||type.equals(SQL_CONS.CHARACTER_OB)
                ||type.equals(SQL_CONS.INT)||type.equals(SQL_CONS.INTEGER_OB)
                ||type.equals(SQL_CONS.LONG)||type.equals(SQL_CONS.LONG_OB)
                ||type.equals(SQL_CONS.SHORT)||type.equals(SQL_CONS.SHORT_OB)
                ||type.equals(SQL_CONS.SHOWTIME_TYPE)// ShowTime在数据库中存成Integer
                ){
            return SQL_CONS.TYPE_INTEGER;
        }else if(type.equals(SQL_CONS.STRING)){
            return SQL_CONS.TYPE_TEXT;
        }else if(type.equals(SQL_CONS.DOUBLE)||type.equals(SQL_CONS.DOUBLE_OB)
                ||type.equals(SQL_CONS.FLOAT)||type.equals(SQL_CONS.FLOAT_OB)){
            return SQL_CONS.TYPE_REAL;
        }
        return "";
    }

    @Override
    public String toString() {
        return "BaseSqlParams{" +
                "name=\"" + name + '\'' +
                ", type=\"" + type + '\"' +
                ", isKey=" + isKey +
                ", isUniqueness=" + isUniqueness +
                '}';
    }
}
