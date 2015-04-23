package com.cqupt.bean;

/**
 * Created by ls on 15-4-20.
 */
public class User {


    private String name;
    private String passWord;
    private String studentNum;
    private String userUUID;
    private String userClass;

    public String getUserAcademy() {
        return userAcademy;
    }

    public void setUserAcademy(String userAcademy) {
        this.userAcademy = userAcademy;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    private String userAcademy;


    public User(String name, String passWord, String studentNum, String userUUID, String userClass, String userAcademy) {
        this.studentNum = studentNum;
        this.passWord = passWord;
        this.userUUID = userUUID;
        this.name = name;
        this.userAcademy = userAcademy;
        this.userClass = userClass;

    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
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
                ", studentNum='" + studentNum + '\'' +
                ", userUUID='" + userUUID + '\'' +
                '}';
    }
}
