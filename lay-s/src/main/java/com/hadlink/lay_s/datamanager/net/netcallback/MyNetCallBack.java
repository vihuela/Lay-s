package com.hadlink.lay_s.datamanager.net.netcallback;

import android.widget.Toast;

import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.app.App;

public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {

    @Override public void onDispatchError(Error error, Object message) {
        switch (error) {
            case Internal:
                Toast(message.toString());
                break;
            case Invalid:
                /**
                 * 当数据无效时候你做的处理
                 */
                T result = (T) message;

                break;
            case NetWork:
                Toast(message.toString());
                break;
            case Server:
                Toast(message.toString());
                break;
            case UnKnow:
                Toast(message.toString());
                break;
        }
    }

    public void Toast(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
