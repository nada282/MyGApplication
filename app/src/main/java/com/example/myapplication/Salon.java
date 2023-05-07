package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Salon extends AppCompatActivity implements  RecycleViewOnItemClick{
    RecyclerView recyclerView,rec2;
    TextView trxt;
    private List<Places> Salons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);
         recyclerView = findViewById(R.id.Salon_recycler);



        Salons.add(new Places("salon1", R.drawable.salon1));
        Salons.add(new Places("salon2", R.drawable.salon2));
        Salons.add(new Places("salon3", R.drawable.salon3));
        Salons.add(new Places("salon4", R.drawable.salon4));


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Adapter2 adapter = new Adapter2(Salons, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {

        Places selectedPlace = Salons.get(position);
        Intent intent;
        switch(selectedPlace.getName()) {
            case "salon1":
                intent = new Intent(this, Salon1.class);
                break;
            case "salon2":
                intent = new Intent(this, Salon2.class);
                break;
            // Add cases for other salons as required
            default:
                return;
        }
        intent.putExtra("place", selectedPlace);
        startActivity(intent);
        }



    @Override
    public void onLongItemClick(int position) {

    }
}