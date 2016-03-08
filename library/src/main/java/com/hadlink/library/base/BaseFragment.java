package com.hadlink.library.base;

import android.text.TextUtils;
import android.widget.Toast;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.library.base.presenter.FragmentPresenter;
import com.hadlink.library.base.view.IDelegate;
import com.hadlink.library.model.Event;

/**
 * @author Created by lyao on 2016/3/8.
 * @description
 */
public abstract class BaseFragment<T extends IDelegate> extends FragmentPresenter<T> {
    @Override protected void onEvent(int what, Object obj) {
        switch (what) {
            case Event.NET_REQUEST_ERROR:
                String eventTag = ((ErrorInfo) obj).getEventTag();
                if (TextUtils.equals(eventTag, netTag)) {
                    onNetError((ErrorInfo) obj);
                }
                break;
        }
    }

    /**
     * 记得设置netTag
     * 此回调有限制，仅适合我自己写的网络
     */
    protected void onNetError(ErrorInfo errorInfo) {
        switch (errorInfo.getError()) {
            case NetWork:
            case Internal:
            case Server:
            case UnKnow:
                Toast.makeText(context, errorInfo.getObject().toString(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
