package com.hadlink.library.database.help;

/**
 * Created by ipcjs on 2015/7/7.
 */
public class Timestamp extends ATime {
    public Timestamp(long time) {
        super(time);
    }

    public Timestamp(long value, boolean isValue) {
        super(value, isValue);
    }

    @Override
    public Timestamp newWithDelta(long delta) {
        return new Timestamp(getValue() + delta, true);
    }

    public Timestamp() {
    }

    @Override
    public long getTime() {
        return getValue();
    }

    @Override
    public void setTime(long time) {
        setValue(time);
    }
}
