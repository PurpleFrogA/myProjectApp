package com.example.myprojectapp;

public class Item {
    private String name;
    private String id;
    private String weight;
    //private String al7aja;
    private String kind;
    //private String picture;

    public Item(String name, String id, String weight/*, String al7aja*/, String kind/*, String picture*/) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        //this.al7aja = al7aja;
        this.kind = kind;
        //this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    /*public String getAl7aja() {
        return al7aja;
    }*/

    /*public void setAl7aja(String al7aja) {
        this.al7aja = al7aja;
    }*/

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    /*public String getPicture() {
        return picture;
    }*/

    /*public void setPicture(String picture) {
        this.picture = picture;
    }*/

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", weight='" + weight + '\'' +
                /*", al7aja='" + al7aja + '\'' +*/
                ", kind='" + kind + '\'' +
                /*", picture='" + picture + '\'' +*/
                '}';
    }

}
