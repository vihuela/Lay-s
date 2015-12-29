package com.hadlink.library.adapter.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hadlink.library.adapter.utils.ViewEventListener;


public class BindableViewHolder<T> extends RecyclerView.ViewHolder {

    private BindableLayout<T> bindableLayout;

    public BindableViewHolder(BindableLayout<T> itemView) {
        super((View) itemView);
        bindableLayout = itemView;

    }

    public void bind(T object, int position) {
        bindableLayout.bind(object, position);
    }

    public void setViewEventListener(ViewEventListener<T> listener) {
        bindableLayout.setViewEventListener(listener);
    }
}
