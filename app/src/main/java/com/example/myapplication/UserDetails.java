package com.example.myapplication;

import android.view.View;

public class UserDetails {
    public String fullName;

//    public String dob;
    public String password;
//    public String gender ;
    public String mobileNumber;
    public String location;
   public String email;
UserDetails(){}




    UserDetails(String fullName, String password, String  mobileNumber , String location, String email){
    this.fullName=fullName;
    //    this.dob=dob;
        this.password=password;
      //  this.gender=gender;
        this.mobileNumber=mobileNumber;
        this.location=location;
        this.email=email;

    }

    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        name = name;
    }

//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobile) {
        mobileNumber = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
