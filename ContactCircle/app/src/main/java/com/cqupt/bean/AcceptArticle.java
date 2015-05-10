package com.cqupt.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class AcceptArticle implements Serializable {

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAttachments() {
        return attachments;
    }

    public void setAttachments(int attachments) {
        this.attachments = attachments;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public AcceptArticle(String id, String time, int zanCount, String content, int attachments, int comments, String circle, String type, String photoURL, String userHead, String name, String title) {
        this.id = id;
        this.time = time;
        this.zanCount = zanCount;
        this.content = content;
        this.attachments = attachments;
        this.comments = comments;
        this.circle = circle;
        this.type = type;
        this.photoURL = photoURL;
        this.userHead = userHead;
        this.name = name;
        this.title = title;
    }


    @Override
    public String toString() {
        return "AcceptArticle{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", zanCount=" + zanCount +
                ", attachments=" + attachments +
                ", comments=" + comments +
                ", circle='" + circle + '\'' +
                ", type='" + type + '\'' +
                ", photoURL='" + photoURL + '\'' +
                ", userHead='" + userHead + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public AcceptArticle() {

    }


    private String id;
    private String time;
    private String content;
    private int zanCount;
    private int attachments;
    private int comments;
    private String circle;
    private String type;
    private String photoURL;
    private String userHead;
    private String name;
    private String title;


}
