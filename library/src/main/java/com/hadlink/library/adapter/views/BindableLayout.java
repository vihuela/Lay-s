package com.hadlink.library.adapter.views;

import android.view.View;

import com.hadlink.library.adapter.utils.ViewEventListener;


public interface BindableLayout<T> {
    void bind(T item, int position);

    void bind(T item);

    void setViewEventListener(ViewEventListener<T> viewEventListener);

    ViewEventListener<T> getViewEventListener();

    void notifyItemAction(int actionId, T theItem, View view);
}
