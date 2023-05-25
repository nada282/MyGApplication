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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DryCleanList extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener ,ItemListAdapter.OnItemClickListener{

    private BottomNavigationView bottom;
    private RecyclerView recyclerView;
    private ItemListAdapter adapter;
    private DatabaseReference servicesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dry_clean_list);
        recyclerView = findViewById(R.id.DrycleanList_recycler);

        // Get the details of the selected dryclean from the intent
        Intent intent = getIntent();
        String drycleanId = intent.getStringExtra("dryclean_id");
        String drycleanName = intent.getStringExtra("dryclean_name");
        String drycleanImageUrl = intent.getStringExtra("dryclean_image");

        // Set the title of the action bar to the name of the selected salon

        // Construct the database reference for the services of the selected Restaurant
        servicesRef = FirebaseDatabase.getInstance().getReference().child("Services");
        Query query = servicesRef.orderByChild("DryClean").equalTo(drycleanName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(query, ServicesClass.class)
                        .build();

        adapter = new ItemListAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(DataSnapshot snapshot, int position) {
        ServicesClass salon = snapshot.getValue(ServicesClass.class);

        Intent intent = new Intent(DryCleanList.this, ServiceDetails.class);
        intent.putExtra("id", snapshot.getKey());
        intent.putExtra("name", salon.getName());
        intent.putExtra("price", salon.getPrice());
        intent.putExtra("desc", salon.getDescription());
        intent.putExtra("image", salon.getImage());

        // Add any other necessary data as extras
        startActivity(intent);
    }
}
