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

import java.util.List;

public class RecommendAdapter extends FirebaseRecyclerAdapter<PlacesClass, RecommendAdapter.SalonViewHolder> {

    private OnItemClickListener listener;

    public RecommendAdapter(@NonNull FirebaseRecyclerOptions<PlacesClass> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull SalonViewHolder holder, int position, @NonNull PlacesClass model) {
        holder.bind(model);

    }



    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendcard, parent, false);
        return new SalonViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mNameTextView;


        public SalonViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.txtName);

            itemView.setOnClickListener(this);
        }

        public void bind(PlacesClass salon) {
            Glide.with(mImageView.getContext())
                    .load(salon.getImage())
                    .into(mImageView);
            mNameTextView.setText(salon.getName());
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
