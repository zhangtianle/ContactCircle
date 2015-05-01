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



    @Override
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", attachName='" + attachName + '\'' +
                ", attachURL='" + attachURL + '\'' +
                '}';
    }
    private String id;
    private String attachName;
    private String attachURL;

    public  Attachment(){

    }
}
