package com.hadlink.library.net.proxy;

import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;
import com.hadlink.library.Event.BusEvent;
import com.hadlink.library.net.ApiUtils;
import com.hadlink.library.net.impl.CommonResponse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        final ResponseClass annotation = method.getAnnotation(ResponseClass.class);
        Call call = (Call) method.invoke(api, args);
        call.enqueue(new ApiUtils.callBack1() {
            @Override public void onSuccess(Object o) {
                BusEvent busEvent = new BusEvent();
                Class clazz = annotation.value();
                CommonResponse response = (CommonResponse) o;
                Object result = null;
                if(response.getResult() instanceof List){
                    result = new ArrayList();
                    List<LinkedTreeMap> list = response.getResult();
                    for(LinkedTreeMap map:list){
                        ((List) result).add(transLinkedTreemap2Obj(clazz, map));
                    }
                    Log.d("","");
                }else if(response.getResult() instanceof LinkedTreeMap){
                    result = transLinkedTreemap2Obj(clazz,(LinkedTreeMap)(response.getResult()));
                }
                response.setResult(result);
                busEvent.what = method.getName().hashCode();
                busEvent.obj = o;
                EventBus.getDefault().post(busEvent);
            }
        });
    }

    public Object transLinkedTreemap2Obj(Class clazz,LinkedTreeMap map){
        try {
            Object obj = clazz.newInstance();
            Field[] fields = clazz.getFields();
            Iterator lit = map.entrySet().iterator();
            while (lit.hasNext()) {
                Map.Entry e = (Map.Entry) lit.next();
                for(Field field:fields){
                    if(field.getName().equals(e.getKey())){
                        field.getType();
                        if(String.class.equals(field.getType())){
                            if(e.getValue() != null) {
                                field.set(obj, e.getValue());
                            }
                        }else if(Byte.class.equals(field.getType())||byte.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            byte f= (byte) fValue;
                            field.set(obj,f);
                        }else if(Character.class.equals(field.getType())||char.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            char f= (char) fValue;
                            field.set(obj,f);
                        }else if(Short.class.equals(field.getType())||short.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            short f= (short) fValue;
                            field.set(obj,f);
                        }else if(Integer.class.equals(field.getType())||int.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            int f= (int) fValue;
                            field.set(obj,f);
                        }else if(Long.class.equals(field.getType())||long.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            long f= (long) fValue;
                            field.set(obj,f);
                        }else if(Float.class.equals(field.getType())||float.class.equals(field.getType())){
                            float fValue = Float.valueOf(e.getValue().toString());
                            field.set(obj,fValue);
                        }else if(Double.class.equals(field.getType())||double.class.equals(field.getType())){
                            double fValue = Double.valueOf(e.getValue().toString());
                            byte f= (byte) fValue;
                            field.set(obj,f);
                        }else if(Boolean.class.equals(field.getType())||boolean.class.equals(field.getType())){
                            boolean fValue = Boolean.parseBoolean(e.getValue().toString());
                            field.set(obj,fValue);
                        }
                    }
                }
            }
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }



}
