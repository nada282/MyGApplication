package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Places implements Parcelable {
    private String name;
    private int imageID;

    public Places(String name, int imageID) {
        this.name = name;
        this.imageID = imageID;
    }

    protected Places(Parcel in) {
        name = in.readString();
        imageID = in.readInt();
    }

    public static final Creator<Places> CREATOR = new Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel in) {
            return new Places(in);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };
    

    public String getName() {
        return name;
    }

    public int getImageID() {
        return imageID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(imageID);
    }
}
