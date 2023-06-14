package com.example.myprojectapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
    FirebaseServices fbs;
    ArrayList<String> itemListpath;


    public ItemAdapter(Context context, ArrayList<Item> listItem, ArrayList<String> itemListpath) {
        this.context = context;
        this.listItem = listItem;
        this.itemListpath = itemListpath;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {

        Item items = listItem.get(position);
        holder.name.setText(items.getName());
        //holder.weight.setText(items.getWeight());
        //holder.size.setText(items.getSize());
        holder.kind.setText(items.getKind());
        fbs = FirebaseServices.getInstance();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                DetailsFragment fragment = new DetailsFragment();
                fragmentTransaction.replace(R.id.ItemListFrameLayout, fragment);
                fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                fragmentTransaction.commit();
            }
        });

        /*StorageReference storageRef= fbs.getStorage().getReference().child(items.getImageUrl());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.image);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });*/
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
}
