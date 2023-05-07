package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dorms extends AppCompatActivity implements  RecycleViewOnItemClick{

    private List<Places> dorms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorms);
        RecyclerView recyclerView = findViewById(R.id.dorms_recycler);

        dorms.add(new Places("dorms1", R.drawable.dorms1));
        dorms.add(new Places("dorms2", R.drawable.dorms2));
        dorms .add(new Places("dorms3", R.drawable.dorms3));
        dorms .add(new Places("dorms4", R.drawable.dorms4));



        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Adapter1 adapter = new Adapter1(dorms , this);
        recyclerView.setAdapter(adapter);
    }

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
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}