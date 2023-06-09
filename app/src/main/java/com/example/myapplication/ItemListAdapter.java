package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends FirebaseRecyclerAdapter<ServicesClass, ItemListAdapter.SalonViewHolder> {

    private OnItemClickListener listener;

    public ItemListAdapter(@NonNull FirebaseRecyclerOptions<ServicesClass> options) {
        super(options);
    }

    public void updateData(Query query) {
        FirebaseRecyclerOptions<ServicesClass> options =
                new FirebaseRecyclerOptions.Builder<ServicesClass>()
                        .setQuery(query, ServicesClass.class)
                        .build();

        updateOptions(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull SalonViewHolder holder, int position, @NonNull ServicesClass model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new SalonViewHolder(view);


    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView serviceimage;
        private TextView name;
        private TextView price;
        // Add more TextViews or views as needed


        public SalonViewHolder(View itemView) {
            super(itemView);
            serviceimage = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            // Initialize more TextViews or views as needed

            itemView.setOnClickListener(this);
        }

        public void bind(ServicesClass salon) {
            Glide.with(serviceimage.getContext())
                    .load(salon.getImage())
                    .into(serviceimage);
            name.setText(salon.getName());
            price.setText(String.valueOf(salon.getPrice()));
            // Set values for more TextViews or views as needed
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(getSnapshots().getSnapshot(position), position);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DataSnapshot snapshot, int position);
    }
}
