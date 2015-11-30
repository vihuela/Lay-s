package com.hadlink.library.adapter.builders;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.hadlink.library.adapter.utils.Mapper;
import com.hadlink.library.adapter.views.BindableLayout;


public interface BindableLayoutBuilder<T> {
    BindableLayout build(@NonNull ViewGroup parent, int viewType, T item, @NonNull Mapper mapper);

    Class<? extends BindableLayout> viewType(@NonNull T item, int position, @NonNull Mapper mapper);
}
