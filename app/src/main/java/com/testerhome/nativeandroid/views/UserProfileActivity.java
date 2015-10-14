package com.testerhome.nativeandroid.views;

import android.content.Intent;
import android.os.Bundle;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

import butterknife.OnClick;

/**
 * Created by vclub on 15/10/13.
 */
public class UserProfileActivity extends BackBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setCustomTitle("个人资料");
    }

    @OnClick(R.id.btn_show_favorite)
    void onFavoriteClick(){
        startActivity(new Intent(this, AccountFavoriteActivity.class));
    }

    @OnClick(R.id.btn_show_topics)
    void onTopicsClick(){
        startActivity(new Intent(this, AccountTopicsActivity.class));
    }
}
