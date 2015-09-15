package com.testerhome.nativeandroid.views.base;

import android.support.v7.app.AppCompatActivity;

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
}
