package com.testerhome.nativeandroid.models;

/**
 * Created by cvtpc on 2015/10/16.
 */
public class TopicReplyEntity {

    /**{
     "id": 248607,
     "body_html": "<p>在我刚毕业的时候读过这一篇, 收获颇丰.</p>",
     "created_at": "2015-02-23T14:14:52.043+08:00",
     "updated_at": "2015-02-23T14:14:52.043+08:00",
     "deleted": false,
     "topic_id": 24325,
     "user": {
     "id": 121,
     "login": "lyfi2003",
     "name": "windy",
     "avatar_url": "http://ruby-china-files-dev.b0.upaiyun.com/user/large_avatar/121.jpg"
     },
     "abilities": {
     "update": false,
     "destroy": false
     }
     },**/

    private int id;
    private String body_html;
    private String created_at;
    private String updated_at;
    private int  topic_id;
    private UserEntity user;
    private boolean deleted;
    private AbilitiesEntity abilities;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
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

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AbilitiesEntity getAbilities() {
        return abilities;
    }

    public void setAbilities(AbilitiesEntity abilities) {
        this.abilities = abilities;
    }

}
