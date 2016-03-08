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

package com.hadlink.lay_s.delegate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.presenter.ImageListFrgPresenter;
import com.hadlink.library.base.view.AppDelegate;
import com.hadlink.library.widget.HackViewpager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class MainDelegate extends AppDelegate {

    SmartTabLayout tabLayout;
    HackViewpager viewpager;
    Fragment[] frgs;
    String[] titles;


    @Override public int getRootLayoutId() {
        return R.layout.delegate_main;
    }

    @Override public void initWidget() {

        tabLayout = get(R.id.tab);
        viewpager = get(R.id.pager);
        //all pagers
        titles = getRootView().getContext().getResources().getStringArray(R.array.images_category_list_id);
        frgs = new Fragment[titles.length];
        for (int i = 0; i < titles.length; i++) {
            frgs[i] = new ImageListFrgPresenter();
            Bundle args = new Bundle();
            args.putString("title", titles[i]);
            frgs[i].setArguments(args);
        }
    }

    @Override public int getOptionsMenuId() {
        return R.menu.menu_main;
    }


    public void initView(FragmentManager fm) {
        viewpager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override public Fragment getItem(int position) {
                return frgs[position];
            }

            @Override public int getCount() {
                return frgs.length;
            }

            @Override public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewpager.setOffscreenPageLimit(frgs.length);
        tabLayout.setViewPager(viewpager);
    }

}
