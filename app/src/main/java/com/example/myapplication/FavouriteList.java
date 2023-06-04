package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

=======
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
>>>>>>> origin/master
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< HEAD
=======
import com.google.firebase.database.Query;
>>>>>>> origin/master
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


<<<<<<< HEAD
public class FavouriteList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
=======
public class FavouriteList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,PlacesAdapter.OnItemClickListener{
>>>>>>> origin/master

    private BottomNavigationView bottom;
    RecyclerView recyclerView;
    DatabaseReference favoritesRef;
    ImageButton fav_btn;

<<<<<<< HEAD
    private List<FavItem> favItemList ;
    private FavAdapter favAdapter;
=======
    private List<PlacesClass> favItemList ;
    private PlacesAdapter favAdapter;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

<<<<<<< HEAD
        // Step 1: Set up Firebase in your Android project
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        favoritesRef = database.getReference("Favourite");

        favItemList=new ArrayList<>();
        favAdapter= new FavAdapter(favItemList);

        recyclerView = findViewById(R.id.fav_recycler);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favAdapter);



        // Step 2: Create and add a sample item to the Firebase database
        FavItem item = new FavItem("Name", "Restaurant","https://yummy.ps/upload/1653307363--%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A8%D8%B9%D8%B8%D9%85.png");
        String itemId = favoritesRef.push().getKey();
        favoritesRef.child(itemId).setValue(item);

        // Step 3: Listen for changes in the Firebase database and update the favorite list
        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favItemList.clear(); // Clear existing data in your favorite list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FavItem item = snapshot.getValue(FavItem.class);
                    favItemList.add(item); // Add item to your favorite list

                    Log.d("FavItem", "Name: " + item.getName());
                    Log.d("FavItem", "Category: " + item.getCategory());
                    Log.d("FavItem", "Image URL: " + item.getImageUrlAsString());
                }
                favAdapter.notifyDataSetChanged(); // Notify the adapter about the data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error case
            }
        });

        // Step 4: Set up the adapter for the RecyclerView
        favAdapter = new FavAdapter(favItemList);
        recyclerView.setAdapter(favAdapter);

        // Step 5: Implement adding items to the favorite list
//        fav_btn = findViewById(R.id.favoriteList);
//        fav_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Perform action when the favorite button is clicked
//                FavItem newItem = new FavItem("New Item", "New Item ID", R.drawable.favourite_shadow);
//                addToFavorites(newItem);
//            }
//        });

        // ...
    }

    private void addToFavorites(FavItem item) {
        String itemId = favoritesRef.push().getKey();
        favoritesRef.child(itemId).setValue(item);
=======


        recyclerView = findViewById(R.id.Favourite_recycler);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        favoritesRef = FirebaseDatabase.getInstance().getReference().child("Favourite");
        FirebaseRecyclerOptions<PlacesClass> options =
                new FirebaseRecyclerOptions.Builder<PlacesClass>()
                        .setQuery(favoritesRef, snapshot -> {
                            String productName = snapshot.getKey(); // Get the product name as the key
                            if (productName != null) {
                                return new PlacesClass(productName, snapshot.child("image").getValue(String.class));
                            }
                            return null;
                        })
                        .build();

        favAdapter = new PlacesAdapter(options);
        favAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(favAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        favAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        favAdapter.stopListening();
>>>>>>> origin/master
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/master
                startActivity(in2);
                return true;
        }
        return false;
    }
<<<<<<< HEAD
}
=======


    @Override
    public void onItemClick(DataSnapshot snapshot, int position) {
        ServicesClass salon = snapshot.getValue(ServicesClass.class);

        Intent intent = new Intent(FavouriteList.this, ServiceDetails.class);
        intent.putExtra("id", snapshot.getKey());
        intent.putExtra("name", salon.getName());
        intent.putExtra("price", salon.getPrice());
        intent.putExtra("desc", salon.getDescription());
        intent.putExtra("image", salon.getImage());

        // Add any other necessary data as extras
        startActivity(intent);
    }
    }
>>>>>>> origin/master
