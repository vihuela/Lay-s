package com.hadlink.library.databasenew.sqlite;

/**
 * Created by zhouml on 2015/12/3.
 */
public enum  SQLmain {
    CREATE("create %s "),SELECT(""),INSERT(""),UPDATA("");
    private String str;

    SQLmain(String str){
        this.str = str;
    }

    @Override public String toString() {
        return str+" ";
    }
}
