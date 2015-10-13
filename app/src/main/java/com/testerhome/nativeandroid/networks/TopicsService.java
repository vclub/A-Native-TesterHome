package com.testerhome.nativeandroid.networks;

import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.models.TopicsResponse;
import com.testerhome.nativeandroid.models.UserResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Bin Li on 2015/9/15.
 */
public interface TopicsService {

    @GET("/topics.json")
    void getTopicsByType(@Query("type") String type,
                         @Query("offset") int page,
                         Callback<TopicsResponse> callback);

    @GET("/topics/{id}.json")
    void getTopicById(@Path("id") String id,
                      Callback<TopicDetailResponse> callback);


    @GET("/users/{username}/topics.json")
    void getUserTopics(@Path("username") String username,
                       @Query("access_token") String accessToken,
                       Callback<TopicsResponse> callback);

    @GET("/users/{username}.json")
    void getUserInfo(@Path("username") String username,
                     @Query("access_token") String accessToken,
                     Callback<UserResponse> callback);
}
