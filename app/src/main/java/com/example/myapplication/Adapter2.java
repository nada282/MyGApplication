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
import com.google.firebase.database.DatabaseReference;

public class Adapter2 extends FirebaseRecyclerAdapter<SalonClass, Adapter2.SalonViewHolder> {

    private OnItemClickListener listener;

    public Adapter2(@NonNull FirebaseRecyclerOptions<SalonClass> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull SalonViewHolder holder, int position, @NonNull SalonClass model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homecard_view, parent, false);
        return new SalonViewHolder(view);
    }

    public void cleanup() {
        stopListening();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mNameTextView;
        private DatabaseReference salonRef;

        public SalonViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.txtName);

            itemView.setOnClickListener(this);
        }

        public void bind(SalonClass salon) {
            Glide.with(mImageView.getContext())
                    .load(salon.getImage())
                    .into(mImageView);
            mNameTextView.setText(salon.getName());
        }

        @Override
        public void onClick(View view) {
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
