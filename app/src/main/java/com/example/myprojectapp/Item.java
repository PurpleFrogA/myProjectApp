package com.example.myprojectapp;

public class Item {
    private String name;
    private String weight;
    private String kind;
    private String size;

    public Item(String name,String weight, String kind, String size) {
        this.name = name;
        this.weight = weight;
        this.kind = kind;
        this.size = size;
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

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", kind='" + kind + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
