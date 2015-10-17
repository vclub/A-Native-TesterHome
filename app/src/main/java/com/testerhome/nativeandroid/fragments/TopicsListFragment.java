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

    private int mNextCursor = 0;

    private TopicsListAdapter mAdatper;

    private String type;
    private int nodeId;
    private boolean isViewLoad = false;

    public static TopicsListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        TopicsListFragment fragment = new TopicsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TopicsListFragment newInstance(int  nodeId) {
        Bundle args = new Bundle();
        args.putInt("nodeId", nodeId);
        TopicsListFragment fragment = new TopicsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_topics;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!isViewLoad){
            type = getArguments().getString("type");
            if(type == null){
                nodeId = getArguments().getInt("nodeId");
            }
            setupView();
        }
        isViewLoad = true;
    }

    private void setupView() {
        mAdatper = new TopicsListAdapter(getActivity());
        mAdatper.setListener(new TopicsListAdapter.EndlessListener() {
            @Override
            public void onListEnded() {
                if (mNextCursor > 0) {
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
                mNextCursor = 0;
                loadTopics();
            }
        });

        loadTopics();
    }

    private void loadTopics() {

        if(type!=null){
            TesterHomeApi.getInstance().getTopicsService().getTopicsByType(type,
                    mNextCursor*20,
                    new Callback<TopicsResponse>() {
                        @Override
                        public void success(TopicsResponse topicsResponse, Response response) {
                            Log.d("topic",type+"i load");
                            loadSuccess(topicsResponse);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            loadFail(error);
                        }
                    });
        }else{
            TesterHomeApi.getInstance().getTopicsService().getTopicsByNodeId(nodeId,
                    mNextCursor*20,
                    new Callback<TopicsResponse>() {
                        @Override
                        public void success(TopicsResponse topicsResponse, Response response) {
                            loadSuccess(topicsResponse);

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            loadFail(error);
                        }
                    });
        }

    }

    private void loadSuccess(TopicsResponse topicsResponse) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (topicsResponse.getTopics().size() > 0) {
            if (mNextCursor == 0) {
                mAdatper.setItems(topicsResponse.getTopics());
            } else {
                mAdatper.addItems(topicsResponse.getTopics());
            }
            if(topicsResponse.getTopics().size()==20){
                mNextCursor += 1;
            }else{
                mNextCursor = 0;
            }

        } else {
            mNextCursor = 0;
        }
    }

    private void loadFail(RetrofitError error){
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Log.e("demo", "failure() called with: " + "error = [" + error + "]"
                + error.getUrl());
    }
}
