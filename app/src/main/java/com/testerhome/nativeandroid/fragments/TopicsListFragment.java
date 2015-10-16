package com.testerhome.nativeandroid.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicsResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.views.adapters.TopicsListAdapter;
import com.testerhome.nativeandroid.views.widgets.DividerItemDecoration;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Bin Li on 2015/9/16.
 */
public class TopicsListFragment extends BaseFragment {

    @Bind(R.id.rv_topic_list)
    RecyclerView recyclerViewTopicList;

    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private int mNextCursor = 1;

    private TopicsListAdapter mAdatper;

    private String type;

    public static TopicsListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        TopicsListFragment fragment = new TopicsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_topics;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getString("type");
        setupView();
    }

    private void setupView() {
        mAdatper = new TopicsListAdapter(getActivity());
        mAdatper.setListener(new TopicsListAdapter.EndlessListener() {
            @Override
            public void onListEnded() {
                if (mNextCursor > 0) {
                    mNextCursor = mNextCursor + 1;
                    loadTopics();
                }
            }
        });

        recyclerViewTopicList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTopicList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerViewTopicList.setAdapter(mAdatper);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopics();
            }
        });

        loadTopics();
    }

    private void loadTopics() {
        TesterHomeApi.getInstance().getTopicsService().getTopicsByType(type,
                mNextCursor,
                new Callback<TopicsResponse>() {
                    @Override
                    public void success(TopicsResponse topicsResponse, Response response) {

                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (topicsResponse.getTopics().size() > 0) {
                            if (mNextCursor == 1) {
                                mAdatper.setItems(topicsResponse.getTopics());
                            } else {
                                mAdatper.addItems(topicsResponse.getTopics());
                            }
                        } else {
                            mNextCursor = 0;
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        Log.e("demo", "failure() called with: " + "error = [" + error + "]"
                                + error.getUrl());
                    }
                });
    }
}
