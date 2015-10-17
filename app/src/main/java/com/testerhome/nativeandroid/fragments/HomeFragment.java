package com.testerhome.nativeandroid.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;

import butterknife.Bind;

/**
 * Created by vclub on 15/9/15.
 */
public class HomeFragment extends BaseFragment {


    @Bind(R.id.tl_topics)
    TabLayout tabLayoutTopicsTab;

    @Bind(R.id.vp_topics)
    ViewPager viewPagerTopics;

    private TopicViewPagerAdapter mAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupView();
    }

    private void setupView(){

        mAdapter = new TopicViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerTopics.setAdapter(mAdapter);

        tabLayoutTopicsTab.setupWithViewPager(viewPagerTopics);
        tabLayoutTopicsTab.setTabsFromPagerAdapter(mAdapter);
    }





    public class TopicViewPagerAdapter extends FragmentPagerAdapter {

        private String[] typeName = {"最新","最热","沙发","精华"};
        private String[] typeValue = {Config.TOPICS_TYPE_RECENT,
                                        Config.TOPICS_TYPE_POPULAR,
                                        Config.TOPICS_TYPE_NO_REPLY,
                                        Config.TOPICS_TYPE_EXCELLENT};

        public TopicViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TopicsListFragment.newInstance(typeValue[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return typeName[position];
        }

        @Override
        public int getCount() {
            return typeName.length;
        }
    }
}
