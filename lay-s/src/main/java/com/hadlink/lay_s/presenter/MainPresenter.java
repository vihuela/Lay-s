package com.hadlink.lay_s.presenter;

import android.view.Menu;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.delegate.MainDelegate;
import com.hadlink.library.base.BaseActivity;
import com.joanzapata.iconify.fonts.MaterialIcons;

/**
 * @author Created by lyao on 2016/3/2.
 * @description
 */
public class MainPresenter extends BaseActivity<MainDelegate> {


    @Override protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override protected void onUseIconifySetMenuItem(Menu menu) {
        setMenuItem(MaterialIcons.md_face, R.color.text_dark, menu.findItem(R.id.git));
    }
}
