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

package com.hadlink.lay_s.datamanager.net.baseResponse;


import com.hadlink.easynet.impl.CommonResponse;

import java.util.List;

/**
 * @author Created by lyao on 2015/10/29.
 * @update
 * @description
 */
public class BaseListResponse<T> implements CommonResponse<List<T>> {

    public long code;
    public String message;
    public DataEntity data;

    @Override public List<T> getResult() {
        return data.pageData;
    }

    @Override public void setResult(List<T> ts) {
        this.data.pageData = ts;
    }

    @Override public boolean isValid() {
        return code == 200;
    }

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
        public List<T> pageData;
    }

}
