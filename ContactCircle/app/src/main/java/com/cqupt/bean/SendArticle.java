package com.cqupt.bean;

import java.util.List;

/**
 * Created by ls on 15-4-30.
 */
public class SendArticle {
    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCircleUUID() {
        return circleUUID;
    }

    public void setCircleUUID(String circleUUID) {
        this.circleUUID = circleUUID;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public SendArticle(String userUUID, String circleUUID, String title, String content, String tags) {
        this.userUUID = userUUID;
        this.circleUUID = circleUUID;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    private String userUUID;
    private String title;
    private String content;
    private String circleUUID;
    private String tags;


}
