package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SupermarketItemList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private DatabaseReference servicesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket_item_list);
        recyclerView = findViewById(R.id.SupermarketItemList_recycler);

        // Get the details of the selected salon from the intent
        Intent intent = getIntent();
        String supermarketId = intent.getStringExtra("supermarket_id");
        String supermarketName = intent.getStringExtra("supermarket_name");
        String supermarketImageUrl = intent.getStringExtra("supermarket_image");

        // Set the title of the action bar to the name of the selected salon

        // Construct the database reference for the services of the selected salon
        servicesRef = FirebaseDatabase.getInstance().getReference().child("Items");
        Query query = servicesRef.orderByChild("Supermarket").equalTo(supermarketName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(query, ServicesClass.class)
                        .build();

        adapter = new ItemListAdapter(options);
        recyclerView.setAdapter(adapter);
        // Create a FirebaseRecyclerOptions object for the adapter

        bottom = findViewById(R.id.bottom);
        BottomNavigationView nav1 = findViewById(R.id.bottom);
        nav1.setItemIconTintList(null);


        bottom.setOnNavigationItemSelectedListener(this);
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
