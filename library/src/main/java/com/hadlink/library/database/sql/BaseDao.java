package com.hadlink.library.database.sql;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hadlink.library.database.help.BaseSqlParams;
import com.hadlink.library.database.help.ParamsType;
import com.hadlink.library.database.help.PrintC2bAndB2c;
import com.hadlink.library.database.help.SQL_CONS;
import com.hadlink.library.database.help.SqlUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 周 on 2015/4/23.
 * 数据库Dao基础类
 * 解析实体参数，构造基本数据库
 */
public abstract class BaseDao<A> implements BaseDataInterface<A> {
    public String TABLE_NAME;
    public String createTableSql;
    //存储主键或外键，用于构造默认查询条件语句
    public List<String> keys = new ArrayList<String>();
    //存储表字段
    List<BaseSqlParams> params = new ArrayList<BaseSqlParams>();
    BaseDbHelper mBaseDbHelper;
    public BaseDao() {
    }

    /**
     * 初始化表结构
     * @param db
     */
    public void init(SQLiteDatabase db,BaseDbHelper mBaseDbHelper) {
    	this.mBaseDbHelper = mBaseDbHelper;
        getParams();
        db.execSQL(generateCreateSql());
        judgeChange(db);
        PrintC2bAndB2c.b2cPrint(params, TABLE_NAME);
        PrintC2bAndB2c.c2bPrint(params, TABLE_NAME);
    }

    /**
     * 生成建表语句
     */
    private String generateCreateSql() {
        if (TABLE_NAME.toLowerCase().equals("eatdata")) {
            Log.d("rd62", "s");
        }
        StringBuilder key = new StringBuilder(SQL_CONS.PRIMARY_KEY + SQL_CONS.LEFT_BRACKET);
        String oldKey = key.toString();
        StringBuilder content = new StringBuilder();
        for (BaseSqlParams p : params) {
            content.append(p.getName());
            content.append(p.getSQLType());
            content.append(SQL_CONS.DOT);
            if (p.isKey()) {
                key.append(p.getName());
                key.append(SQL_CONS.DOT);
            }
        }
        if (key.toString().trim().equals(oldKey.trim())) {
            key = new StringBuilder("");
            content = content.deleteCharAt(content.length() - 1);
        } else {
            key = key.deleteCharAt(key.length() - 1);
            key.append(SQL_CONS.RIGHT_BRACKET);
        }
        createTableSql =
                SQL_CONS.CREATE_BEGIN + TABLE_NAME + SQL_CONS.LEFT_BRACKET + content.toString() + key.toString() + SQL_CONS.RIGHT_BRACKET;
        Log.d("rd65", createTableSql);
        return createTableSql;
    }

