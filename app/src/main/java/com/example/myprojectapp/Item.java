package com.example.myprojectapp;

public class Item {
    private String name;
    private String weight;
    private String kind;
    private String size;
    private String imageUrl;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Item(String name, String weight, String kind, String size, String photo, String user) {
        this.name = name;
        this.weight = weight;
        this.kind = kind;
        this.size = size;
        this.imageUrl = photo;
        this.user = user;
    }
    //private String al7aja;


    public Item() {

    }

    //private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
