package com.hadlink.lay_s.datamanager.bean;

import java.util.List;

/**
 * @author Created by lyao on 2016/1/4.
 * @update
 * @description
 */
public class FreshEvent {
    /**
     * description :
     * first_name : Cedric
     * id : 587
     * last_name : Hsu
     * name : Cedric
     * nickname : Cedric
     * slug : cedric
     * url :
     */

    public AuthorEntity author;
    public int comment_count;
    public CustomFieldsEntity custom_fields;
    public String date;
    public int id;
    public String title;
    public String url;
    /**
     * description :
     * id : 687
     * post_count : 2089
     * slug : %e6%97%a0%e5%8e%98%e5%a4%b4%e7%a0%94%e7%a9%b6
     * title : 无厘头研究
     */

    public List<TagsEntity> tags;

    public static class AuthorEntity {
        public String description;
        public String first_name;
        public int id;
        public String last_name;
        public String name;
        public String nickname;
        public String slug;
        public String url;
    }

    public static class CustomFieldsEntity {
        public List<String> thumb_c;
        public List<String> views;
    }

    public static class TagsEntity {
        public String description;
        public int id;
        public int post_count;
        public String slug;
        public String title;
    }
}
