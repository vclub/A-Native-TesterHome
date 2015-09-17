package com.testerhome.nativeandroid.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicDetailEntity;
import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailFragment extends BaseFragment {

    @Bind(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @Bind(R.id.sdv_detail_user_avatar)
    SimpleDraweeView sdvDetailUserAvatar;
    @Bind(R.id.tv_detail_name)
    TextView tvDetailName;
    @Bind(R.id.tv_detail_username)
    TextView tvDetailUsername;
    @Bind(R.id.tv_detail_publish_date)
    TextView tvDetailPublishDate;

    private String mTopicId;

    @Bind(R.id.tv_detail_body)
    TextView tvDetailBody;

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

    private void loadInfo() {
        TesterHomeApi.getInstance().getTopicsService().getTopicById(mTopicId,
                new Callback<TopicDetailResponse>() {
                    @Override
                    public void success(TopicDetailResponse topicDetailResponse, Response response) {
                        TopicDetailEntity topicEntity = topicDetailResponse.getTopic();
                        tvDetailTitle.setText(topicEntity.getTitle());
                        tvDetailName.setText(topicEntity.getNode_name() + ".");
                        tvDetailUsername.setText(TextUtils.isEmpty(topicEntity.getUser().getName()) ? "匿名用户" : topicEntity.getUser().getName());
                        tvDetailPublishDate.setText("." + StringUtils.formatPublishDateTime(topicEntity.getCreated_at()) + ".");
                        sdvDetailUserAvatar.setImageURI(Uri.parse("https://testerhome.com" + topicEntity.getUser().getAvatar_url()));
                        tvDetailBody.setText(Html.fromHtml(topicEntity.getBody_html()));
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
