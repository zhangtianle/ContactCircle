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
        return "Circle{" +
                "userUUID='" + userUUID + '\'' +
                ", id='" + id + '\'' +
                ", circleName='" + circleName + '\'' +
                ", circleHead='" + circleHead + '\'' +
                '}';
    }

    private String userUUID;
    private String id;
    private String circleName;
    private String circleHead;

    public String getCircleHead() {
        return circleHead;
    }

    public void setCircleHead(String circleHead) {
        this.circleHead = circleHead;
    }


    public Circle(String userUUID, String circleName, String id, String circleHead) {
        this.userUUID = userUUID;
        this.circleName = circleName;
        this.id = id;
        this.circleHead = circleHead;

    }
}
