package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlacesA extends AppCompatActivity  {

    private List<PlacesClass> placesList = new ArrayList<>();
    private List<PlacesClass> Salons = new ArrayList<>();


    ItemListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView namee, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        namee = findViewById(R.id.name);
        recyclerView = findViewById(R.id.dry);
        // text = findViewById(R.id.text1);
    }
}

     /*   adapter = new Adapter2(Salons, this);
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

        }*/


 /*   @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onLongItemClick(int position) {

    }*/
//}
