package com.example.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , RecycleViewOnItemClick{

    private BottomNavigationView bottom;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private List<PlacesClass> novelsModels = new ArrayList<>();
    private List<PlacesClass> placesList = new ArrayList<>();



    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  RecyclerView recyclerView = findViewById(R.id.place_recycler);
        final DrawerLayout drawerLayout = findViewById(R.id.DrawerLayout);
        //  final BottomNavigationView bottom=findViewById(R.id.bottom_nav);
        //  navigationView=findViewById(R.id.navigation);
        bottom = findViewById(R.id.bottom);




      /*  toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        bottom.setOnNavigationItemSelectedListener(this);


        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView nav = findViewById(R.id.navigation);
        nav.setItemIconTintList(null);

        BottomNavigationView nav1 = findViewById(R.id.bottom);
        nav1.setItemIconTintList(null);



  /*   novelsModels.add(new PlacesClass("DryClean", R.drawable.dry));
        novelsModels.add(new PlacesClass("SuperMarket", R.drawable.market));
        novelsModels.add(new PlacesClass("Dorms", R.drawable.dorms));
        novelsModels.add(new PlacesClass("Resturant", R.drawable.resturant));
        novelsModels.add(new PlacesClass("Salon", R.drawable.salon));*/



     //   recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    //    Adapter1 adapter = new Adapter1(novelsModels, this);
    //    recyclerView.setAdapter(adapter);





        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is already granted, get the location
            getLocation();
        }


    }


    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted, get the location
                getLocation();
            } else {
                // Permission was denied, show a message or take other action
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Get the device's location
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Do something with the location
                Toast.makeText(this, "Latitude: " + latitude + ", Longitude: " + longitude,
                        Toast.LENGTH_LONG).show();
            }
        }

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
    public void onItemClick(int position) {


    }

    @Override
    public void onLongItemClick(int position) {

    }

    public void salonclick(View view) {
        Intent in = new Intent(this,Salon.class);
        startActivity(in);
    }

    public void superMarketclick(View view) {
        Intent in = new Intent(this,SuperMarket.class);
        startActivity(in);
    }

    public void DryCleanClick(View view) {
        Intent in = new Intent(this,DryClean.class);
        startActivity(in);
    }

    public void ResturantClick(View view) {
        Intent in = new Intent(this, Restaurant.class);
        startActivity(in);
    }


    public void DormsClick(View view) {
        Intent in = new Intent(this,Dorms.class);
        startActivity(in);
    }
}