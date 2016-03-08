/*
 *    Copyright (c) 2016, lyao. lomoliger@hotmail.com
 *
 *     Part of the code from the open source community,
 *     thanks stackOverflow & gitHub .
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

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
