package com.example.myapplication;

import android.content.ClipData;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// RecentlyViewedActivity.java
// RecentlyViewedActivity.java
public class RecentlyView extends AppCompatActivity {

    private RecyclerView recentlyViewedRecyclerView;
    private PlacesAdapter adapter;
    private List<PlacesClass> recentlyViewedList;

    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_view);

        // Get a reference to the Firebase Realtime Database
        databaseRef = FirebaseDatabase.getInstance().getReference();

        recentlyViewedRecyclerView = findViewById(R.id.Recently_recycler);
        recentlyViewedRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference recentlyViewedRef = databaseRef.child("RecentlyView");

        recentlyViewedList = new ArrayList<>();

        Query query = recentlyViewedRef.orderByChild("name");

        FirebaseRecyclerOptions<PlacesClass> options =
                new FirebaseRecyclerOptions.Builder<PlacesClass>()
                        .setQuery(query, PlacesClass.class)
                        .build();


        adapter = new PlacesAdapter(options);
        recentlyViewedRecyclerView.setAdapter(adapter);

        // Call a method to retrieve recently viewed items from the database
        retrieveRecentlyViewedItems();
    }

    private void retrieveRecentlyViewedItems() {
        // Retrieve recently viewed items from the database
       // String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference recentlyViewedRef = databaseRef.child("RecentlyView");

        recentlyViewedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recentlyViewedList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    PlacesClass item = itemSnapshot.getValue(PlacesClass.class);
                    recentlyViewedList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                // Handle database retrieval error
            }
        });



    }

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
