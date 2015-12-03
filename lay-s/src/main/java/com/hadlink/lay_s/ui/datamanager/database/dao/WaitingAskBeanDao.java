package com.hadlink.lay_s.ui.datamanager.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.hadlink.lay_s.ui.datamanager.bean.WaitingAskBean;
import com.hadlink.library.database.sql.BaseDao;

/**
 * Created by zhouml on 2015/12/2.
 */
public class WaitingAskBeanDao extends BaseDao<WaitingAskBean> {

    WaitingAskBean waitingAskBean;

    private WaitingAskBeanDao() {
    }

    private static WaitingAskBeanDao instance;

    public static WaitingAskBeanDao getInstance() {
        if (instance == null) {
            synchronized (WaitingAskBeanDao.class) {
                if (instance == null) {
                    instance = new WaitingAskBeanDao();
                }
            }
        }
        return instance;
    }


    @Override public ContentValues b2c(WaitingAskBean b) {
        ContentValues c = new ContentValues();
        c.put("avatarUrl", b.getAvatarUrl());
        c.put("tagName", b.getTagName());
        c.put("brandName", b.getBrandName());
        c.put("createTime", b.getCreateTime());
        c.put("questionContent", b.getQuestionContent());
        c.put("nickName", b.getNickName());
        c.put("msgCount", b.getMsgCount());
        c.put("gender", b.getGender());
        c.put("questionID", b.getQuestionID());
        c.put("awardScore", b.getAwardScore());
        c.put("userID", b.getUserID());
        return c;
    }

    @Override public WaitingAskBean c2b(Cursor c) {
        WaitingAskBean b = new WaitingAskBean();
        b.setAvatarUrl(c.getString(c.getColumnIndex("avatarUrl")));
        b.setTagName(c.getString(c.getColumnIndex("tagName")));
        b.setBrandName(c.getString(c.getColumnIndex("brandName")));
        b.setCreateTime(c.getString(c.getColumnIndex("createTime")));
        b.setQuestionContent(c.getString(c.getColumnIndex("questionContent")));
        b.setNickName(c.getString(c.getColumnIndex("nickName")));
        b.setMsgCount(c.getInt(c.getColumnIndex("msgCount")));
        b.setGender(c.getInt(c.getColumnIndex("gender")));
        b.setQuestionID(c.getInt(c.getColumnIndex("questionID")));
        b.setAwardScore(c.getInt(c.getColumnIndex("awardScore")));
        b.setUserID(c.getInt(c.getColumnIndex("userID")));
        return b;
    }

}
