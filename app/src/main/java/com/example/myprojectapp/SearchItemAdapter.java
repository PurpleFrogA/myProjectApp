package com.example.myprojectapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>{

    Context context;
    List<Item> listSearchItem;
    FirebaseServices fbs;
    ArrayList<String> itemListPath;

    //ArrayList<Item> itemList;

    public SearchItemAdapter(Context context, List<Item> listItem, ArrayList<String> itemListPath) {
        this.context = context;
        this.listSearchItem = listItem;
        this.itemListPath = itemListPath;
    }

    public void setFilteredList(List<Item> filteredList){
        this.listSearchItem = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchItemAdapter.SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new SearchItemAdapter.SearchItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.SearchItemViewHolder holder, int position) {

        Item items = listSearchItem.get(position);
        holder.nameSearch.setText(items.getName());
        fbs = FirebaseServices.getInstance();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAnother , new DetailsFragment(itemListPath.get(position))).addToBackStack(null).commit();
            }
        });

        StorageReference storageRef= fbs.getStorage().getReference().child(items.getImageUrl());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imageSearch);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSearchItem.size();
    }

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameSearch;
        ImageView imageSearch;

        public SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameSearch = itemView.findViewById(R.id.searchAdapterName);
            imageSearch = itemView.findViewById(R.id.searchAdapterImg);
        }
    }
}
