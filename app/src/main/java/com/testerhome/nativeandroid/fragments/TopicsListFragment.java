package com.testerhome.nativeandroid.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicsResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.views.adapter.TopicsListAdapter;

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
        recyclerViewTopicList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTopicList.setAdapter(mAdatper);
        loadTopics();
    }

    private void loadTopics(){
        TesterHomeApi.getInstance().getTopicsService().getTopicsByType(type, new Callback<TopicsResponse>() {
            @Override
            public void success(TopicsResponse topicsResponse, Response response) {
                Log.e("demo", response.getUrl());

                if (topicsResponse.getTopics().size() > 0){
                    mAdatper.addItems(topicsResponse.getTopics());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("demo", "failure() called with: " + "error = [" + error + "]" + error.getUrl());
            }
        });
    }
}
