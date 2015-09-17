package com.testerhome.nativeandroid.views;

import android.os.Bundle;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.TopicDetailFragment;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailActivity extends BackBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_base);
        setCustomTitle("帖子详情");

        setupView();
    }

    private void setupView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                TopicDetailFragment.newInstance(getIntent().getIntExtra("topic_id", 0)))
                .commit();
    }
}
