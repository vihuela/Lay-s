package com.hadlink.lay_s.model;

public class Event {
    public final static String SEND_TAB_TITLE_ACTION = "send_tab_title";
    private String action;
    private Object object;
    public int arg;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public <T extends Object> T getObject() {
        return (T) object;
    }

    public void setObject(Object obj) {
        this.object = obj;
    }
}
