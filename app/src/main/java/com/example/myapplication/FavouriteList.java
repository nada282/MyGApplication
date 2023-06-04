package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavouriteList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,PlacesAdapter.OnItemClickListener{

    private BottomNavigationView bottom;
    RecyclerView recyclerView;
    DatabaseReference favoritesRef;
    ImageButton fav_btn;

    private List<PlacesClass> favItemList ;
    private PlacesAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);



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