package com.testerhome.nativeandroid.views;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.HomeFragment;
import com.testerhome.nativeandroid.fragments.JobFragment;
import com.testerhome.nativeandroid.fragments.MyFragment;
import com.testerhome.nativeandroid.fragments.TopicFragment;
import com.testerhome.nativeandroid.views.base.BaseActivity;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        setupView();
    }

    private void setupView() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View indicator = getLayoutInflater().inflate(R.layout.home_indicator, null);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.topic_indicator, null);
        mTabHost.addTab(mTabHost.newTabSpec("topic").setIndicator(indicator), TopicFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.job_indicator, null);
        mTabHost.addTab(mTabHost.newTabSpec("job").setIndicator(indicator), JobFragment.class, null);

        indicator = getLayoutInflater().inflate(R.layout.my_indicator, null);
        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), MyFragment.class, null);

        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
