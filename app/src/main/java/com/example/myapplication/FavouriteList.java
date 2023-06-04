package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavouriteList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom;
    RecyclerView recyclerView;
    DatabaseReference favoritesRef;
    ImageButton fav_btn;

    private List<FavItem> favItemList ;
    private FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

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
}
