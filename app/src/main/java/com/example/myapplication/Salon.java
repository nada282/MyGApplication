package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Salon extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mbase;
    PlacesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
        recyclerView = findViewById(R.id.Salon_recycler);
        mbase = FirebaseDatabase.getInstance().getReference();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        FirebaseRecyclerOptions<PlacesClass> options =
                new FirebaseRecyclerOptions.Builder<PlacesClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("salon"), PlacesClass.class)
                        .build();
        adapter = new PlacesAdapter(options);
        recyclerView.setAdapter(adapter);

        setupAdapterClickListener();
    }

    private void setupAdapterClickListener() {
        adapter.setOnItemClickListener(new PlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot snapshot, int position) {

                PlacesClass salon = snapshot.getValue(PlacesClass.class);

                Intent intent = new Intent(Salon.this, PlacesList.class);
                intent.putExtra("salon_id", snapshot.getKey());
                intent.putExtra("salon_name", salon.getName());
                intent.putExtra("salon_image", salon.getImage());

                // Add any other necessary data as extras
                startActivity(intent);
            }
        });
    }

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
}
