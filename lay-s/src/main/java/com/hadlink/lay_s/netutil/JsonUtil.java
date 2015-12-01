package com.hadlink.lay_s.netutil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author lyao
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonUtil {

    // 反射 int->Integer.TYPE Integer->Integer.class
    public static <T> T convertFromObject(JSONObject jsonObject,
                                          Class<T> targetClass) throws Exception {
        return convertFromObject(jsonObject, targetClass, null);
    }

    protected static <T> T convertFromObject(JSONObject jsonObject,
                                             Class<T> targetClass, Type genericType) throws Exception {
        if (targetClass == String.class) {
            return (T) jsonObject.toString();
        }
        T target = targetClass.newInstance();
        convertFromObject(jsonObject, target, genericType);
        return target;
    }

    protected static <T> void convertFromObject(JSONObject jsonObject,
                                                T target, Type genericType) throws Exception {
        if (target instanceof Map) {
            convertFromMap(jsonObject, (Map<?, ?>) target, genericType);
            return;
        }
        Class<?> targetClass = target.getClass();
        JsonField[] targetFields = JsonField.getFields(targetClass);
        for (JsonField field : targetFields) {
            String fieldName = field.getName();
            if (!jsonObject.has(fieldName)) {
                continue;
            }
            Class<?> fieldClass = field.getType();
            if (fieldClass == String.class) {
                field.set(target, jsonObject.getString(fieldName));
            } else if (fieldClass == Integer.TYPE
                    || fieldClass == Integer.class) {
                field.set(target, jsonObject.getInt(fieldName));
            } else if (fieldClass == Long.TYPE || fieldClass == Long.class) {
                field.set(target, jsonObject.getLong(fieldName));
            } else if (fieldClass == Double.TYPE || fieldClass == Double.class) {
                field.set(target, jsonObject.getDouble(fieldName));
            } else if (fieldClass == Boolean.TYPE
                    || fieldClass == Boolean.class) {
                field.set(target, jsonObject.getBoolean(fieldName));
            } else if (List.class.isAssignableFrom(fieldClass)) {
                try {
                    field.set(target, convertFromArray(jsonObject.getJSONArray(fieldName), fieldClass, field.getGenericType()));
                } catch (org.json.JSONException e) {
                    /*避免值为空字符串引起解析异常*/
                    field.set(target, null);
                }
            } else {
                field.set(
                        target,
                        convertFromObject(jsonObject.getJSONObject(fieldName),
                                fieldClass, field.getGenericType()));
            }
        }
    }

    public static <T> T convertFromArray(JSONArray jsonArray,
                                         Class<T> targetClass) throws Exception {
        return convertFromArray(jsonArray, targetClass, null);
    }

    protected static <T> T convertFromArray(JSONArray jsonArray,
                                            Class<T> targetClass, Type genericType) throws Exception {
        if (targetClass == String.class) {
            return (T) jsonArray.toString();
        }
        T target = targetClass.newInstance();
        convertFromArray(jsonArray, (List<?>) target, genericType);
        return target;
    }

    protected static <T extends List> void convertFromArray(
            JSONArray jsonArray, T target, Type genericType) throws Exception {
        Class<? extends List> targetClass = target.getClass();
        Class<?> elementClass = getListElementClass(targetClass, genericType);
        for (int i = 0, size = jsonArray.length(); i < size; i++) {
            if (elementClass == String.class) {
                target.add(jsonArray.getString(i));
            } else if (elementClass == Integer.class) {
                target.add(jsonArray.getInt(i));
            } else if (elementClass == Long.class) {
                target.add(jsonArray.getLong(i));
            } else if (elementClass == Double.class) {
                target.add(jsonArray.getDouble(i));
            } else if (elementClass == Boolean.class) {
                target.add(jsonArray.getBoolean(i));
            } else if (List.class.isAssignableFrom(elementClass)) {
                target.add(convertFromArray(jsonArray.getJSONArray(i),
                        elementClass,
                        getListElementType(targetClass, genericType)));
            } else {
                target.add(convertFromObject(jsonArray.getJSONObject(i),
                        elementClass,
                        getListElementType(targetClass, genericType)));
            }
        }
    }

    protected static <T> T convertFromMap(JSONObject jsonObject,
                                          Class<T> targetClass, Type genericType) throws Exception {
        if (targetClass == String.class) {
            return (T) jsonObject.toString();
        }
        T target = targetClass.newInstance();
        convertFromMap(jsonObject, (Map<?, ?>) target, genericType);
        return target;
    }

    protected static <T extends Map> void convertFromMap(JSONObject jsonObject,
                                                         T target, Type genericType) throws Exception {
        Class<? extends Map> targetClass = target.getClass();
        Class<?> keyClass = getMapKeyClass(targetClass, genericType);
        if (!String.class.isAssignableFrom(keyClass)) {
            throw new Exception();
        }
        Class<?> elementClass = getMapElementClass(targetClass, genericType);
        Iterator<?> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next().toString();
            if (elementClass == String.class) {
                target.put(key, jsonObject.getString(key));
            } else if (elementClass == Integer.class) {
                target.put(key, jsonObject.getInt(key));
            } else if (elementClass == Long.class) {
                target.put(key, jsonObject.getLong(key));
            } else if (elementClass == Double.class) {
                target.put(key, jsonObject.getDouble(key));
            } else if (elementClass == Boolean.class) {
                target.put(key, jsonObject.getBoolean(key));
            } else if (List.class.isAssignableFrom(elementClass)) {
                target.put(
                        key,
                        convertFromArray(jsonObject.getJSONArray(key),
                                elementClass,
                                getMapElementType(targetClass, genericType)));
            } else {
                target.put(
                        key,
                        convertFromObject(jsonObject.getJSONObject(key),
                                elementClass,
                                getMapElementType(targetClass, genericType)));
            }
        }
    }

    protected static Class<?> getListElementClass(Class<?> listClass,
                                                  Type genericType) {
        if (genericType == null) {
            genericType = listClass.getGenericSuperclass();
        } else {
            if (!(genericType instanceof ParameterizedType)) {
                genericType = listClass.getGenericSuperclass();
            }
        }
        if (genericType instanceof ParameterizedType) {
            Type t = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if (t instanceof Class) {
                return (Class<?>) t;
            }
            if (t instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) t).getRawType();
            }
        }
        return String.class;
    }

    protected static Type getListElementType(Class<?> listClass,
                                             Type genericType) {
        if (genericType == null) {
            genericType = listClass.getGenericSuperclass();
        }
        if (genericType instanceof ParameterizedType) {
            return ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
        return String.class;
    }

    public static Class<?> getMapKeyClass(Class<?> mapClass, Type genericType) {
        if (genericType == null) {
            genericType = mapClass.getGenericSuperclass();
        } else {
            if (!(genericType instanceof ParameterizedType)) {
                genericType = mapClass.getGenericSuperclass();
            }
        }
        if (genericType instanceof ParameterizedType) {
            Type t = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            if (t instanceof Class) {
                return (Class<?>) t;
            }
        }
        return Void.class;
    }

    public static Class<?> getMapElementClass(Class<?> mapClass,
                                              Type genericType) {
        if (genericType == null) {
            genericType = mapClass.getGenericSuperclass();
        } else {
            if (!(genericType instanceof ParameterizedType)) {
                genericType = mapClass.getGenericSuperclass();
            }
        }
        if (genericType instanceof ParameterizedType) {
            Type t = ((ParameterizedType) genericType).getActualTypeArguments()[1];
            if (t instanceof Class) {
                return (Class<?>) t;
            }
            if (t instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) t).getRawType();
            }
        }
        return String.class;
    }

    public static Type getMapElementType(Class<?> mapClass, Type genericType) {
        if (genericType == null) {
            genericType = mapClass.getGenericSuperclass();
        }
        if (genericType instanceof ParameterizedType) {
            return ((ParameterizedType) genericType).getActualTypeArguments()[1];
        }
        return String.class;
    }

    public static <T> T convert(String str, Class<T> targetClass)
            throws Exception {
        if (str == null) {
            return null;
        }
        if (targetClass == String.class) {
            return (T) str;
        }
        T target = targetClass.newInstance();
        convert(str, target);
        return target;
    }

    public static <T> void convert(String str, T target) throws Exception {
        JSONTokener jsonTokener = new JSONTokener(str);
        Object v = jsonTokener.nextValue();
        if (v instanceof JSONObject) {
            convertFromObject((JSONObject) v, target, null);
        } else if (v instanceof JSONArray) {
            convertFromArray((JSONArray) v, (List<?>) target, null);
        } else {
            throw new Exception();
        }
    }

    public static <T> String convert(T target) throws Exception {
        if (target == null) {
            return null;
        }
        if (target instanceof String) {
            return (String) target;
        }
        if (target instanceof Integer) {
            return target.toString();
        }
        if (target instanceof Long) {
            return target.toString();
        }
        if (target instanceof Double) {
            return target.toString();
        }
        if (target instanceof Boolean) {
            return target.toString();
        }
        return ((target instanceof List) ? convertToArray(target)
                : convertToObject(target)).toString();
    }

    public static <T> JSONObject convertToObject(T target) throws Exception {
        if (target instanceof Map) {
            return convertToMap(target);
        }
        JSONObject jsonObject = new JSONObject();
        Class<?> targetClass = target.getClass();
        JsonField[] targetFields = JsonField.getFields(targetClass);
        for (JsonField field : targetFields) {
            Object fieldValue = field.get(target);
            if (fieldValue == null) {
                continue;
            }
            String fieldName = field.getName();
            Class<?> fieldClass = field.getType();
            if (fieldClass == String.class) {
                jsonObject.put(fieldName, fieldValue);
            } else if (fieldClass == Integer.TYPE
                    || fieldClass == Integer.class) {
                jsonObject.put(fieldName, fieldValue);
            } else if (fieldClass == Long.TYPE || fieldClass == Long.class) {
                jsonObject.put(fieldName, fieldValue);
            } else if (fieldClass == Double.TYPE || fieldClass == Double.class) {
                jsonObject.put(fieldName, fieldValue);
            } else if (fieldClass == Boolean.TYPE
                    || fieldClass == Boolean.class) {
                jsonObject.put(fieldName, fieldValue);
            } else if (List.class.isAssignableFrom(fieldClass)) {
                jsonObject.put(fieldName, convertToArray(fieldValue));
            } else {
                jsonObject.put(fieldName, convertToObject(fieldValue));
            }
        }
        return jsonObject;
    }

    public static <T> JSONArray convertToArray(T target) throws Exception {
        List<?> targetList = (List<?>) target;
        JSONArray jsonArray = new JSONArray();
        for (Object element : targetList) {
            Class<?> elementClass = element.getClass();
            if (elementClass == String.class) {
                jsonArray.put(element);
            } else if (elementClass == Integer.class) {
                jsonArray.put(element);
            } else if (elementClass == Long.class) {
                jsonArray.put(element);
            } else if (elementClass == Double.class) {
                jsonArray.put(element);
            } else if (elementClass == Boolean.class) {
                jsonArray.put(element);
            } else if (List.class.isAssignableFrom(elementClass)) {
                jsonArray.put(convertToArray(element));
            } else {
                jsonArray.put(convertToObject(element));
            }
        }
        return jsonArray;
    }

    protected static <T> JSONObject convertToMap(T target) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<?, ?> targetMap = (Map<?, ?>) target;
        boolean keyChecked = false;
        for (Entry<?, ?> entry : targetMap.entrySet()) {
            Object keyObject = entry.getKey();
            if (!keyChecked) {
                if (!(keyObject instanceof String)) {
                    break;
                }
                keyChecked = true;
            }
            String key = keyObject.toString();
            Object element = entry.getValue();
            Class<?> elementClass = element.getClass();
            if (elementClass == String.class) {
                jsonObject.put(key, targetMap.get(key));
            } else if (elementClass == Integer.class) {
                jsonObject.put(key, targetMap.get(key));
            } else if (elementClass == Long.class) {
                jsonObject.put(key, targetMap.get(key));
            } else if (elementClass == Double.class) {
                jsonObject.put(key, targetMap.get(key));
            } else if (elementClass == Boolean.class) {
                jsonObject.put(key, targetMap.get(key));
            } else if (List.class.isAssignableFrom(elementClass)) {
                jsonObject.put(key, convertToArray(targetMap.get(key)));
            } else {
                jsonObject.put(key, convertToObject(targetMap.get(key)));
            }
        }
        return jsonObject;
    }

    public static class JsonField {

        protected static final String GET_PREFIX = "get";
        protected static final String SET_PREFIX = "set";
        protected static final String IS_PREFIX = "is";
        protected static Map<Class<?>, JsonField[]> sFieldsCache = new HashMap<Class<?>, JsonField[]>();
        protected static Object sSync = new Object();
        protected Field mField;
        protected Method mGetMethod;
        protected Method mSetMethod;
        protected Class<?> mType;
        protected String mName;
        protected Type mGenericType;

        protected JsonField() {

        }

        protected static String getFieldNameFromMethod(String method,
                                                       int startIndex) {
            char[] cs = new char[method.length() - startIndex];
            for (int i = startIndex, j = 0, size = method.length(); i < size; i++, j++) {
                char c = method.charAt(i);
                if (i == startIndex) {
                    c = Character.toLowerCase(c);
                }
                cs[j] = c;
            }
            return new String(cs);
        }

        protected static JsonField[] generateFields(Class<?> cls) {
            List<JsonField> fields = new ArrayList<JsonField>();
            Map<String, Object> fieldsMap = new HashMap<String, Object>();
            Field[] fs = cls.getFields();
            for (Field f : fs) {
                int modifiers = f.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                try {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                } catch (Exception e) {

                }
                String name = f.getName();
                fieldsMap.put(name, null);
                JsonField field = new JsonField();
                field.setGenericType(f.getGenericType());
                field.setType(f.getType());
                field.setName(f.getName());
                field.setField(f);
                fields.add(field);
            }
            Method[] ms = cls.getMethods();
            List<Method> setList = new ArrayList<Method>();
            List<Method> getList = new LinkedList<Method>();
            for (Method m : ms) {
                int modifiers = m.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                String name = m.getName();
                Class<?>[] paramTypes = m.getParameterTypes();
                Class<?> returnType = m.getReturnType();
                if (name.startsWith(SET_PREFIX)) {
                    if (paramTypes.length != 1 || returnType != Void.TYPE) {
                        continue;
                    }
                    setList.add(m);
                }
                if (name.startsWith(GET_PREFIX)) {
                    if (paramTypes.length != 0 || returnType == Void.TYPE) {
                        continue;
                    }
                    getList.add(m);
                }
                if (name.startsWith(IS_PREFIX)) {
                    if (paramTypes.length != 0
                            || (returnType != Boolean.TYPE && returnType != Boolean.class)) {
                        continue;
                    }
                    getList.add(m);
                }
            }
            for (Method sm : setList) {
                String sName = sm.getName();
                sName = getFieldNameFromMethod(sName, 3);
                if (fieldsMap.containsKey(sName)) {
                    continue;
                }
                Class<?> sType = sm.getParameterTypes()[0];
                for (Method gm : getList) {
                    Class<?> gType = gm.getReturnType();
                    if (gType != sType) {
                        continue;
                    }
                    String gName = gm.getName();
                    if (gType == Boolean.TYPE || gType == Boolean.class) {
                        gName = getFieldNameFromMethod(gName, 2);
                    } else {
                        gName = getFieldNameFromMethod(gName, 3);
                    }
                    if (sName.equals(gName)) {
                        try {
                            if (!sm.isAccessible()) {
                                sm.setAccessible(true);
                            }
                            if (!gm.isAccessible()) {
                                gm.setAccessible(true);
                            }
                        } catch (Exception e) {

                        }
                        JsonField field = new JsonField();
                        field.setGenericType(gm.getGenericReturnType());
                        field.setName(sName);
                        field.setType(sType);
                        field.setGetMethod(gm);
                        field.setSetMethod(sm);
                        fields.add(field);
                        getList.remove(gm);
                        break;
                    }
                }
            }
            setList.clear();
            getList.clear();
            fieldsMap.clear();
            JsonField[] fieldsArray = new JsonField[fields.size()];
            fields.toArray(fieldsArray);
            fields.clear();
            return fieldsArray;
        }

        public static JsonField[] getFields(Class<?> cls) {
            synchronized (sSync) {
                JsonField[] fields = sFieldsCache.get(cls);
                if (fields == null) {
                    fields = generateFields(cls);
                    sFieldsCache.put(cls, fields);
                }
                return fields;
            }
        }

        public Type getGenericType() {
            return mGenericType;
        }

        protected void setGenericType(Type genericType) {
            mGenericType = genericType;
        }

        public Class<?> getType() {
            return mType;
        }

        protected void setType(Class<?> cls) {
            mType = cls;
        }

        public String getName() {
            return mName;
        }

        protected void setName(String name) {
            mName = name;
        }

        public Object get(Object object) throws Exception {
            if (mField != null) {
                return mField.get(object);
            } else {
                return mGetMethod.invoke(object);
            }
        }

        public void set(Object object, Object value) throws Exception {
            if (mField != null) {
                mField.set(object, value);
            } else {
                mSetMethod.invoke(object, value);
            }
        }

        protected void setField(Field field) {
            mField = field;
        }

        protected void setGetMethod(Method method) {
            mGetMethod = method;
        }

        protected void setSetMethod(Method method) {
            mSetMethod = method;
        }
    }
}