    /**
     * 判断表是否字段发生改变
     */
    private void judgeChange(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from "
                        + TABLE_NAME,
                new String[]{
                });
        String[] strs = cursor.getColumnNames();
        cursor.close();
        if (hasDeleteCloumn(strs, params)) {//如果数据有要删除的字段
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL(createTableSql);
        } else {//如果数据库有要添加的字段
            List<BaseSqlParams> addCloumns = comparaNewParams(strs, params);
            for (BaseSqlParams ba : addCloumns) {
                addTableCloumn(ba, db);
            }
        }
    }

    /**
     * 添加一个字段到表中
     */
    private void addTableCloumn(BaseSqlParams baseSqlParams, SQLiteDatabase db) {
        String sql = "ALTER TABLE " + TABLE_NAME +
                " ADD " + baseSqlParams.getName() + baseSqlParams.getSQLType();
        db.execSQL(sql);
    }

    /**
     * 是否有删除字段
     * @param strs
     * @param list
     * @return true表示数据库有要删除的字段
     */
    private boolean hasDeleteCloumn(String[] strs, List<BaseSqlParams> list) {
        if (strs == null || list == null || strs.length == 0 || list.size() == 0) {
            return false;
        }
        if (strs.length > list.size()) {
            return true;
        }
        oo:
        for (int i = 0; i < strs.length; i++) {
            for (BaseSqlParams ba : list) {
                if (ba.getName().equals(strs[i])) {
                    continue oo;
                }
            }
            //进到这里表示该strs在list中里面没有，表示该数据库有要删除的字段
            return true;
        }
        return false;
    }

    /**
     * 找出新的字段
     * @param strs
     * @param list
     * @return
     */
    public List<BaseSqlParams> comparaNewParams(String[] strs, List<BaseSqlParams> list) {
        if (strs == null || list == null || strs.length == 0 || list.size() == 0) {
            return null;
        }
        List<BaseSqlParams> result = new ArrayList<BaseSqlParams>();
        oo:
        for (BaseSqlParams ba : list) {
            for (int i = 0; i < strs.length; i++) {
                if (ba.getName().equals(strs[i])) {
                    continue oo;
                }
            }
            result.add(ba);
        }
        return result;
    }

    private BaseDao getBaseDao() {
        return this;
    }

    /**
     * 获取实体中的属性，将属性封装
     */
    private void getParams() {
        try {
            params.clear();
            keys.clear();
            //获取子类的实例
            BaseDao baseDao = getBaseDao();
            //获取子类的Class对象
            Class clazz = baseDao.getClass();
            //子类对象名
            String clazzName = clazz.getName().toLowerCase();
            //获取子类对象的属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                //如果属性的名字和子类的名字一致则找到了实体类
                if (clazzName.contains(f.getName().toLowerCase())) {
                    TABLE_NAME = f.getName();
                    //找到该实体类的Class对象
                    Class entityClass = Class.forName(f.getGenericType().toString().split(" ")[1]);
                    //获取属性
                    Field[] entityFields = entityClass.getDeclaredFields();
                    for (Field f1 : entityFields) {
                        if (f1.isAnnotationPresent(ParamsType.class)) { //有注释
                            ParamsType paramsType = f1.getAnnotation(ParamsType.class);
                            switch (paramsType.value()) {
                                case KEY:
                                    keys.add(f1.getName());
                                    //这个属性是主键
                                    params.add(new BaseSqlParams(f1.getName(), f1.getGenericType().toString(), true, true));
                                    break;
                                case FOREIGN_KEY:
                                    keys.add(f1.getName());//外键的时候，这里不要break;
                                case NORMAL:
                                    //这个属性是普通的字段
                                    params.add(new BaseSqlParams(f1.getName(), f1.getGenericType().toString(), false, false));
                                    break;
                                default:
                                    break;
                            }
//                            String paramsStr = paramsType.value().toString();
//                            if (paramsStr.equals(ParamsType.Type.KEY.toString())) {
//                                keys.add(f1.getName());
//                                //这个属性是主键
//                                params.add(new BaseSqlParams(f1.getName(), f1.getGenericType().toString(), true, true));
//                            } else if (paramsStr.equals(ParamsType.Type.NORMAL.toString()) || paramsStr.equals(ParamsType.Type.FOREIGN_KEY.toString())) {
//                                if (paramsStr.equals(ParamsType.Type.FOREIGN_KEY.toString())) {//外键的时候
//                                    keys.add(f1.getName());
//                                }
//                                //这个属性是普通的字段
//                                params.add(new BaseSqlParams(f1.getName(), f1.getGenericType().toString(), false, false));
//                            }
                        } else {
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCondition() {
        StringBuilder condition = new StringBuilder();
        for (String key : keys) {
            condition.append(key).append(SQL_CONS.EQUAL).append(SQL_CONS.QUEST).append(SQL_CONS.AND);
        }
        condition.delete(condition.length() - SQL_CONS.AND.length(), condition.length());
        return condition.toString();
    }

    /**
     * 查询按照条件查询,条件默认是由表关键字组成的
     * @param str
     * @return
     */
    protected A get(String[] str) {
        Cursor c = this.mBaseDbHelper.getDB().rawQuery("select * from " + TABLE_NAME
                        + " where " + getCondition(), str
        );
        return processCursorForOne(c);
    }

    /**
     * 替换{@link #getByCondition(String, Object...)}
     * 按条件查询
     * @param str       具体的查询条件值
     * @param condition 查询条件
     * @return
     */
    @Deprecated
    protected A getByCondition(String[] str, String condition) {
        Cursor c = this.mBaseDbHelper.getDB().rawQuery("select * from " + TABLE_NAME
                        + " where " + condition, str
        );
        return processCursorForOne(c);
    }

    /**
     * 按条件查询单条
     * @param condition 条件
     * @param args      替换条件中的问号
     * @return
     */
    protected A getByCondition(String condition, Object... args) {
        return getByCondition(SqlUtil.newString(args), condition);
    }

    /**
     * 将cursor转化为实体
     * @param c
     * @return
     */
    private A processCursorForOne(Cursor c) {
        try {
            if (c.moveToNext()) {
                return c2b(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return null;
    }

    /**
     * 按条件查询，条件为主键
     * @param str
     * @return
     */
    protected List<A> getAll(String[] str) {
        Cursor c = this.mBaseDbHelper.getDB().rawQuery("select * from " + TABLE_NAME
                        + " where " + getCondition(), str
        );
        return processCursorForAll(c);
    }

    /**
     * 替换{@link #getAllByCondition(String, Object...)}
     * 按条件查询，条件为主动填写
     * @param str
     * @param condition
     * @return
     */
    @Deprecated
    protected List<A> getAllByCondition(String[] str, String condition) {
        String sql = "select * from " + TABLE_NAME + " where " + condition;
        Cursor c = this.mBaseDbHelper.getDB().rawQuery(sql, str);
        List<A> result = processCursorForAll(c);
//        Lv.pDb("查询结果大小：" + result.size(), sql, Arrays.toString(str), "<<<>>>" + result);
        return result;
    }

    /**
     * 按条件查询多条
     * @param condition 条件
     * @param args      替换条件中的问号
     * @return
     */
    protected List<A> getAllByCondition(String condition, Object... args) {
        return getAllByCondition(SqlUtil.newString(args), condition);
    }

    /**
     * 处理cursor
     */
    protected List<A> processCursorForAll(Cursor c) {
        try {
            List<A> list = new ArrayList<A>();
            while (c.moveToNext()) {
                list.add(c2b(c));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
        }
        return null;
    }

    /**
     * 保存A
     * @param a
     * @return
     */
    @Override
    public boolean save(A a) {
        try {
            ContentValues c = b2c(a);
            this.mBaseDbHelper.getDB().replace(TABLE_NAME, getCondition(), c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存所有实体
     * @param list
     * @return
     */
    @Override
    public boolean saveAll(List<A> list) {
        for (A a : list) {
            save(a);
        }
        return true;
    }

    /**
     * 删除A
     * @param a
     * @return
     */
    @Override
    public boolean delete(A a) {
        try {
            String condition = getCondition();
            this.mBaseDbHelper.getDB().delete(TABLE_NAME, condition,
                    ((BaseEntity) a).getKeyValues()
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 替代：{@link #deleteAllByCondition(String, Object...)}
     * 按条件删除
     * @param strArr
     * @param condition
     * @return
     */
    @Deprecated
    public boolean deleteAllByCondition(String[] strArr, String condition) {
    	this.mBaseDbHelper.getDB().delete(TABLE_NAME, condition, strArr);
        return true;
    }

    /**
     * 按条件删除
     * @param condition 条件
     * @param args      替换条件中的问号
     * @return
     */
    public boolean deleteAllByCondition(String condition, Object... args) {
        return deleteAllByCondition(SqlUtil.newString(args), condition);
    }

    /**
     * 删除所有
     * @return
     */
    @Override
    public boolean deleteAll() {
    	this.mBaseDbHelper.getDB().delete(TABLE_NAME, null, new String[]{
        });
        return true;
    }

    /**
     * 将cursor转化为A
     * @param c
     * @return
     */
    public abstract A c2b(Cursor c);

    /**
     * 将A转化为ContentValues
     * @param a
     * @return
     */
    public abstract ContentValues b2c(A a);
}

