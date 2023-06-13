package com.example.myprojectapp;

public class User {
    private String name;
    private String email;
    private String phoneNum;
    private String gender;
    private String address;
    private String profile;

    public User(String name, String email, String phoneNum, String gender, String address, String profile) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.address = address;
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
                "email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {
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

}
