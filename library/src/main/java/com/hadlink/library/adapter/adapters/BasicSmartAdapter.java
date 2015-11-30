package com.hadlink.library.adapter.adapters;

import android.support.annotation.UiThread;
import android.view.View;

import com.hadlink.library.adapter.utils.ViewEventListener;

import java.util.List;


/**
 * Common methods for adapters added by the library
 */
public interface BasicSmartAdapter {
    @UiThread void setItems(List items);

    @UiThread void addItem(Object item);

    @UiThread void delItem(Object item);

    @UiThread void addItems(List items);

    @UiThread void clearItems();

    ViewEventListener getViewEventListener();

    void setViewEventListener(ViewEventListener viewEventListener);

    void notifyAction(int actionId, Object object, int position, View view);

    void setAutoDataSetChanged(boolean enabled);
}
