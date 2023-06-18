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

public class SupermarketItemList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener ,ItemListAdapter.OnItemClickListener{

    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private RatingBar rating;
    private String supermarketId;
    String supermarketName;


    private DatabaseReference servicesRef;
    private SearchView searchView;
    String Image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket_item_list);
        recyclerView = findViewById(R.id.SupermarketItemList_recycler);

        rating = findViewById(R.id.ratingBar);


        ImageView imageView = findViewById(R.id.image);

        Intent intent = getIntent();
         Image = intent.getStringExtra("supermarket_image");

        // Load the supermarket image into the ImageView using Glide
        Glide.with(this)
                .load(Image)
                .centerCrop()
                .into(imageView);

        searchView = findViewById(R.id.searchButton);

        // Get the details of the selected supermarket from the intent
        supermarketId = intent.getStringExtra("supermarket_id");
        supermarketName = intent.getStringExtra("supermarket_name");

        DatabaseReference supermarketRef = FirebaseDatabase.getInstance().getReference().child("Supermarket");
        Query query = supermarketRef.orderByChild("name").equalTo(supermarketName);
        query.addValueEventListener(new ValueEventListener() {
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

        // Construct the database reference for the services of the selected supermarket
        servicesRef = FirebaseDatabase.getInstance().getReference().child("SuperMarketItems");
        Query servicesQuery = servicesRef.orderByChild("Supermarket").equalTo(supermarketName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(servicesQuery, ServicesClass.class)
                        .build();

        adapter = new ItemListAdapter(options);
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);

        bottom = findViewById(R.id.bottom);
        bottom.setItemIconTintList(null);

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

    private void updateRating(float rating) {
        DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference().child("Supermarket");
        restaurantRef.child(supermarketId).child("rating").setValue(rating);

        DatabaseReference recommendedRef = FirebaseDatabase.getInstance().getReference().child("RecommendedMarket");
        recommendedRef.child(supermarketId).child("rating").setValue(rating);
        recommendedRef.child(supermarketId).child("name").setValue(supermarketName);
        recommendedRef.child(supermarketId).child("image").setValue(Image);
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
        switch(id){
            case R.id.home:
                Intent in = new Intent (this,MainActivity.class);
                startActivity(in);
                return true;
            case R.id.map:
                Intent in1 = new Intent (this,Map.class);
                startActivity(in1);
                return true;
            case R.id.profile:
                Intent in2 = new Intent (this,Profile.class);
                startActivity(in2);
                return true;
        }
        return false;
    }

    @Override
    public void onItemClick(DataSnapshot snapshot, int position) {
        ServicesClass supermarket = snapshot.getValue(ServicesClass.class);

        String itemName = supermarket.getName();
        String itemNimage = supermarket.getImage();


        PlacesClass selectedItem = new PlacesClass(itemName,itemNimage);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference recentlyViewedRef = databaseRef.child("RecentlyView");

        recentlyViewedRef.child(itemName).setValue(selectedItem);

        Intent intent = new Intent(SupermarketItemList.this, ServiceDetails.class);
        intent.putExtra("id", snapshot.getKey());
        intent.putExtra("name", supermarket.getName());
        intent.putExtra("price", supermarket.getPrice());
        intent.putExtra("desc", supermarket.getDescription());
        intent.putExtra("image", supermarket.getImage());

        // Add any other necessary data as extras
        startActivity(intent);
    }
}
