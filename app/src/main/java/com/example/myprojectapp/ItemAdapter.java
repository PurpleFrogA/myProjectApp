package com.example.myprojectapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.grpc.Context;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;

    ArrayList<Items> listItem;

    public ItemAdapter(Context context, ArrayList<Items> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Items items = listItem.get(position);
        holder.name.setText(items.getName());
        holder.weight.setText(items.getWeight());
        holder.size.setText(items.getSize());
        holder.kind.setText(items.getKind());

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name,weight,size,kind;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvNameItem);
            weight= itemView.findViewById(R.id.tvWeightItem);
            size = itemView.findViewById(R.id.tvSizeItem);
            kind = itemView.findViewById(R.id.tvKindItem);

        }
    }
}
