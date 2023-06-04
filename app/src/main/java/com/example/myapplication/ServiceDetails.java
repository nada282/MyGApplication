package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceDetails extends AppCompatActivity {

    private ImageView serviceImageView;
    private TextView serviceNameTextView;
    private TextView servicePriceTextView;
    private TextView serviceDescriptionTextView;
    private ImageButton favorite;
    DatabaseReference favoritesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        favorite = findViewById(R.id.favoriteList);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        favoritesRef = database.getReference("Favourite");


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

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when the favorite button is clicked
                PlacesClass newItem = new PlacesClass(salonName, salonImageUrl);
                addToFavorites(newItem);

                favorite.setColorFilter(Color.RED);

            }
        });



        Glide.with(this)
                .load(salonImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(serviceImageView);


        // Load the image using Glide library

        }

    private void addToFavorites(PlacesClass item) {
        String itemName = item.getName();
        favoritesRef.child(itemName).setValue(item);

    }
    }

