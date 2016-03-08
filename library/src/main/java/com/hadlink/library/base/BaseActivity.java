package com.hadlink.library.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.library.R;
import com.hadlink.library.base.presenter.ActivityPresenter;
import com.hadlink.library.base.view.IDelegate;
import com.hadlink.library.model.Event;

public abstract class BaseActivity<T extends IDelegate> extends ActivityPresenter<T> {
    protected LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        rootView = new LinearLayout(this);
        rootView.setOrientation(LinearLayout.VERTICAL);
        super.onCreate(savedInstanceState);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    localLayoutParams.flags);
        }
        super.setContentView(rootView);
    }

    @Override
    protected void initToolbar() {
        if (getToolbarAvailable()) {
            toolbar = viewDelegate.getToolbar();
            if (toolbar == null) {
                View topLayout = View.inflate(this, R.layout.base_toolbar, null);
                toolbar = (Toolbar) topLayout.findViewById(R.id.toolbar);
                toolbar.setTitle(getToolBarTitle());
                rootView.addView(topLayout, 0);
            }
            if (toolbar != null)
                setSupportActionBar(toolbar);
        }
    }

    protected String getToolBarTitle() {
        return null;
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootView.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

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
