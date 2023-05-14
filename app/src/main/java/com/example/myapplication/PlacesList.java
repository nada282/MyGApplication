package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PlacesList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private DatabaseReference servicesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        recyclerView = findViewById(R.id.placeList_recycler);

        // Get the details of the selected salon from the intent
        Intent intent = getIntent();
        String salonId = intent.getStringExtra("salon_id");
        String salonName = intent.getStringExtra("salon_name");
        String salonImageUrl = intent.getStringExtra("salon_image");

        // Set the title of the action bar to the name of the selected salon

        // Construct the database reference for the services of the selected salon
        servicesRef = FirebaseDatabase.getInstance().getReference().child("Services");
        Query query = servicesRef.orderByChild("salon").equalTo(salonName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(query, ServicesClass.class)
                        .build();

        adapter = new ItemListAdapter(options);
        recyclerView.setAdapter(adapter);
        // Create a FirebaseRecyclerOptions object for the adapter


        // Create a new instance of ItemListAdapter and set it as the adapter for the RecyclerView

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
}
