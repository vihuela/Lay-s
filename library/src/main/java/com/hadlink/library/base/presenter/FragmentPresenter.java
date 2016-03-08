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
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hadlink.library.R;
import com.hadlink.library.base.view.IDelegate;
import com.hadlink.library.model.Event;
import com.hadlink.library.util.rx.RxBus;
import com.hadlink.library.util.varyview.VaryViewHelper;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Subscription;
import rx.functions.Action1;


/**
 * Presenter base class for Fragment
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 */
public abstract class FragmentPresenter<T extends IDelegate> extends RxFragment {
    protected T viewDelegate;
    protected Context context;
    protected String logTag;
    protected VaryViewHelper varyViewHelper;
    protected Handler handler = new Handler(Looper.getMainLooper());
    protected String netTag;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;
    private Subscription rxSubscribe;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        logTag = getClass().getSimpleName();
        if (getArguments() != null) onArguments(getArguments());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDelegate.create(inflater, container, savedInstanceState);
        return viewDelegate.getRootView();
    }

    @SuppressLint("InflateParams") @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDelegate.initWidget();
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
        bindEvenListener();
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewDelegate.getOptionsMenuId() != 0) {
            inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindBus()) {
            if (rxSubscribe != null && rxSubscribe.isUnsubscribed()) rxSubscribe.unsubscribe();
        }
        if (viewDelegate.getLoadingTargetView() != null) varyViewHelper.releaseVaryView();
        viewDelegate.destroy();
        viewDelegate = null;
        handler.removeCallbacksAndMessages(null);
    }


    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * when fragment is invisible for the first time
     */
    private void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    /**
     * ------------------------------------------------开放以下方法-------------------------------------------------------------
     */


    /**
     * 代理的视图类
     */
    protected abstract Class<T> getDelegateClass();

    /**
     * 是否需要rxBus
     */
    protected boolean bindBus() {
        return false;
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
     * 如果有设置loadingView，加载失败时重试点击的回调
     *
     * @see IDelegate #getLoadingTargetView()
     */
    protected void onRetryListener() {
    }

    /**
     * 当fragment首次可见时回调
     */
    protected void onFirstUserVisible() {
    }

    /**
     * 当fragment可见时回调
     */
    protected void onUserVisible() {
    }


    /**
     * 当fragment不可见时回调
     */
    protected void onUserInvisible() {
    }

    /**
     * 当需要获取传递给fragment的参数时回调
     */
    protected void onArguments(Bundle arguments) {
    }
}
