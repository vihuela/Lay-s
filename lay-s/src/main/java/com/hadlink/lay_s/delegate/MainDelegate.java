package com.hadlink.lay_s.delegate;

import android.support.v7.widget.Toolbar;

import com.hadlink.lay_s.R;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.HackViewpager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * @author Created by lyao on 2016/3/2.
 * @description
 */
public class MainDelegate extends AppDelegate {

    SmartTabLayout tabLayout;
    HackViewpager viewpager;


    @Override public int getRootLayoutId() {
        return R.layout.delegate_main;
    }

    @Override public void initWidget() {
        tabLayout = get(R.id.tab);
        viewpager = get(R.id.pager);
    }

    @Override public int getOptionsMenuId() {
        return R.menu.menu_main;
    }

    @Override public Toolbar getToolbar() {
        Toolbar toolbar = get(R.id.toolbar);
        toolbar.setTitle("Image Browser");
        return toolbar;
    }
}
