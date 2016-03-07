package com.hadlink.library.model;

public class Event {

    public final static int NET_REQUEST_ERROR = 0x10;

    public int arg;
    private Object object;

    public <T extends Object> T getObject() {
        return (T) object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }
}
