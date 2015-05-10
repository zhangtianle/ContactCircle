package com.cqupt.bean;

/**
 * Created by ls on 15-5-9.
 */
public class Friend {


    public String getCirclesCounts() {
        return circlesCounts;
    }

    public void setCirclesCounts(String circlesCounts) {
        this.circlesCounts = circlesCounts;
    }

    public String getFriendClass() {
        return friendClass;
    }

    public void setFriendClass(String friendClass) {
        this.friendClass = friendClass;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Friend toString is:{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", circlesCounts='" + circlesCounts + '\'' +
                ", friendClass='" + friendClass + '\'' +
                ", college='" + college + '\'' +
                '}';
    }



    private String id;
    private String name;
    private String head;
    private String circlesCounts;
    private String friendClass;
    private String college;

}
