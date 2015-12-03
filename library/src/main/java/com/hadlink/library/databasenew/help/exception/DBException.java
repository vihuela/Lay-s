package com.hadlink.library.databasenew.help.exception;

/**
 * Created by zhouml on 2015/12/2.
 */
public class DBException extends RuntimeException{

    public DBException(Throwable throwable) {
        super(throwable);
    }

    public DBException() {
    }

    public DBException(String detailMessage) {
        super(detailMessage);
    }

    public DBException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
