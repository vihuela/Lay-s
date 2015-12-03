package com.hadlink.lay_s.ui.datamanager.net.baseResponse;

import com.hadlink.library.net.impl.CommonResponse;

/**
 * Created by zhouml on 2015/12/3.
 */
public class BaseBeanResponse implements CommonResponse{
    /**
     * examineStatus : 1
     * occupationCardThree : null
     * occupationCardOne : http://res.imchehu.com/expert/img/auth/220477925311539.png
     * occupationCardTow : http://res.imchehu.com/expert/img/auth/220602744347064.png
     * identityCard : http://res.imchehu.com/expert/img/auth/220469996328261.png
     */

    public Object data;
    public long code;
    public String message;

//    "examineStatus":1,"occupationCardThree":null,"occupationCardOne":"http://res.imchehu.com/expert/img/auth/220477925311539.png","occupationCardTow":"http://res.imchehu.com/expert/img/auth/220602744347064.png","identityCard":"http://res.imchehu.com/expert/img/auth/220469996328261.png"




    @Override public Object getResult() {
        return data;
    }

    @Override public void setResult(Object t) {
        data = t;
    }


}
