package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlacesA extends AppCompatActivity implements RecycleViewOnItemClick {

    private List<Places> placesList = new ArrayList<>();
    private List<Places> Salons = new ArrayList<>();


    Adapter2 adapter;
    private RecyclerView recyclerView;
    private TextView namee, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        namee = findViewById(R.id.name);
        recyclerView = findViewById(R.id.dry);
       // text = findViewById(R.id.text1);

        adapter = new Adapter2(Salons, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);


        Salons.add(new Places("salon1", R.drawable.salon1));
        Salons.add(new Places("salon2", R.drawable.salon2));
        Salons.add(new Places("salon3", R.drawable.salon3));
        Salons.add(new Places("salon4", R.drawable.salon4));

        placesList.add(new Places("salon1", R.drawable.salon1));


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int image = intent.getIntExtra("image", 0);
        ArrayList<Places> placesList = intent.getParcelableArrayListExtra("places");

        // Update UI with data
        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(name);

      //  adapter.setData(placesList);

        }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}
