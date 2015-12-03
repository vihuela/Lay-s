package com.hadlink.library.database.help;


import java.io.Serializable;

/**
 * Created by ipcjs on 2015/7/7.
 */
public abstract class ATime implements Serializable, Cloneable, Comparable<ATime> {
    private long value;

    public ATime() {
    }

    public ATime(long time) {
        setTime(time);
    }

    public ATime(long value, boolean isValue) {
        if (isValue) {
            setValue(value);
        } else {
            throw new RuntimeException("isValue一定要传true");
        }
    }

    public abstract ATime newWithDelta(long delta);

    public abstract long getTime();

    public abstract void setTime(long time);

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public ATime clone() {
        return newWithDelta(0);
    }

    @Override
    public int compareTo(ATime another) {
        if (this.getTime() > another.getTime()) {
            return 1;
        } else if (this.getTime() < another.getTime()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ATime && getTime() == ((ATime) o).getTime();
    }

    @Override
    public int hashCode() {
        long time = getTime();
        return (int) (time ^ (time >>> 32));
    }
}
