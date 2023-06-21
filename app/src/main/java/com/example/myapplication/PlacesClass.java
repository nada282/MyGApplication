package com.example.myapplication;

public class PlacesClass {
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String image;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;


    public PlacesClass(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PlacesClass(String name, String image) {
        this.name = name;
        this.image = image;
    }
    public PlacesClass(String id,String name, String image) {
        this.id=id;
        this.name = name;
        this.image = image;
    }



}
