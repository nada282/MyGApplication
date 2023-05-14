package com.example.myapplication;

public class ServicesClass {

    private String name;
    private String image;
    private double price;
    private String description;

    public ServicesClass() {}

    public ServicesClass(String name, String image, double price, String description) {
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
