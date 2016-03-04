package com.hadlink.lay_s.datamanager.net.netcallback;

import android.widget.Toast;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.app.App;
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

    @Override public void onDispatchError(Error error, ErrorInfo e) {
        switch (error) {
            case Internal:
                Toast(e.getObject().toString());
                break;
            case Invalid:
                /**
                 * 当数据无效时候你做的处理
                 */
                T result = (T) e.getObject();

                break;
            case NetWork:
                Toast(e.getObject().toString());
                break;
            case Server:
                Toast(e.getObject().toString());
                break;
            case UnKnow:
                Toast(e.getObject().toString());
                break;
        }
        //post event
        Event event = new EventImpl();
        event.arg = EventImpl.NET_REQUEST_ERROR;
        event.setObject(e);
        RxBus.getDefault().post(event);
    }

    public void Toast(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
