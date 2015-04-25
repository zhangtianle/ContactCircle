package com.cqupt.bean;

import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class User {

    private int grade;
    private String name;
    private String passWord;
    private String userNum;
    private String id;
    private String userClass;
    private String userCollege;
    private List<Circle> circles;


    public List<Circle> getCircles() {
        return circles;
    }

    public void setCircles(List<Circle> circles) {
        this.circles = circles;
    }
    public User() {

    }


    public String getUserCollege() {
        return userCollege;
    }

    public void setUserCollege(String userCollege) {
        this.userCollege = userCollege;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }


    public User(String name, String passWord, String userNum, String id, String userClass, String userCollege, List<Circle> circles) {
        this.userNum = userNum;
        this.passWord = passWord;
        this.id = id;
        this.name = name;
        this.userCollege = userCollege;
        this.userClass = userClass;
        this.circles = circles;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
        return "User{" +
                "name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userNum='" + userNum + '\'' +
                ", id='" + id + '\'' +
                ", userCollege='" + userCollege + '\'' +
                '}';
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
