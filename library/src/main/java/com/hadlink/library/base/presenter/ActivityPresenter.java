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

package com.hadlink.library.base.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hadlink.library.R;
import com.hadlink.library.base.view.IDelegate;
import com.hadlink.library.model.Event;
import com.hadlink.library.util.rx.RxBus;
import com.hadlink.library.util.varyview.VaryViewHelper;
import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Subscription;
import rx.functions.Action1;


/**
 * Presenter base class for Activity
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 */
public abstract class ActivityPresenter<T extends IDelegate> extends RxAppCompatActivity {
    protected T viewDelegate;
    protected Context context;
    protected VaryViewHelper varyViewHelper;
    protected Handler handler = new Handler(Looper.getMainLooper());
    protected String netTag;
    /**
     * 这里toolBar的配置可以配合策略模式设置几种模板，这里不展开
     */
    protected Toolbar toolbar;
    private Subscription rxSubscribe;

    public ActivityPresenter() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    @SuppressLint("InflateParams") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        initToolbar();
        viewDelegate.initWidget();
        bindEvenListener();

        if (bindBus()) {
            rxSubscribe = RxBus.getDefault().take(Event.class)
                    .subscribe(new Action1<Event>() {
                        @Override
                        public void call(Event event) {
                            onEvent(event.arg, event.getObject());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                        }
                    });
        }

        if (viewDelegate.getLoadingTargetView() != null) {
            varyViewHelper = new VaryViewHelper.Builder()
                    .setDataView(viewDelegate.getLoadingTargetView())
                    .setLoadingView(LayoutInflater.from(context).inflate(R.layout.layout_loadingview, null))
                    .setEmptyView(LayoutInflater.from(context).inflate(R.layout.layout_emptyview, null))
                    .setErrorView(LayoutInflater.from(context).inflate(R.layout.layout_errorview, null))
                    .setRefreshListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            onRetryListener();
                        }
                    })
                    .build();
        }

        Bundle extras = getIntent().getExtras();

        if (null != extras) {
            getBundleExtras(extras);
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IDelegate error");
            }
        }
    }


    protected void initToolbar() {
        if (getToolbarAvailable()) {
            toolbar = viewDelegate.getToolbar();
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
            onUseIconifySetMenuItem(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindBus()) {
            if (rxSubscribe != null && rxSubscribe.isUnsubscribed()) rxSubscribe.unsubscribe();
        }
        if (viewDelegate.getLoadingTargetView() != null) varyViewHelper.releaseVaryView();
        viewDelegate.destroy();
        viewDelegate = null;
        handler.removeCallbacksAndMessages(null);
    }

    protected final void setMenuItem(Icon icon, int color, MenuItem menuItem) {
        menuItem.setIcon(
                new IconDrawable(this, icon)
                        .colorRes(color)
                        .actionBarSize());
    }

    /**
     * ------------------------------------------------开放以下方法-------------------------------------------------------------
     */

    /**
     * 绑定哪个视图类
     */
    protected abstract Class<T> getDelegateClass();


    /**
     * 是否需要rxBus
     */
    protected boolean bindBus() {
        return false;
    }

    /**
     * 如果有设置loadingView，加载失败时重试点击的回调
     *
     * @see IDelegate #getLoadingTargetView()
     */
    protected void onRetryListener() {
    }

    /**
     * rxBus事件回调，根据what判断时间类型
     *
     * @param what 事件类型
     * @param obj  携带的对象
     */
    protected void onEvent(int what, Object obj) {

    }

    /**
     * 初始化一些监听等
     */
    protected void bindEvenListener() {
    }

    /**
     * 接收bundle回调
     */
    protected void getBundleExtras(Bundle extras) {
    }

    /**
     * 是否启用toolBar
     */
    protected boolean getToolbarAvailable() {
        return true;
    }

    /**
     * only Iconify use
     */
    protected void onUseIconifySetMenuItem(Menu menu) {
    }
}
