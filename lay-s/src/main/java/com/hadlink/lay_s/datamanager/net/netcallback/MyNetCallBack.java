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

package com.hadlink.lay_s.datamanager.net.netcallback;

import com.hadlink.easynet.conf.ErrorInfo;
import com.hadlink.easynet.util.NetUtils;
import com.hadlink.lay_s.model.EventImpl;
import com.hadlink.library.model.Event;
import com.hadlink.library.util.rx.RxBus;

public abstract class MyNetCallBack<T> extends NetUtils.callBack<T> {

    public MyNetCallBack(String eventTag) {
        super(eventTag);
    }

    public MyNetCallBack() {
        super(null);
    }

    /**
     * 除了Invalid 返回的是原对象之外，其余都是String
     */
    @Override public void onDispatchError(Error error, ErrorInfo e) {

        switch (error) {
            case Internal:
                break;
            case Invalid:
                /**
                 * 当数据无效时候你做的处理
                 */
                T result = (T) e.getObject();

                break;
            case NetWork:
                break;
            case Server:
                break;
            case UnKnow:
                break;
        }
        //post event
        Event event = new EventImpl();
        event.arg = EventImpl.NET_REQUEST_ERROR;
        event.setObject(e);
        RxBus.getDefault().post(event);
    }
}
