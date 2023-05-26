package com.example.myprojectapp;

public class User {
    private String email;
    private String phoneNum;
    private String gender;

    public User() {
    }

    public User(String email, String phoneNum, String gender) {
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
