package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Profile extends AppCompatActivity {
private Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnlogout =findViewById(R.id.button2);

    }

    public void btnlogout(View view) {

        Intent intent = new Intent(Profile.this,
               Registration.class);
        startActivity(intent);
    }
}