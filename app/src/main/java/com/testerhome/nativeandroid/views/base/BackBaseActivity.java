package com.testerhome.nativeandroid.views.base;

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
}
