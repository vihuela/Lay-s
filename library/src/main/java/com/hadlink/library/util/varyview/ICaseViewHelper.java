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

package com.hadlink.library.util.varyview;

import android.content.Context;
import android.view.View;

/**
 * 功能：切换页面的接口
 */
public interface ICaseViewHelper {

    /**
     * <p>获取上下文</p>
     *
     * @return Context
     */
    Context getContext();

    /**
     * <p>获取显示数据的View</p>
     *
     * @return View
     */
    View getDataView();

    /**
     * <p>获取当前正在显示的View</p>
     *
     * @return View
     */
    View getCurrentView();

    /**
     * <p>切换View</p>
     *
     * @param view 需要显示的View
     */
    void showCaseLayout(View view);

    /**
     * <p>切换View</p>
     *
     * @param layoutId 需要显示布局id
     */
    void showCaseLayout(int layoutId);

    /**
     * <p>恢复显示数据的View</p>
     */
    void restoreLayout();

    /**
     * <p>实例化布局</p>
     *
     * @param layoutId 需要实例化的布局id
     * @return View
     */
    View inflate(int layoutId);
}
