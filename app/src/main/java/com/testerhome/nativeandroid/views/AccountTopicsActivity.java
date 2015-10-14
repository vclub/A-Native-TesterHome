package com.testerhome.nativeandroid.views;

import android.os.Bundle;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.AccountTopicsFragment;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

/**
 * Created by vclub on 15/10/14.
 */
public class AccountTopicsActivity extends BackBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_topics);

        setCustomTitle("我的发帖");

        setupView();
    }

    private void setupView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                AccountTopicsFragment.newInstance())
                .commit();
    }
}
