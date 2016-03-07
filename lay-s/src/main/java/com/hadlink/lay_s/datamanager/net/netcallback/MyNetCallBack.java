package com.hadlink.lay_s.datamanager.net.netcallback;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.model.EventImpl;
import com.hadlink.library.model.Event;
import com.hadlink.library.util.rx.RxBus;

public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {

    public MyNetCallBack(String eventTag) {
        super(eventTag);
    }

    public MyNetCallBack() {
        super(null);
    }

    /**
     * 除了Invalid 返回的是原对象之外，其余都是String
     */
    @Override public void onDispatchError(Error error, ErrorInfo e) {

        switch (error) {
            case Internal:
                break;
            case Invalid:
                /**
                 * 当数据无效时候你做的处理
                 */
                T result = (T) e.getObject();

                break;
            case NetWork:
                break;
            case Server:
                break;
            case UnKnow:
                break;
        }
        //post event
        Event event = new EventImpl();
        event.arg = EventImpl.NET_REQUEST_ERROR;
        event.setObject(e);
        RxBus.getDefault().post(event);
    }
}
