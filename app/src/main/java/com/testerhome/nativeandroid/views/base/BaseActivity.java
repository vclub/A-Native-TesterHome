package com.testerhome.nativeandroid.views.base;

import android.support.v7.app.AppCompatActivity;

import com.squareup.leakcanary.RefWatcher;
import com.testerhome.nativeandroid.application.NativeApp;

import butterknife.ButterKnife;

/**
 * Created by vclub on 15/9/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        RefWatcher refWatcher = NativeApp.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
