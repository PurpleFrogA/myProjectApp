package com.example.myprojectapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    private String path;
    private TextView nameDetails,weightDetails,sizeDetails,kindDetails;
    private TextView lenderName,lenderPhoneNum,lenderEmail,lenderAddress;
    private ImageView imageDetails;
    Item item;
    User user;
    FirebaseFirestore ff;
    FirebaseServices fbs;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String loginemail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


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
        connectComponents();
    }

    private void connectComponents() {
        nameDetails = getView().findViewById(R.id.detailsFragName);
        weightDetails = getView().findViewById(R.id.detailsFragWeight);
        sizeDetails = getView().findViewById(R.id.detailsFragSize);
        kindDetails = getView().findViewById(R.id.detailsFragKind);
        imageDetails = getView().findViewById(R.id.detailsFragImage);

        lenderName = getView().findViewById(R.id.detailsLenName);
        lenderPhoneNum = getView().findViewById(R.id.detailsLenPhoneNum);
        lenderEmail = getView().findViewById(R.id.detailsLenEmail);
        lenderAddress = getView().findViewById(R.id.detailsLenAddress);

        fbs = FirebaseServices.getInstance();
        ff = FirebaseFirestore.getInstance();


        evenToChangeItem();
    }

    private void evenToChangeUser() {
        String id = item.getUser();
            ff.collection("User").document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            displayItemData(user);
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error retrieving document: " + e.getMessage());
                    }
                });

    }
    private void displayItemData(User user) {
        // Access the UI elements in your fragment and update them with the item's data
        lenderName.setText("Name: " + user.getName());
        lenderEmail.setText("Email: " + user.getEmail());
        lenderPhoneNum.setText("Phone number: " + user.getPhoneNum());
        lenderAddress.setText("Address: " + user.getAddress());
    }


    private void evenToChangeItem() {

        DocumentReference documentReference = fbs.getFire().collection("Item").document(path);
        documentReference.get()
                .addOnSuccessListener((DocumentSnapshot documentSnapshot) -> {
                    if(documentSnapshot.exists()){
                        item = documentSnapshot.toObject(Item.class);
                        hearmeItem();
                        evenToChangeUser();
                    } else {
                        System.out.println("Documents doesn't exist");
                    }
                }).addOnFailureListener(e ->{
                    System.out.println("Error");
                });
    }

    private void hearmeItem() {
        nameDetails.setText("Name: " + item.getName());
        weightDetails.setText("Weight: " + item.getWeight());
        sizeDetails.setText("Size: " + item.getSize());
        kindDetails.setText("Kind: " + item.getKind());

        StorageReference storageReference = fbs.getStorage().getReference().child(item.getImageUrl());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity())
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