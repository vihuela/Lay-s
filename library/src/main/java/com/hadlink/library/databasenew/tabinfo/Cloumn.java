package com.hadlink.library.databasenew.tabinfo;

/**
 * Created by zhouml on 2015/12/2.
 */
public class Cloumn {

    private String name;
    private SQLTYPE sqltype;
    private boolean isKey;
    private boolean isUniqueness;
    private boolean isId;

    public Cloumn(String name, SQLTYPE sqltype, boolean isKey, boolean isUniqueness, boolean isId) {
        this.name = name;
        this.sqltype = sqltype;
        this.isKey = isKey;
        this.isUniqueness = isUniqueness;
        this.isId = isId;
    }


}
