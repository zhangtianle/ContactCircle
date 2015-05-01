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

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", pubuserUUID='" + pubuserUUID + '\'' +
                ", reuserUUID='" + reuserUUID + '\'' +
                ", content='" + content + '\'' +
                ", layer=" + layer +
                '}';
    }

    private String id;
    private String pubuserUUID;
    private String reuserUUID;
    private String content;
    private int layer;

    public Comment() {

    }
}
