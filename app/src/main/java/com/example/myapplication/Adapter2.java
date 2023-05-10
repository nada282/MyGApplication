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

public class Adapter2 extends FirebaseRecyclerAdapter<SalonClass, Adapter2.SalonViewHolder> {

    private static RecycleViewOnItemClick mListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param salon
     */
    public Adapter2(@NonNull FirebaseRecyclerOptions<SalonClass> options, Salon salon) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SalonViewHolder holder, int position, @NonNull SalonClass model) {
        holder.bind(model);
    }
    public void cleanup() {
        stopListening();

    }

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homecard_view, parent, false);
        return new SalonViewHolder(view);
    }

    public static class SalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mNameTextView;

        public SalonViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mNameTextView = itemView.findViewById(R.id.txtName);

            itemView.setOnClickListener(this);
        }

        public void bind(SalonClass salon) {
            // Load the salon image using Glide or any other image loading library
            Glide.with(mImageView.getContext())
                    .load(salon.getImage())
                    .into(mImageView);
            mNameTextView.setText(salon.getName());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(position);
            }
        }
    }

    public interface RecycleViewOnItemClick {
        void onItemClick(int position);
    }
}
