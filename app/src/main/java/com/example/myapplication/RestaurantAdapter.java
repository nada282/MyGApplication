package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant_Fav> restaurantList;
    private List<Restaurant_Fav> favoritesList;

    public RestaurantAdapter(List<Restaurant_Fav> restaurantList, List<Restaurant_Fav> favoritesList) {
        this.restaurantList=restaurantList;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_list, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant_Fav restaurant = restaurantList.get(position);
        holder.nameTextView.setText(restaurant.getName());
        holder.addressTextView.setText(restaurant.getPlace());
        // Set other views

        // Check if the restaurant is already in favoritesList
        boolean isFavorite = favoritesList.contains(restaurant);
        holder.favoriteButton.setSelected(isFavorite);

        holder.favoriteButton.setOnClickListener(v -> {
            if (isFavorite) {
                favoritesList.remove(restaurant);
                removeFromFavorites(restaurant);
            } else {
                favoritesList.add(restaurant);
                addToFavorites(restaurant);
            }
            notifyItemChanged(position);
        });
    }

    private void addToFavorites(Restaurant_Fav restaurant) {
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("Favorites");
        favoritesRef.child(restaurant.getName()).setValue(restaurant);
    }

    private void removeFromFavorites(Restaurant_Fav restaurant) {
        DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference("Favorites");
        favoritesRef.child(restaurant.getName()).removeValue();
    }
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        Button favoriteButton;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            addressTextView = itemView.findViewById(R.id.productName);
            favoriteButton = itemView.findViewById(R.id.productImage);
        }
    }
    private void addToFav(Restaurant_Fav res_fav){
        DatabaseReference userfav = FirebaseDatabase
                .getInstance()
                .getReference("Resturant")
                .child("Items");
        userfav.child(res_fav.getName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){


                        }
                        else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
