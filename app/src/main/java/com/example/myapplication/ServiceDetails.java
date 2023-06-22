package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ServiceDetails extends AppCompatActivity {

    private ImageView serviceImageView;
    private TextView serviceNameTextView;
    private TextView servicePriceTextView;
    private TextView serviceDescriptionTextView;
    private ImageButton favorite;
    private CollectionReference favoritesRef;
    String salonName;
    String salonId;
    String salonImageUrl;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        favorite = findViewById(R.id.favoriteList);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is authenticated, continue with accessing user information
            String uid = user.getUid();
            favoritesRef = FirebaseFirestore.getInstance()
                    .collection("User")
                    .document(uid)
                    .collection("favorite");
            // Rest of your code
        } else {

            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
            finish(); // Optional: Close the current activity to prevent the user from navigating back

            // User is not authenticated, handle this case accordingly
            Toast.makeText(this, "Please log in to access this feature.", Toast.LENGTH_SHORT).show();

        }




        serviceImageView = findViewById(R.id.productImage);
        serviceNameTextView = findViewById(R.id.productName);
        servicePriceTextView = findViewById(R.id.productPrice);
        serviceDescriptionTextView = findViewById(R.id.productDesc);

        Intent intent = getIntent();
         salonId = intent.getStringExtra("id");
         salonName = intent.getStringExtra("name");
        double salonPrice = intent.getDoubleExtra("price", 0);
        String salonDesc = intent.getStringExtra("desc");
        salonImageUrl = intent.getStringExtra("image");

        serviceNameTextView.setText(salonName);
        servicePriceTextView.setText(String.valueOf(salonPrice));
        serviceDescriptionTextView.setText(salonDesc);

        // Check if the item is already in favorites and update the button color
        favoritesRef.document(salonId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                isFavorite = true;
                favorite.setColorFilter(Color.RED);
            } else {
                isFavorite = false;
                favorite.setColorFilter(Color.WHITE);
            }
        }).addOnFailureListener(e -> {
            // Failed to retrieve favorite status
        });

        favorite.setOnClickListener(v -> {
            if (isFavorite) {
                removeFromFavorites(salonId);
                favorite.setColorFilter(Color.WHITE);
                Toast.makeText(ServiceDetails.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                ServicesClass newItem = new ServicesClass(salonId, salonName, salonImageUrl, salonPrice);
                addToFavorites(newItem);
                favorite.setColorFilter(Color.RED);
                Toast.makeText(ServiceDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            isFavorite = !isFavorite; // Toggle the favorite state
        });

        Glide.with(this)
                .load(salonImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(serviceImageView);
    }

    private void addToFavorites(ServicesClass item) {
        favoritesRef.document(item.getId()).set(item)
                .addOnSuccessListener(aVoid -> {

                })
                .addOnFailureListener(e -> {
                    // Error occurred while adding the item to favorites
                });
    }

    private void removeFromFavorites(String itemId) {
        favoritesRef.document(itemId).delete()
                .addOnSuccessListener(aVoid -> {
                    // Item removed from favorites successfully
                })
                .addOnFailureListener(e -> {
                    // Error occurred while removing the item from favorites
                });
    }
}
