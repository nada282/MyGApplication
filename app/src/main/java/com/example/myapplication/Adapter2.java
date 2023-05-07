package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder>{


    private List<Places> novelsModels;
    private RecycleViewOnItemClick recycleViewOnItemClick;

    public Adapter2(List<Places> novelsModels, RecycleViewOnItemClick recycleViewOnItemClick) {
        this.novelsModels = novelsModels;
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.homecard_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(novelsModels.get(position).getName());

        holder.image.setImageResource(novelsModels.get(position).getImageID());
        holder.setIsRecyclable(true);
    }


    public void setData(List<Places> placesList) {
        novelsModels = placesList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return novelsModels.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.txtName);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recycleViewOnItemClick.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    recycleViewOnItemClick.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}