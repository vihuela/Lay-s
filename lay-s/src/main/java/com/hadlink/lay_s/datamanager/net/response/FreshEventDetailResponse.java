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

package com.hadlink.lay_s.datamanager.net.response;

import android.text.TextUtils;

import com.hadlink.easynet.impl.CommonResponse;

/**
 * @author Created by lyao on 2016/1/4.
 * @update
 * @description
 */
public class FreshEventDetailResponse implements CommonResponse {

    /**
     * status : ok
     * previous_url : http://jandan.net/2016/01/04/apollo-astronaut-life-insurance.html
     * next_url : http://jandan.net/2016/01/04/under-100000-pounds.html
     */

    public String status;
    /**
     * id : 73580

     */

    public PostEntity post;
    public String previous_url;
    public String next_url;

    @Override public Object getResult() {
        return null;
    }

    @Override public void setResult(Object o) {

    }

    @Override public boolean isValid() {
        return TextUtils.equals("ok", status);
    }

    public static class PostEntity {
        public int id;
        public String content;
    }
}
