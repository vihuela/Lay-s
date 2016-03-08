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
