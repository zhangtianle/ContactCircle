package com.cqupt.bean;

/**
 * Created by ls on 15-4-25.
 */
public class Comment {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPubuserUUID() {
        return pubuserUUID;
    }

    public void setPubuserUUID(String pubuserUUID) {
        this.pubuserUUID = pubuserUUID;
    }

    public String getReuserUUID() {
        return reuserUUID;
    }

    public void setReuserUUID(String reuserUUID) {
        this.reuserUUID = reuserUUID;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getPubuserHead() {
        return pubuserHead;
    }

    public void setPubuserHead(String pubuserHead) {
        this.pubuserHead = pubuserHead;
    }

    public String getReuserHead() {
        return reuserHead;
    }

    public void setReuserHead(String reuserHead) {
        this.reuserHead = reuserHead;
    }

    public String getReuserName() {
        return reuserName;
    }

    public void setReuserName(String reuserName) {
        this.reuserName = reuserName;
    }

    public String getPubuserName() {
        return pubuserName;
    }

    public void setPubuserName(String pubuserName) {
        this.pubuserName = pubuserName;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", pubuserUUID='" + pubuserUUID + '\'' +
                ", reuserUUID='" + reuserUUID + '\'' +
                ", reuserName='" + reuserName + '\'' +
                ", reuserHead='" + reuserHead + '\'' +
                ", content='" + content + '\'' +
                ", layer=" + layer +
                ", articleUUID='" + articleUUID + '\'' +
                ", pubuserName='" + pubuserName + '\'' +
                ", pubuserHead='" + pubuserHead + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    private String id;
    private String pubuserUUID;
    private String reuserUUID;
    private String reuserName;
    private String reuserHead;
    private String content;
    private int layer;
    private String articleUUID;
    private String pubuserName;
    private String pubuserHead;
    private String time;

    public Comment(String id, String reuserUUID, String reuserHead, String reuserName, String pubuserUUID, String articleUUID, int layer, String content, String pubuserName, String pubuserHead, String time) {
        this.id = id;
        this.reuserUUID = reuserUUID;
        this.reuserHead = reuserHead;
        this.reuserName = reuserName;
        this.pubuserUUID = pubuserUUID;
        this.articleUUID = articleUUID;
        this.layer = layer;
        this.content = content;
        this.pubuserName = pubuserName;
        this.pubuserHead = pubuserHead;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getArticleUUID() {
        return articleUUID;
    }

    public void setArticleUUID(String articleUUID) {
        this.articleUUID = articleUUID;
    }


    public Comment() {

    }
}
