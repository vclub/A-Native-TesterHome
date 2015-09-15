package com.testerhome.nativeandroid.models;

import java.util.List;

/**
 * Created by Bin Li on 2015/9/15.
 */
public class TopicsResponse {

    private List<TopicEntity> topics;

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEntity> topics) {
        this.topics = topics;
    }
}
