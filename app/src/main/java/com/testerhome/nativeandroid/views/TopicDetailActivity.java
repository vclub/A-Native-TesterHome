package com.testerhome.nativeandroid.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.TopicDetailFragment;
import com.testerhome.nativeandroid.views.base.BackBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailActivity extends BackBaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_base);
        ButterKnife.bind(this);
        setCustomTitle("帖子详情");

        setupView();
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    private void setupView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                TopicDetailFragment.newInstance(getIntent().getIntExtra("topic_id", 0)))
                .commit();
    }



    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.reply_topic:
                    TopicDetailActivity.this.startActivity(new Intent(TopicDetailActivity.this, TopicReplyActivity.class).putExtra("topic_id", getIntent().getIntExtra("topic_id", 0)));
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic_detail, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
