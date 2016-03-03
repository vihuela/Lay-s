package com.hadlink.library.util.rx;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


/**
 * 用于替换EventBus的RxBus实现,同时用做Http响应数据的分发
 * Note:实现思路来自http://www.jianshu.com/p/ca090f6e2fe2
 */
public class RxBus {

    private RxBus() {
    }

    private static volatile RxBus mInstance;

    public static RxBus getDefault() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void post(Object event) {
        bus.onNext(event);
    }

    public <T> Observable<T> take(final Class<T> eventType) {
        return bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }
        }).cast(eventType);
    }
    //usage
    /*private void setTabTitle(int position) {
        Event event = new Event();
        event.setAction(Event.SEND_TAB_TITLE_ACTION);
        CharSequence pageTitle = viewpager.getAdapter().getPageTitle(position);
        event.setObject(pageTitle);
        RxBus.getDefault().post(event);
    }*/
    /*rxBusSubscript = RxBus.getDefault().take(Event.class)
    .subscribe(new Action1<Event>() {
        @Override
        public void call(Event event) {
            changeContent(event);
        }
    }, new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
        }
    });*/
}
