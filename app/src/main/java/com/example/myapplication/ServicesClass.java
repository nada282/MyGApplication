package com.example.myapplication;

public class ServicesClass {

    public int getId() {
        return id;
    }

    private int id;
    private String name;
    private String image;
    private double price;
    private String description;

    public ServicesClass() {}

    public ServicesClass(int id,String name, String image, double price, String description) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
