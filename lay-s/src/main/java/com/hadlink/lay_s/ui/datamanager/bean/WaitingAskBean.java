package com.hadlink.lay_s.ui.datamanager.bean;


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

    public WaitingAskBean(){}

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

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(int dataTotal) {
        this.dataTotal = dataTotal;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAwardScore() {
        return awardScore;
    }

    public void setAwardScore(int awardScore) {
        this.awardScore = awardScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
