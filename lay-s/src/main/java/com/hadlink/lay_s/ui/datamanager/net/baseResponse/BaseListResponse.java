package com.hadlink.lay_s.ui.datamanager.net.baseResponse;


import com.hadlink.library.net.impl.CommonResponse;

import java.util.List;

/**
 * @author Created by lyao on 2015/10/29.
 * @update
 * @description
 */
public class BaseListResponse implements CommonResponse {

    public long code;
    public String message;
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
        public List pageData;
    }

    public List getResult(){
        return data.pageData;
    }
    @Override public void setResult(Object t) {
        data.pageData = (List) t;
    }
}
