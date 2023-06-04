package com.example.myapplication;

public class Restaurant_Fav extends Restaurant {
    private String name;
    private String place;
    private String imageUrl;

    public Restaurant_Fav(String name, String place, String imageUrl){
        this.name=name;
        this.place=place;
        this.imageUrl=imageUrl;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
