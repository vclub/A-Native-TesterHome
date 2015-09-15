package com.testerhome.nativeandroid.models;

/**
 * Created by Bin Li on 2015/9/15.
 */
public class UserEntity {
    /**
     * id : 104
     * login : seveniruby
     * name : 思寒
     * avatar_url : /user/large_avatar/104.jpg
     */

    private int id;
    private String login;
    private String name;
    private String avatar_url;

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
