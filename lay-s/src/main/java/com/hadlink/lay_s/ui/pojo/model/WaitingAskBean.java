package com.hadlink.lay_s.ui.pojo.model;

/**
 * @author Created by lyao on 2015/11/29.
 * @update
 * @description
 */
public class WaitingAskBean {

    public int pageTotal;//总页数
    public int dataTotal;//数据总条数
    public boolean isFirst;//是否第一个条目，用于显示条目数布局

    public String nickName;
    public String avatarUrl;
    public int awardScore;
    public String createTime;
    public int gender;
    public int msgCount;
    public String questionContent;
    public int questionID;
    public String brandName;
    public String tagName;
    public int userID;

    private WaitingAskBean(boolean isFirst, int dataTotal, int pageTotal, String nickName, String avatarUrl, int awardScore, String createTime, int gender, int msgCount, String questionContent, int questionID, String brandName, String tagName, int userID) {
        this.isFirst = isFirst;
        this.dataTotal = dataTotal;
        this.pageTotal = pageTotal;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.awardScore = awardScore;
        this.createTime = createTime;
        this.gender = gender;
        this.msgCount = msgCount;
        this.questionContent = questionContent;
        this.questionID = questionID;
        this.brandName = brandName;
        this.tagName = tagName;
        this.userID = userID;
    }

    public static WaitingAskBean createWaitingAskBean(boolean isFirst, int dataTotal, int pageTotal, String nickName, String avatarUrl, int awardScore, String createTime, int gender, int msgCount, String questionContent, int questionID, String brandName, String tagName, int userID) {
        return new WaitingAskBean(isFirst, dataTotal, pageTotal, nickName, avatarUrl, awardScore, createTime, gender, msgCount, questionContent, questionID, brandName, tagName, userID);
    }
}
