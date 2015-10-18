package com.testerhome.nativeandroid.models;

/**
 * Created by cvtpc on 2015/10/12.
 */
public class NotificationEntity {

    private int id;
    private String type;
    private Boolean read;
    private TesterUser actor;
    private String mention_type;
    private String mention;
    private TopicReplyEntity reply;
    private String created_at;
    private String updated_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public TesterUser getActor() {
        return actor;
    }

    public void setActor(TesterUser actor) {
        this.actor = actor;
    }

    public String getMention_type() {
        return mention_type;
    }

    public void setMention_type(String mention_type) {
        this.mention_type = mention_type;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public TopicReplyEntity getReply() {
        return reply;
    }

    public void setReply(TopicReplyEntity reply) {
        this.reply = reply;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
