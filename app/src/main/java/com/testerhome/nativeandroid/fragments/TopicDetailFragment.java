package com.testerhome.nativeandroid.fragments;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailFragment extends BaseFragment {

    private String mTopicId;

    @Bind(R.id.test)
    TextView demo;

    public static TopicDetailFragment newInstance(Integer topicId) {

        Bundle args = new Bundle();
        args.putInt("topic_id", topicId);
        TopicDetailFragment fragment = new TopicDetailFragment();
        fragment.setArguments(args);
        fragment.mTopicId = topicId.toString();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // getArguments().getString("topic_id");

        loadInfo();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_topic_detail;
    }

    private void loadInfo(){
        TesterHomeApi.getInstance().getTopicsService().getTopicById(mTopicId,
                new Callback<TopicDetailResponse>() {
                    @Override
                    public void success(TopicDetailResponse topicDetailResponse, Response response) {
                        demo.setText(Html.fromHtml(topicDetailResponse.getTopic().getBody_html()));
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}
