package com.hadlink.library.net.proxy;

import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.library.Event.BusEvent;
import com.hadlink.library.net.ApiUtils;
import com.hadlink.library.net.GsonUtils;
import com.hadlink.library.net.impl.CommonResponse;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Call;

/**
 * Created by zhouml on 2015/12/1.
 */
public class NetProcess {
    Class apiInterface, clazzResult;
    String URL;

    public NetProcess(Class apiInterface, String URL) {
        this.apiInterface = apiInterface;
        this.URL = URL;
    }

    public void Process(final Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object api = ApiUtils.createApi(this.apiInterface, this.URL);
        ParameterizedType type = null, type1 = null;
        try {
            type = (ParameterizedType) method.getGenericReturnType();
            type1 = (ParameterizedType) type.getActualTypeArguments()[0];
            clazzResult = (Class) type1.getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call call = (Call) method.invoke(api, args);
        call.enqueue(new ApiUtils.callBack1() {
            @Override public void onSuccess(Object o) {
                CommonResponse response = (CommonResponse) o;
                if(clazzResult == null){//Api接口返回值没有写第二级泛型
                    post(o,method.getName());
                    return;
                }
                Object result = null;
                if(response.getResult() == null){
                }else if (response.getResult() instanceof List) {
                    result = new ArrayList();
                    List<LinkedTreeMap> list = (List<LinkedTreeMap>) response.getResult();
                    for (LinkedTreeMap map : list) {
                        JSONObject ob = new JSONObject(map);
                        ((List) result).add(GsonUtils.INSTANCE.get().fromJson(ob.toString(),clazzResult));
                    }
                }
//                else if (response.getResult() instanceof LinkedTreeMap) {
//                    JSONObject ob = new JSONObject((LinkedTreeMap)response.getResult());
//                    result =GsonUtils.INSTANCE.get().fromJson(ob.toString(),clazzResult) ;
//                }
                response.setResult(result);
                post(response,method.getName());
            }
        });
    }

    private void post(Object o,String methodName){
        BusEvent busEvent = new BusEvent();
        busEvent.what = methodName.hashCode();
        busEvent.obj = o;
        EventBus.getDefault().post(busEvent);
    }


}
