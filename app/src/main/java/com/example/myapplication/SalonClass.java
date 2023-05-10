package com.example.myapplication;

public class SalonClass {

    String Name, image;

    public SalonClass() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public SalonClass(String name, String image) {
        Name = name;
        this.image = image;
    }

}
