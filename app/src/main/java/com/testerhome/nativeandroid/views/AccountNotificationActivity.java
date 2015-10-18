package com.testerhome.nativeandroid.views;

import android.os.Bundle;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.AccountNotificationFragment;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

/**
 * Created by cvtpc on 2015/10/18.
 */
public class AccountNotificationActivity extends BackBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_base);
        setCustomTitle("我的通知");
        setupView();
    }

    private void setupView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                AccountNotificationFragment.newInstance())
                .commit();
    }
}
