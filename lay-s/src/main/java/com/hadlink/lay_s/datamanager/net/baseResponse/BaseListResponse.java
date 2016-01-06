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
