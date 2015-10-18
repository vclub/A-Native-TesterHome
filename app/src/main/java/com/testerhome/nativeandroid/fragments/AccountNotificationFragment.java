package com.testerhome.nativeandroid.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testerhome.nativeandroid.R;
import com.testerhome.nativeandroid.auth.TesterHomeAccountService;
import com.testerhome.nativeandroid.models.NotificationResponse;
import com.testerhome.nativeandroid.models.TesterUser;
import com.testerhome.nativeandroid.networks.TesterHomeApi;
import com.testerhome.nativeandroid.views.adapters.NotificationAdapter;
import com.testerhome.nativeandroid.views.widgets.DividerItemDecoration;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by cvtpc on 2015/10/18.
 */
public class AccountNotificationFragment extends BaseFragment {

    @Bind(R.id.rv_topic_list)
    RecyclerView recyclerViewTopicList;

    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private int mNextCursor = 0;

    private NotificationAdapter mAdatper;


    public static AccountNotificationFragment newInstance() {
        AccountNotificationFragment fragment = new AccountNotificationFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_topics;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTesterHomeAccount == null){
            getUserInfo();
        }
        setupView();


    }

    private void setupView() {
        mAdatper = new NotificationAdapter(getActivity());
        mAdatper.setListener(new NotificationAdapter.EndlessListener() {
            @Override
            public void onListEnded() {
                if (mNextCursor > 0) {
                    loadNotification();
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        recyclerViewTopicList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTopicList.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerViewTopicList.setAdapter(mAdatper);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNextCursor = 0;
                loadNotification();
            }
        });

        loadNotification();
    }

    private TesterUser mTesterHomeAccount;

    private void getUserInfo(){
        mTesterHomeAccount = TesterHomeAccountService.getInstance(getContext()).getActiveAccountInfo();
    }


    private void loadNotification() {

        TesterHomeApi.getInstance().getTopicsService().getNotifications(mTesterHomeAccount.getAccess_token(),
                mNextCursor * 20,
                new Callback<NotificationResponse>() {
                    @Override
                    public void success(NotificationResponse notificationResponse, Response response) {

                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (notificationResponse.getNotifications().size() > 0) {
                            if (mNextCursor == 0) {
                                mAdatper.setItems(notificationResponse.getNotifications());
                            } else {
                                mAdatper.addItems(notificationResponse.getNotifications());
                            }
                            if (notificationResponse.getNotifications().size() == 20) {
                                mNextCursor += 1;
                            } else {
                                mNextCursor = 0;
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
