package com.testerhome.nativeandroid.fragments;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.models.TopicDetailEntity;
import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import us.feras.mdv.MarkdownView;

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
    MarkdownView tvDetailBody;

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
//        tvDetailBody.getSettings().setUseWideViewPort(true);
//        tvDetailBody.getSettings().setLoadWithOverviewMode(true);
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
                        tvDetailUsername.setText(TextUtils.isEmpty(topicEntity.getUser().getLogin()) ? "匿名用户" : topicEntity.getUser().getName());
                        tvDetailPublishDate.setText(StringUtils.formatPublishDateTime(topicEntity.getCreated_at())
                                + "." + topicEntity.getHits() + "次阅读");
                        sdvDetailUserAvatar.setImageURI(Uri.parse(Config.getImageUrl(topicEntity.getUser().getAvatar_url())));


                        showWebContent(topicEntity.getBody_html());
                        // tvDetailBody.loadMarkdown(topicEntity.getBody());
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void showWebContent(String htmlBody){
        String prompt = "";
        AssetManager assetManager = getActivity().getResources().getAssets();

        try{
            InputStream inputStream = assetManager.open("upgrade.html");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            prompt = new String(b);
            prompt = prompt.concat(htmlBody.replace("<img src=\"/photo/", "<img src=\"https://testerhome.com/photo/")).concat("</body></html>");
            inputStream.close();
        }catch (IOException e){
            Log.e("", "Counldn't open updrage-alter.html", e);
        }

        tvDetailBody.setBackgroundColor(0);
        tvDetailBody.loadDataWithBaseURL(null, prompt, "text/html", "utf-8", null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
