package com.testerhome.nativeandroid.models;

import java.util.List;

/**
 * Created by cvtpc on 2015/10/12.
 */
public class NotificationResponse {

    private List<NotificationEntity> notifications;

    public  List<NotificationEntity> getNotifications(){
        return notifications;
    }

    public void setNotifications(List<NotificationEntity> notifications){
        this.notifications = notifications;
    }
}
