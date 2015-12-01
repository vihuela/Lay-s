package com.hadlink.library.net.proxy;

import com.hadlink.library.Event.BusEvent;
import com.hadlink.library.net.ApiUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;
import retrofit.Call;

/**
 * Created by zhouml on 2015/12/1.
 */
public class NetProcess {
    Class apiInterface ;
    String URL;
    public NetProcess(Class apiInterface,String URL){
        this.apiInterface = apiInterface;
        this.URL = URL;
    }

    public void Process(final Method method,Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object api = ApiUtils.createApi(this.apiInterface, this.URL);
        Call call = (Call) method.invoke(api, args);
        call.enqueue(new ApiUtils.callBack1() {
            @Override public void onSuccess(Object o) {
                BusEvent busEvent = new BusEvent();
                busEvent.what = method.getName().hashCode();
                busEvent.obj = o;
                EventBus.getDefault().post(busEvent);
            }
        });
    }

}
