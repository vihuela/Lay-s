package com.hadlink.lay_s.ui.datamanager.net.netcallback;

import android.content.Context;
import android.widget.Toast;

import com.hadlink.lay_s.ui.app.App;
import com.hadlink.library.net.NetUtils;

/**
 * Created by zhouml on 2015/12/7.
 */
public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {

    Context context;


    public MyNetCallBack(){
        this.context = App.getInstance();
    }

    @Override public void onDispatchError(Error error, Object message) {
        switch (error){
            case Internal:
                Toast(message.toString());
                break;
            case Invalid:
                T result = (T) message;
                /**
                 * 当数据无效时候你做的处理
                 */
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
    public void Toast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
