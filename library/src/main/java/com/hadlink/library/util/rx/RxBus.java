package com.hadlink.library.util.rx;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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

    //////////////////////////////////////////////////////////////////////////////

    private ConcurrentHashMap<String, Result> pool = new ConcurrentHashMap<>();

    public Observable<Result> take(final String url) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                while (true) {
                    Result result = pool.get(url);
                    if (result != null) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                        break;
                    }
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 入队
     */
    public void put(String url, Map<String, String> header, byte[] data) {
        pool.put(url, new Result(header, data));
    }
}
