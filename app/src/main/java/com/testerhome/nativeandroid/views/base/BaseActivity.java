package com.testerhome.nativeandroid.views.base;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.application.NativeApp;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vclub on 15/9/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toobar;

    @Nullable
    @Bind(R.id.toolbar_title)
    TextView customTitle;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        setupToolbar();
    }

    protected void setupToolbar(){
        if (toobar != null){
            setSupportActionBar(toobar);
        }
    }

    public void setCustomTitle(String title){
        if (customTitle != null){
            customTitle.setText(title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        RefWatcher refWatcher = NativeApp.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
