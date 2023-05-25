package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

public class ServiceDetails extends AppCompatActivity {

    private ImageView serviceImageView;
    private TextView serviceNameTextView;
    private TextView servicePriceTextView;
    private TextView serviceDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        serviceImageView = findViewById(R.id.productImage);
        serviceNameTextView = findViewById(R.id.productName);
        servicePriceTextView = findViewById(R.id.productPrice);
        serviceDescriptionTextView = findViewById(R.id.productDesc);

        Intent intent = getIntent();
        String salonId = intent.getStringExtra("id");
        String salonName = intent.getStringExtra("name");
        double salonprice = intent.getDoubleExtra("price",0);
        String salondesc = intent.getStringExtra("desc");
        String salonImageUrl = intent.getStringExtra("image");

        serviceNameTextView.setText(salonName);
        servicePriceTextView.setText(String.valueOf(salonprice));
        serviceDescriptionTextView.setText(salondesc);

        Glide.with(this)
                .load(salonImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(serviceImageView);


        // Load the image using Glide library

        }
    }

