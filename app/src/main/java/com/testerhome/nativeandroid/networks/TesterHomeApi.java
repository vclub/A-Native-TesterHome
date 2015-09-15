package com.testerhome.nativeandroid.networks;

import com.testerhome.nativeandroid.Config;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Bin Li on 2015/9/16.
 */
public class TesterHomeApi {

    private static TesterHomeApi instance;
    private TopicsService topicsService;

    public static TesterHomeApi getInstance() {
        if (instance == null){
            instance = new TesterHomeApi();
        }
        return instance;
    }

    private TesterHomeApi(){
        RestAdapter restAdapter = buildRestAdapter();
        this.topicsService = restAdapter.create(TopicsService.class);
    }

    private RestAdapter buildRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(Config.BASE_URL)
                .setClient(new OkClient())
                .build();
    }

    public TopicsService getTopicsService() {
        return topicsService;
    }
}
