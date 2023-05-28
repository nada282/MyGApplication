package com.example.myapplication;

import android.view.View;

public class UserDetails {
    public String Name;

    public String dob;
    public String password;
    public String gender ;
    public String Mobile;
UserDetails(){}
    UserDetails(String Name,String dob,String password,String gender,String  Mobile){
        this.dob=dob;
        this.password=password;
        this.gender=gender;
        this.Mobile=Mobile;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
