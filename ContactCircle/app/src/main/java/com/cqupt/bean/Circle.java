package com.cqupt.bean;

/**
 * Created by ls on 15-4-25.
 */
public class Circle {
    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Circle(String userUUID, String id, String circleName) {
        this.userUUID = userUUID;
        this.id = id;
        this.circleName = circleName;
    }

    public Circle() {

    }



    @Override
    public String toString() {
        return "{" +
                "userUUID='" + userUUID + '\'' +
                ", id='" + id + '\'' +
                ", circleName='" + circleName + '\'' +
                '}';
    }
    private String userUUID;
    private String id;
    private String circleName;
}
