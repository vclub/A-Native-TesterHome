package com.testerhome.nativeandroid.views.base;

import android.view.MenuItem;

import com.testerhome.nativeandroid.R;

/**
 * Created by vclub on 15/9/17.
 */
public abstract class BackBaseActivity extends BaseActivity {

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (toobar != null)
            toobar.setNavigationIcon(R.drawable.abc_ic_ab_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
