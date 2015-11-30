package com.hadlink.library.conf;

import com.hadlink.library.net.impl.CommonDispatchRequest;

/**
 * @author Created by lyao on 2015/11/29.
 */
public class CommonEvent {

    public interface IEvent {
    }

    /**
     * 列表事件
     */
    public static final class ListEvent implements IEvent {
        public static final int None = 1;
        public static final int Refresh = 2;
        public static final int LoadMore = 3;

        public int currentEvent = 1;

        public ListEvent(int event) {
            this.currentEvent = event;
        }
    }

    /**
     * 通用错误事件
     */
    public static final class ToastEvent implements IEvent {
        public CommonDispatchRequest.Error error;
        public String message;
    }
}
