package com.hadlink.lay_s.ui.pojo.response;


import com.hadlink.library.net.impl.CommonResponse;

import java.util.List;

/**
 * @author Created by lyao on 2015/10/29.
 * @update
 * @description
 */
public class BaseListResponse<T> extends CommonResponse {

    /**
     * dataTotal : 37732
     * nextPage : true
     * pageNo : 1
     * pageNumEnd : 5
     * pageNumStart : 1
     * pageSize : 10
     * pageTotal : 3774
     * prevPage : false
     * showPageNum : 5
     * startOfPage : 0
     */

    public DataEntity data;

    public  class DataEntity {
        public int dataTotal;
        public boolean nextPage;
        public int pageNo;
        public int pageNumEnd;
        public int pageNumStart;
        public int pageSize;
        public int pageTotal;
        public boolean prevPage;
        public int showPageNum;
        public int startOfPage;
        /**
         * nickName : 1
         * avatarUrl : http://cdn.hoetoo.com//freeChat/2015/08/18/143991265320497925.png
         * awardScore : 0
         * createTime : 2015-08-19
         * gender : 0
         * msgCount : 0
         * questionContent : ，被可口可乐了
         * questionID : 17
         * stdName : 奥迪
         * tagName : 用车保养
         * userID : 19
         */

        public List<T> pageData;

//        public  class PageDataEntity {
//            public String nickName;
//            public String avatarUrl;
//            public int awardScore;
//            public String createTime;
//            public int gender;
//            public int msgCount;
//            public String questionContent;
//            public int questionID;
//            public String stdName;
//            public String tagName;
//            public int userID;
//        }
    }
}
