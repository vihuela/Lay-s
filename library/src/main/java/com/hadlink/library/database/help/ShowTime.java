package com.hadlink.library.database.help;



import java.util.TimeZone;

/**
 * Created by ipcjs on 2015/7/7.
 */
public class ShowTime extends ATime {
    public ShowTime(long time) {
        super(time);
    }

    public ShowTime(long value, boolean isValue) {
        super(value, isValue);
    }

    @Override
    public ShowTime newWithDelta(long delta) {
        return new ShowTime(getValue() + delta, true);
    }

    public ShowTime() {
    }

    @Override
    public long getTime() {
        return getValue() - TimeZone.getDefault().getRawOffset();
    }

    @Override
    public void setTime(long time) {
        setValue(time + TimeZone.getDefault().getRawOffset());
    }



    @Override
    public ShowTime clone() {
        return newWithDelta(0);
    }
}
