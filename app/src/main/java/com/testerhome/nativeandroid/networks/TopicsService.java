package com.testerhome.nativeandroid.networks;

import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.models.TopicsResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Bin Li on 2015/9/15.
 */
public interface TopicsService {

    @FormUrlEncoded
    @POST("/topics.json")
    void getTopicsByType(@Field("type") String type,
                         Callback<TopicsResponse> callback);

    @GET("/topics/{id}.json")
    void getTopicById(@Path("id") String id,
                         Callback<TopicDetailResponse> callback);
}
