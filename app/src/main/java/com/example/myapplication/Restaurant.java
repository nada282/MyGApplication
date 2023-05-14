package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends AppCompatActivity implements  RecycleViewOnItemClick{

    private List<PlacesClass> rest = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        RecyclerView recyclerView = findViewById(R.id.rest_recycler);

      /*  rest.add(new PlacesClass("salon1", R.drawable.restaurant1));
        rest.add(new PlacesClass("salon2", R.drawable.restaurant2));
        rest.add(new PlacesClass("salon3", R.drawable.restaurant3));
        rest.add(new PlacesClass("salon4", R.drawable.restaurant4));*/



     /*   recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Adapter1 adapter = new Adapter1(rest, this);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}