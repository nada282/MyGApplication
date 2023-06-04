package com.example.myapplication;


public class FavItem {
    private String name;
    private String category;
    private String  imageUrl; // Change the type to Long

    public FavItem() {
        // Default constructor required for Firebase
    }

    public FavItem(String name, String category, String imageUrl) {
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String item_title) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String key_id) {
        this.category = category;
    }

    public String imageUrl() {
        return imageUrl;
    }

    public void imageUrl(String  imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlAsString() {
        // Manually convert the imageUrl from Long to String
        return (imageUrl != null) ? String.valueOf(imageUrl) : null;
    }
}


