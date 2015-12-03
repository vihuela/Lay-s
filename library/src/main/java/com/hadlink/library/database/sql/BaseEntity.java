package com.hadlink.library.database.sql;

import com.hadlink.library.database.help.ParamsType;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/4/24.
 */
public class BaseEntity implements Serializable {
    public String[] getKeyValues() {
        try {
            ArrayList<String> keyValue = new ArrayList<String>();
            Class clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f1 : fields) {
                if (f1.isAnnotationPresent(ParamsType.class)) { //有注释
                    ParamsType paramsType = f1.getAnnotation(ParamsType.class);
                    switch (paramsType.value()) {
                        case KEY:
                            f1.setAccessible(true);
                            keyValue.add(f1.get(this) + "");
                            break;
                        case FOREIGN_KEY:
                            f1.setAccessible(true);
                            keyValue.add(f1.get(this) + "");
                            break;
                        default:
                            break;
                    }
//                    String paramsStr = paramsType.value().toString();
//                    if (paramsStr.equals(ParamsType.Type.KEY.toString())) {
//                        f1.setAccessible(true);
//                        keyValue.add(f1.get(this) + "");
//                    } else if (paramsStr.equals(ParamsType.Type.FOREIGN_KEY.toString())) {
//                        f1.setAccessible(true);
//                        keyValue.add(f1.get(this) + "");
//                    }
                }
            }
            return (String[]) keyValue.toArray(new String[keyValue.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断json是否有改字段存在，并返回相应处理结果
     * @param jsonObject
     * @param jsonName
     * @return
     */
    public Object getJsonObject(JSONObject jsonObject, String jsonName) {
        try {
            if (jsonObject.isNull(jsonName)) {
                return null;
            } else {
                return jsonObject.get(jsonName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getJsonString(JSONObject jsonObject, String jsonName) {
        Object object = getJsonObject(jsonObject, jsonName);

        return object == null ? "" : object.toString();
    }

    /*
     * 以下所有语句，先转成String，再转成需要的。不然有可能异常，比如int无法转成long。
     */

    public int getJsonInt(JSONObject jsonObject, String jsonName) {
        Object object = getJsonObject(jsonObject, jsonName);

        return object == null ? 0 : Integer.valueOf(object.toString());
    }

    public long getJsonLong(JSONObject jsonObject, String jsonName) {
        Object object = getJsonObject(jsonObject, jsonName);

        return object == null ? 0 : Long.valueOf(object.toString());
    }

    public float getJsonFloat(JSONObject jsonObject, String jsonName) {
        Object object = getJsonObject(jsonObject, jsonName);

        return object == null ? 0 : Float.valueOf(object.toString());
    }

}
