package com.testerhome.nativeandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.testerhome.nativeandroid.Config;
import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.auth.TesterHomeAccountService;
import com.testerhome.nativeandroid.models.TesterUser;
import com.testerhome.nativeandroid.views.AccountFavoriteActivity;
import com.testerhome.nativeandroid.views.AccountNotificationActivity;
import com.testerhome.nativeandroid.views.AccountTopicsActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by vclub on 15/9/16.
 */
public class MyFragment extends BaseFragment {



    @Bind(R.id.id_user_avatar)
    SimpleDraweeView userAvatar;
    @Bind(R.id.loginName)
    TextView userName;
    @Bind(R.id.id_company)
    TextView commanyName;
    @Bind(R.id.id_tag_line)
    TextView tagLine;


    private TesterUser mTesterHomeAccount;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTesterHomeAccount = TesterHomeAccountService.getInstance(getActivity()).getActiveAccountInfo();
        setupView();
    }

    private void setupView() {

        userAvatar.setImageURI(Uri.parse(Config.getImageUrl(mTesterHomeAccount.getAvatar_url())));
        userName.setText(mTesterHomeAccount.getLogin());
        commanyName.setText(mTesterHomeAccount.getCompany());
        tagLine.setText(mTesterHomeAccount.getTagline());
    }


    @OnClick(R.id.id_create_layout)
    void onTopicsClick(){
        startActivity(new Intent(getActivity(), AccountTopicsActivity.class));
    }

    @OnClick(R.id.id_collect_layout)
    void onFavoriteClick(){
        startActivity(new Intent(getActivity(), AccountFavoriteActivity.class));
    }
    @OnClick(R.id.id_notification_layout)
    void onNotificationClick(){
        startActivity(new Intent(getActivity(), AccountNotificationActivity.class));
    }
}
