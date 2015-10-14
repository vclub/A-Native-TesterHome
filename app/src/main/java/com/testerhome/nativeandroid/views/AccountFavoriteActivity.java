package com.testerhome.nativeandroid.views;

import android.os.Bundle;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.AccountFavoriteFragment;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

/**
 * Created by vclub on 15/10/14.
 */
public class AccountFavoriteActivity extends BackBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_favorite);

        setCustomTitle("我的收藏");


        setupView();
    }

    private void setupView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                AccountFavoriteFragment.newInstance())
                .commit();
    }
}
