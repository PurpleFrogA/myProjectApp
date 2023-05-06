package com.example.myprojectapp;

public class Item {
    private String name;
    private String id;
    private String weight;
    private String kind;
    private String size;
//private String al7aja;

    //private String picture;
    public Item(String name, String id, String weight, String kind, String size) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.kind = kind;
        this.size = size;
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
                ", id='" + id + '\'' +
                ", weight='" + weight + '\'' +
                ", kind='" + kind + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
