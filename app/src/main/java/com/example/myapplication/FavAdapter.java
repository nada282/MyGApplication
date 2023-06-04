package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private List<FavItem> favItemList;

    public FavAdapter(List<FavItem> favItemList) {
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_list, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FavItem item = favItemList.get(position);
        holder.titleTextView.setText(item.getName());
        holder.imageView.setImageResource(Integer.parseInt((item.getImageUrlAsString())));
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public FavViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.productName);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
