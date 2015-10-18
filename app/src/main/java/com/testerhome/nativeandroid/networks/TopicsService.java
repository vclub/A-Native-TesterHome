package com.testerhome.nativeandroid.networks;

import com.testerhome.nativeandroid.models.TopicDetailResponse;
import com.testerhome.nativeandroid.models.TopicReplyResponse;
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
                         @Query("offset") int offset,
                         Callback<TopicsResponse> callback);


    @GET("/topics.json")
    void getTopicsByNodeId(@Query("node_id") int nodeId,
                         @Query("offset") int offset,
                         Callback<TopicsResponse> callback);


    @GET("/topics/{id}.json")
    void getTopicById(@Path("id") String id,
                      Callback<TopicDetailResponse> callback);


    @GET("/users/{username}/topics.json")
    void getUserTopics(@Path("username") String username,
                       @Query("access_token") String accessToken,
                       @Query("offset") int offset,
                       Callback<TopicsResponse> callback);

    @GET("/users/{username}/favorites.json")
    void getUserFavorite(@Path("username") String username,
                       @Query("access_token") String accessToken,
                       @Query("offset") int offset,
                       Callback<TopicsResponse> callback);

    @GET("/users/{username}.json")
    void getUserInfo(@Path("username") String username,
                     @Query("access_token") String accessToken,
                     Callback<UserResponse> callback);

    @GET("/topics/{id}/replies.json")
    void getTopicsReplies(@Path("id") String id,
                          @Query("offset") int offset,
                          Callback<TopicReplyResponse> callback);
}
