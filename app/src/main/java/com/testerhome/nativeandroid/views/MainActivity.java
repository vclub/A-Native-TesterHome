package com.testerhome.nativeandroid.views;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.fragments.HomeFragment;
import com.testerhome.nativeandroid.utils.TintUtil;
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

    private void setupView(){
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View indicator = getIndicatoreView("社区", R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("home").setIndicator(indicator), HomeFragment.class, null);

        indicator = getIndicatoreView("话题", R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("topic").setIndicator(indicator), HomeFragment.class, null);

        indicator = getIndicatoreView("招聘", R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("job").setIndicator(indicator), HomeFragment.class, null);

        indicator = getIndicatoreView("我的", R.layout.home_indicator);
        mTabHost.addTab(mTabHost.newTabSpec("config").setIndicator(indicator), HomeFragment.class, null);

        mTabHost.getTabWidget().setDividerDrawable(null);
    }

    private View getIndicatoreView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(name);

        ImageView iv = (ImageView) v.findViewById(R.id.tabImg);

        if (name.equals("社区")) {
            iv.setImageDrawable(TintUtil.tintDrawable(getResources().getDrawable(R.drawable.tab_main_home),
                    getResources().getColorStateList(R.color.tab_item_tint_color)));
        } else if (name.equals("话题")) {
            iv.setImageDrawable(TintUtil.tintDrawable(getResources().getDrawable(R.drawable.tab_main_topic),
                    getResources().getColorStateList(R.color.tab_item_tint_color)));
        } else if (name.equals("招聘")) {
            iv.setImageDrawable(TintUtil.tintDrawable(getResources().getDrawable(R.drawable.tab_main_hot),
                    getResources().getColorStateList(R.color.tab_item_tint_color)));
        } else if (name.equals("我的")) {
            iv.setImageDrawable(TintUtil.tintDrawable(getResources().getDrawable(R.drawable.tab_main_my),
                    getResources().getColorStateList(R.color.tab_item_tint_color)));
        }

        return v;
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
