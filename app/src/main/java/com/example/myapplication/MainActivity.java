package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, RecycleViewOnItemClick, SearchView.OnQueryTextListener {

    private BottomNavigationView bottom;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private List<PlacesClass> novelsModels = new ArrayList<>();
    private List<PlacesClass> placesList = new ArrayList<>();
    private SearchView searchView;
    private PlacesAdapter adapter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView v = findViewById(R.id.slide);
        AnimationDrawable u = (AnimationDrawable) v.getDrawable();
        u.start();


        final DrawerLayout drawerLayout = findViewById(R.id.DrawerLayout);
        bottom = findViewById(R.id.bottom);

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

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Toast.makeText(this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
                return true;
            case R.id.map:
                Intent in1 = new Intent(this, Map.class);
                startActivity(in1);
                return true;
            case R.id.profile:
                Intent in2 = new Intent(this, Profile.class);
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
        Intent in = new Intent(this, Salon.class);
        startActivity(in);
    }

    public void superMarketclick(View view) {
        Intent in = new Intent(this, SuperMarket.class);
        startActivity(in);
    }

    public void DryCleanClick(View view) {
        Intent in = new Intent(this, DryClean.class);
        startActivity(in);
    }

    public void ResturantClick(View view) {
        Intent in = new Intent(this, Restaurant.class);
        startActivity(in);
    }

    public void DormsClick(View view) {
        Intent in = new Intent(this, Dorms.class);
        startActivity(in);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        search(s);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void search(String query) {
        DatabaseReference supermarketRef = FirebaseDatabase.getInstance().getReference().child("Supermarket");
        Query queryRef = supermarketRef.orderByChild("name").equalTo(query);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlacesClass supermarket = snapshot.getValue(PlacesClass.class);
                    // Perform any action with the searched supermarket
                    // For example, start the SupermarketItem activity and pass the supermarket ID
                    Intent intent = new Intent(MainActivity.this, SupermarketItemList.class);
                    intent.putExtra("supermarket_name", supermarket.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

        DatabaseReference salonRef = FirebaseDatabase.getInstance().getReference().child("salon");
        Query querysalon = salonRef.orderByChild("name").equalTo(query);
        querysalon.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlacesClass supermarket = snapshot.getValue(PlacesClass.class);
                    // Perform any action with the searched supermarket
                    // For example, start the SupermarketItem activity and pass the supermarket ID
                    Intent intent = new Intent(MainActivity.this, SalonList.class);
                    intent.putExtra("salon_name", supermarket.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

        //    ---------------------------------------
        DatabaseReference RestRef = FirebaseDatabase.getInstance().getReference().child("Resturant");
        Query queryRest = RestRef.orderByChild("name").equalTo(query);
        queryRest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlacesClass supermarket = snapshot.getValue(PlacesClass.class);
                    // Perform any action with the searched supermarket
                    // For example, start the SupermarketItem activity and pass the supermarket ID
                    Intent intent = new Intent(MainActivity.this,RestaurantList.class);
                    intent.putExtra("restaurant_name", supermarket.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });

        //-----------------------
        DatabaseReference dryRef = FirebaseDatabase.getInstance().getReference().child("DryClean");
        Query querydry = dryRef.orderByChild("name").equalTo(query);
        querydry.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlacesClass supermarket = snapshot.getValue(PlacesClass.class);
                    // Perform any action with the searched supermarket
                    // For example, start the SupermarketItem activity and pass the supermarket ID
                    Intent intent = new Intent(MainActivity.this, DryCleanList.class);
                    intent.putExtra("dryclean_name", supermarket.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });


        //-----------------------------------------

        DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("Items");
        Query queryitem = itemRef.orderByChild("name").equalTo(query);
        queryitem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServicesClass supermarket = snapshot.getValue(ServicesClass.class);
                    Intent intent = new Intent(MainActivity.this, ServiceDetails.class);

                    intent.putExtra("id", snapshot.getKey());
                    intent.putExtra("name", supermarket.getName());
                    intent.putExtra("price", supermarket.getPrice());
                    intent.putExtra("desc", supermarket.getDescription());
                    intent.putExtra("image", supermarket.getImage());

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });


        //------------------------------------------------

        DatabaseReference ItemRef = FirebaseDatabase.getInstance().getReference().child("\n" +
                "SuperMarketItems");
        Query queryItem = ItemRef.orderByChild("name").equalTo(query);
        queryItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServicesClass supermarket = snapshot.getValue(ServicesClass.class);
                    Intent intent = new Intent(MainActivity.this, ServiceDetails.class);

                    intent.putExtra("id", snapshot.getKey());
                    intent.putExtra("name", supermarket.getName());
                    intent.putExtra("price", supermarket.getPrice());
                    intent.putExtra("desc", supermarket.getDescription());
                    intent.putExtra("image", supermarket.getImage());

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });


        //-------------------------------------------------
        DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference().child("\n" +
                "Services");
        Query queryservice = serviceRef.orderByChild("name").equalTo(query);
        queryservice.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ServicesClass supermarket = snapshot.getValue(ServicesClass.class);
                    Intent intent = new Intent(MainActivity.this, ServiceDetails.class);

                    intent.putExtra("id", snapshot.getKey());
                    intent.putExtra("name", supermarket.getName());
                    intent.putExtra("price", supermarket.getPrice());
                    intent.putExtra("desc", supermarket.getDescription());
                    intent.putExtra("image", supermarket.getImage());

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }



}