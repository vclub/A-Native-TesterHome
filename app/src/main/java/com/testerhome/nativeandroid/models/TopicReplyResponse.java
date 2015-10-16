package com.testerhome.nativeandroid.models;

import java.util.List;

/**
 * Created by cvtpc on 2015/10/16.
 */
public class TopicReplyResponse {

    private List<TopicReplyEntity> replies;

    public  List<TopicReplyEntity> getTopicReply(){
        return replies;
    }

    public void setTopicReply(List<TopicReplyEntity> replies){
        this.replies = replies;
    }
}
