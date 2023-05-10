package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetails extends AppCompatActivity implements  RecycleViewOnItemClick{
    RecyclerView recyclerView,rec2;
    TextView trxt;
    private List<Places> Salons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);


        Intent intent = getIntent();
        String salonId = intent.getStringExtra("salon_id");
        String salonName = intent.getStringExtra("salon_name");
        String salonImageUrl = intent.getStringExtra("salon_image");


        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(salonName);



        ImageView imageView = findViewById(R.id.imageView);



        // Use Glide to load the image into the ImageView
        Glide.with(this)
                .load(salonImageUrl)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}