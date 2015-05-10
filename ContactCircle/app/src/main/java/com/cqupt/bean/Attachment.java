package com.cqupt.bean;

/**
 * Created by ls on 15-4-25.
 */
public class Attachment {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachURL() {
        return attachURL;
    }

    public void setAttachURL(String attachURL) {
        this.attachURL = attachURL;
    }


    public Attachment(String id, String articleUUID, String attachURL, String attachName) {
        this.id = id;
        this.articleUUID = articleUUID;
        this.attachURL = attachURL;
        this.attachName = attachName;
    }


    public String getArticleUUID() {
        return articleUUID;
    }

    public void setArticleUUID(String articleUUID) {
        this.articleUUID = articleUUID;
    }


    public  Attachment(){

    }
    private String id;
    private String attachName;
    private String attachURL;

    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", attachName='" + attachName + '\'' +
                ", attachURL='" + attachURL + '\'' +
                ", articleUUID='" + articleUUID + '\'' +
                '}';
    }

    private String articleUUID;
}
