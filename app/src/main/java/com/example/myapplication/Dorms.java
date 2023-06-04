package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Dorms extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom;
    RecyclerView recyclerView;
    DatabaseReference mbase;
    PlacesAdapter adapter;
    private List<PlacesClass> restaurant = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorms);
        RecyclerView recyclerView = findViewById(R.id.dorms_recycler);
        mbase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<PlacesClass> options =
                new FirebaseRecyclerOptions.Builder<PlacesClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Dorms"), PlacesClass.class)
                        .build();
        adapter = new PlacesAdapter(options);
        recyclerView.setAdapter(adapter);

        bottom = findViewById(R.id.bottom);
        BottomNavigationView nav1 = findViewById(R.id.bottom);
        nav1.setItemIconTintList(null);


        bottom.setOnNavigationItemSelectedListener(this);

        //  setupAdapterClickListener();
    }

//    private void setupAdapterClickListener() {
//        adapter.setOnItemClickListener(new PlacesAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(DataSnapshot snapshot, int position) {
//
//                PlacesClass supermarket = snapshot.getValue(PlacesClass.class);
//
//                Intent intent = new Intent();
//                intent.putExtra("dorm_id", snapshot.getKey());
//                intent.putExtra("dorm_name", supermarket.getName());
//                intent.putExtra("dorm_image", supermarket.getImage());
//
//                // Add any other necessary data as extras
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
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
}