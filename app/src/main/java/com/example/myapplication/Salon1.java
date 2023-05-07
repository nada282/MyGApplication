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

public class Salon1 extends AppCompatActivity implements  RecycleViewOnItemClick{
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Adapter2 adapter = new Adapter2(Salons, this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick(int position) {
    }
    @Override
    public void onLongItemClick(int position) {
    }
}