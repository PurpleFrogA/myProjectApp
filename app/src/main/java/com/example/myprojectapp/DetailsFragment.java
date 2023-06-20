package com.example.myprojectapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private String path;
    private FirebaseServices fbs;
    private TextView nameDetails,weightDetails,sizeDetails,kindDetails;
    private ImageView imageDetails;
    Item item;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(String path){
        this.path = path;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        hi();
    }

    private void hi() {
        nameDetails = getView().findViewById(R.id.detailsFragName);
        weightDetails = getView().findViewById(R.id.detailsFragWeight);
        sizeDetails = getView().findViewById(R.id.detailsFragSize);
        kindDetails = getView().findViewById(R.id.detailsFragKind);
        imageDetails = getView().findViewById(R.id.detailsFragImage);

        fbs = FirebaseServices.getInstance();


        evenToChange();
    }


    private void evenToChange() {
        path = item.getImageUrl();
        DocumentReference documentReference = fbs.getFire().collection("Item").document(path);
        documentReference.get()
                .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                    if(documentSnapshot.exists()){
                        item = documentSnapshot.toObject(Item.class);
                        hearme();
                    } else {
                        System.out.println("Documents doesn't exist");
                    }
                }).addOnFailureListener(e ->{
                    System.out.println("Error");
                });
    }

    private void hearme() {
        nameDetails.setText(item.getName());
        weightDetails.setText(item.getWeight());
        sizeDetails.setText(item.getSize());
        kindDetails.setText(item.getKind());

        StorageReference storageReference = fbs.getStorage().getReference().child(item.getImageUrl());

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri)
                        .into(imageDetails);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}