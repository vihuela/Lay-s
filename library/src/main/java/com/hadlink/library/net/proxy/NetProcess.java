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
import rx.Observable;

/**
 * Created by zhouml on 2015/12/1.
 */
@SuppressWarnings("all")
public class NetProcess {
    Class apiOverview, clazzResult;
    String host;

    public NetProcess(Class apiOverview, String host) {
        this.apiOverview = apiOverview;
        this.host = host;
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
        Object o = method.invoke(api, args);

        if (Observable.class.isInstance(o)) {
            Observable ob = (Observable) o;
            ApiUtils.getObservable(ob).subscribe(new ApiUtils.callBack2<Object>() {
                @Override public void onSuccess(Object o) {
                    processResult(o, method);
                }
            });
        } else if (Call.class.isInstance(o)) {
            Call call = (Call) o;
            call.enqueue(new ApiUtils.callBack1() {
                @Override public void onSuccess(Object o) {
                    processResult(o, method);
                }
            });
        }


    }

    private void processResult(Object o, Method method) {
        Object res = null;
        CommonResponse commonRes = null;
        if (o instanceof CommonResponse) {
            commonRes = (CommonResponse) o;
            res = commonRes;
        }
        else res = o;


        if (commonRes != null) {

            /**
             *  有二级泛型
             */
            if (commonRes.getResult() != null && clazzResult != null) {
                if (commonRes.getResult() instanceof List) {
                    res = new ArrayList();
                    List<LinkedTreeMap> list = (List<LinkedTreeMap>) commonRes.getResult();
                    for (LinkedTreeMap map : list) {
                        JSONObject ob = new JSONObject(map);
                        ((List) res).add(GsonUtils.INSTANCE.get().fromJson(ob.toString(), clazzResult));
                    }
                    commonRes.setResult(res);
                }
            }
        }


        post(res, method.getName());

    }

    private void post(Object o, String methodName) {
        BusEvent busEvent = new BusEvent();
        busEvent.what = methodName.hashCode();
        busEvent.obj = o;
        EventBus.getDefault().post(busEvent);
    }


}
