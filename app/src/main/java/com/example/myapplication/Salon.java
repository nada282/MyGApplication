package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;


public class Salon extends AppCompatActivity implements RecycleViewOnItemClick , Serializable {
    RecyclerView recyclerView,rec2;
    TextView trxt;
    DatabaseReference mbase;
    Adapter2 adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        recyclerView = findViewById(R.id.Salon_recycler);
        mbase = FirebaseDatabase.getInstance().getReference();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<SalonClass> options =
                new FirebaseRecyclerOptions.Builder<SalonClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("salon"), SalonClass.class)
                        .build();

        try {
            adapter = new Adapter2(options, this); // Set the listener to this activity
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Make sure to remove the listener to avoid memory leaks
            if (adapter != null) {
                adapter.cleanup();
            }
        }
    }
    @Override
    public void onItemClick(int position) {

        System.out.print("raghaddddddddddddddddddd");
        SalonClass selectedPlace = adapter.getItem(position);
        Intent intent;
        switch(selectedPlace.getName()) {
            case "Salon1":
                intent = new Intent(this, Salon1.class);
                break;
            case "Salon2":
                intent = new Intent(this, Salon2.class);
                break;
            // Add cases for other salons as required
            default:
                return;
        }
        intent.putExtra("place", String.valueOf(selectedPlace));

        startActivity(intent);
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }









    @Override
    public void onLongItemClick(int position) {

    }


}