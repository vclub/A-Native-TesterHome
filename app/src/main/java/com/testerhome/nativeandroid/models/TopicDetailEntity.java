package com.testerhome.nativeandroid.models;

/**
 * Created by vclub on 15/9/17.
 */
public class TopicDetailEntity extends TopicEntity {

    private String body_html;
    private String hits;

    public String getBody_html() {
        return body_html;
    }

    public void setBody_html(String body_html) {
        this.body_html = body_html;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }
}
