package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SalonList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ItemListAdapter.OnItemClickListener {

    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private DatabaseReference servicesRef;
    private SearchView searchView;

    private RatingBar rating;
    private String salonId;
    String salonName;
    String salonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        searchView = findViewById(R.id.searchButton);

        recyclerView = findViewById(R.id.placeList_recycler);

        rating = findViewById(R.id.ratingBar);

        ImageView salonImageView = findViewById(R.id.restaurantImageView);

        Intent intent = getIntent();
         salonImage = intent.getStringExtra("salon_image");

        Glide.with(this)
                .load(salonImage)
                .centerCrop()
                .into(salonImageView);

        salonId = intent.getStringExtra("salon_id");
         salonName = intent.getStringExtra("salon_name");
         salonImage = intent.getStringExtra("salon_image");


        servicesRef = FirebaseDatabase.getInstance().getReference().child("Services");
        Query query = servicesRef.orderByChild("salon").equalTo(salonName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(query, ServicesClass.class)
                        .build();

        adapter = new ItemListAdapter(options);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        bottom = findViewById(R.id.bottom);
        BottomNavigationView nav1 = findViewById(R.id.bottom);
        nav1.setItemIconTintList(null);

        bottom.setOnNavigationItemSelectedListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do nothing when the search query is submitted
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Call searchFirebase method to filter the data based on the entered query
                searchFirebase(newText);
                return true;
            }
        });


        DatabaseReference salonRef = FirebaseDatabase.getInstance().getReference().child("salon");
        Query query1 = salonRef.orderByChild("name").equalTo(salonName);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Float ratingValue = childSnapshot.child("rating").getValue(Float.class);
                        float ratingFloat = ratingValue != null ? ratingValue : 0.0f;
                        rating.setRating(ratingFloat);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });


        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateRating(rating);
            }
        });

    }

    private void updateRating(float rating) {
        DatabaseReference salonRef = FirebaseDatabase.getInstance().getReference().child("salon");
        salonRef.child(salonId).child("rating").setValue(rating);


        DatabaseReference recommendedRef = FirebaseDatabase.getInstance().getReference().child("RecommendedSalon");
        recommendedRef.child(salonId).child("rating").setValue(rating);
        recommendedRef.child(salonId).child("name").setValue(salonName);
        recommendedRef.child(salonId).child("image").setValue(salonImage);
    }

    private void searchFirebase(String query) {
        Query searchQuery = servicesRef.orderByChild("name")
                .startAt(query)
                .endAt(query + "\uf8ff");

        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(searchQuery, ServicesClass.class)
                        .build();

        adapter.updateOptions(options);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
                return true;
            case R.id.map:
                Intent in1 = new Intent(this, Map.class);
                startActivity(in1);
                return true;
            case R.id.profile:
                Intent in2 = new Intent(this, Profile.class);
                startActivity(in2);
                return true;
        }
        return false;
    }

    @Override
    public void onItemClick(DataSnapshot snapshot, int position) {
        ServicesClass salon = snapshot.getValue(ServicesClass.class);

        Intent intent = new Intent(SalonList.this, ServiceDetails.class);
        intent.putExtra("id", snapshot.getKey());
        intent.putExtra("name", salon.getName());
        intent.putExtra("price", salon.getPrice());
        intent.putExtra("desc", salon.getDescription());
        intent.putExtra("image", salon.getImage());

        // Add any other necessary data as extras
        startActivity(intent);
    }
}
