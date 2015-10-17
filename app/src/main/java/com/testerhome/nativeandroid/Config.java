package com.testerhome.nativeandroid;

/**
 * Created by vclub on 15/9/15.
 */
public class Config {

//    recent - 最新
//    popular - 热门的话题（回帖和喜欢超过一定的数量
//    no_reply - 还没有任何回帖的
//    excellent - 精华帖
//    last_actived - 最近活跃的

    public static final String TOPICS_TYPE_RECENT = "recent";
    public static final String TOPICS_TYPE_POPULAR = "popular";
    public static final String TOPICS_TYPE_NO_REPLY = "no_reply";
    public static final String TOPICS_TYPE_EXCELLENT = "excellent";
    public static final String TOPICS_TYPE_LAST_ACTIVED = "last_actived";
    public static final int TOPIC_JOB_NODEID = 19;
    public static final String BASE_URL = "https://testerhome.com/api/v3";

    public static String getImageUrl(String imagePath){
        if(!imagePath.contains("https://testerhome.com")){
            return "https://testerhome.com".concat(imagePath);
        }else{
            return imagePath;
        }

    }
}
