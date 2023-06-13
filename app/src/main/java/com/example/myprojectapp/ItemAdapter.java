package com.example.myprojectapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    Context context;

    ArrayList<Item> listItem;
    // Method to retrieve the image URL from your data source
    /*private String getImageUrl(int position) {
        // Assuming MyData class has a method called getImageUrl() to retrieve the image URL
        return listItem.get(position).getImageUrl();
    }*/
    FirebaseServices fbs;

    public ItemAdapter(Context context, ArrayList<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Item items = listItem.get(position);
        holder.name.setText(items.getName());
        holder.weight.setText(items.getWeight());
        holder.size.setText(items.getSize());
        holder.kind.setText(items.getKind());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*// Get the image URL from your data source (e.g., list, array)
        String imageUrl = listItem.get(position).getImageUrl();

        // Pass the image URL and ImageView to the method for loading and displaying the image
        loadImageFromFirestore(imageUrl, holder.image);
*/

    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name,weight,size,kind;
        ImageView image;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvNameItem);
            weight= itemView.findViewById(R.id.tvWeightItem);
            size = itemView.findViewById(R.id.tvSizeItem);
            kind = itemView.findViewById(R.id.tvKindItem);
            image = itemView.findViewById(R.id.ivPhotoItem);
        }
    }
    /*private void loadImageFromFirestore(String imageUrl, ImageView imageView) {
        // Use an image loading library (e.g., Glide) to load and display the image
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }*/

}
