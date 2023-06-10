package com.example.myprojectapp;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firestore.v1.Cursor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddingDataFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddingDataFrag extends Fragment {
    private static final int RESULT_LOAD_IMG = 23;
    private EditText name, weight;
    private Spinner spinKind,spinSize;
    private Button addingBtn,backToHomeBtn;
    private ImageView addPhoto;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddingDataFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddingDataFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AddingDataFrag newInstance(String param1, String param2) {
        AddingDataFrag fragment = new AddingDataFrag();
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
        return inflater.inflate(R.layout.fragment_adding_data, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        fbs = FirebaseServices.getInstance();
        name = getView().findViewById(R.id.adddataFragName);
        weight = getView().findViewById(R.id.adddataFragW);
        spinKind = getView().findViewById(R.id.adddataSpinKind);
        spinSize = getView().findViewById(R.id.adddataSpinSize);
        backToHomeBtn=getView().findViewById(R.id.adddataFragBack);
        addingBtn=getView().findViewById(R.id.adddataBtn);
        addPhoto = getView().findViewById(R.id.adddataImage);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }

            private void getImageFromGallery() {

            }
        });

        backToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameLayoutMain, new ItemListFragment());
                ft.commit();
            }
        });
        addingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebaseData();
                Toast.makeText(getActivity(), "The data has been added", Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                addPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void addToFirebaseData() {
        String nameStr, weightStr, spinStr,sizeStr;

        spinStr = spinKind.getSelectedItem().toString();
        nameStr = name.getText().toString();
        weightStr = weight.getText().toString();
        sizeStr = spinSize.getSelectedItem().toString();

        if(nameStr.trim().isEmpty() || spinStr.trim().isEmpty() || weightStr.trim().isEmpty()||sizeStr.trim().isEmpty()){
            Toast.makeText(getActivity(), "Some data are incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        Item item = new Item(nameStr,weightStr,spinStr,sizeStr);

        fbs.getFire().collection("Item")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding document", e);
                    }
                });

    }
}