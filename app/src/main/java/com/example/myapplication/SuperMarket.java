package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SuperMarket extends AppCompatActivity implements  RecycleViewOnItemClick{

    private List<Places> supermarket = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_market);
        RecyclerView recyclerView = findViewById(R.id.supermarket_recycler);

        supermarket .add(new Places("supermarket1", R.drawable.supermarket1));
        supermarket .add(new Places("supermarket2", R.drawable.supermarket2));
        supermarket .add(new Places("supermarket3", R.drawable.supermarket3));
        supermarket .add(new Places("supermarket4", R.drawable.supermarket4));



        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Adapter1 adapter = new Adapter1(supermarket , this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}