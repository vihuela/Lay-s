package com.hadlink.library.net.proxy;

import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.library.conf.NetSetter;
import com.hadlink.library.event.NetEvent;
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
import rx.Observable;

/**
 * Created by zhouml on 2015/12/1.
 */
@SuppressWarnings("all")
public class NetProcess {
    NetSetter netSetter;
    Class apiOverview, clazzResult;
    String host;

    public NetProcess(Class apiOverview, String host, NetSetter netSetter) {
        this.apiOverview = apiOverview;
        this.host = host;
        this.netSetter = netSetter;
    }

    public void Process(final Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Object api = ApiUtils.createApi(this.apiOverview, this.host);
        ParameterizedType typeCallOROb, typeBeanORList;
        try {
            typeCallOROb = (ParameterizedType) method.getGenericReturnType();
            typeBeanORList = (ParameterizedType) typeCallOROb.getActualTypeArguments()[0];

            /**
             * 二级泛型
             */
            clazzResult = (Class) (typeBeanORList.getActualTypeArguments()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object o = (Object) method.invoke(api, args);

        if (Observable.class.isInstance(o)) {
            Observable ob = (Observable) o;
            ApiUtils.getObservable(ob).subscribe(new ApiUtils.callBack2<CommonResponse>() {
                @Override public void onSuccess(CommonResponse o) {
                    if (o != null) processResult(o, method);
                }
            });
        } else if (Call.class.isInstance(o)) {
            Call call = (Call) o;
            call.enqueue(new ApiUtils.callBack1<CommonResponse>() {
                @Override public void onSuccess(CommonResponse o) {
                    if (o != null) processResult(o, method);
                }
            });
        }


    }

    private void processResult(CommonResponse o, Method method) {


        if (o != null) {

            /**
             *  有二级泛型
             */
            if (o.getResult() != null && clazzResult != null) {
                if (o.getResult() instanceof List) {
                    List l = new ArrayList();
                    List<LinkedTreeMap> list = (List<LinkedTreeMap>) o.getResult();
                    for (LinkedTreeMap map : list) {
                        JSONObject ob = new JSONObject(map);
                        l.add(GsonUtils.INSTANCE.get().fromJson(ob.toString(), clazzResult));
                    }
                    o.setResult(l);
                }
            }
        }


        post(o, method.getName());

    }

    private void post(CommonResponse o, String methodName) {
        NetEvent netEvent = new NetEvent();
        netEvent.what = methodName.hashCode();
        netEvent.obj = o;
        netEvent.requestCode = netSetter != null ? netSetter.requestCode : -1;
        EventBus.getDefault().post(netEvent);
    }


}
