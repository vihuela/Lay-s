package com.hadlink.lay_s.presenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hadlink.lay_s.R;
import com.hadlink.lay_s.delegate.MainDelegate;
import com.hadlink.library.base.BaseActivity;
import com.joanzapata.iconify.fonts.MaterialIcons;

/**
 * @author Created by lyao on 2016/3/2.
 */
public class MainAtyPresenter extends BaseActivity<MainDelegate> {


    @Override protected Class<MainDelegate> getDelegateClass() {
        return MainDelegate.class;
    }

    @Override protected void onUseIconifySetMenuItem(Menu menu) {
        setMenuItem(MaterialIcons.md_face, R.color.text_dark, menu.findItem(R.id.git));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.initView(getSupportFragmentManager());
    }

    @Override protected String getToolBarTitle() {

        return "Image Browser";
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.git:
                try {
                    Uri uri = Uri.parse("https://github.com/vihuela");
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } catch (ActivityNotFoundException ignored) {
                }

                break;
            case R.id.food:
                startActivity(new Intent(this, AnimAtyPresenter.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
