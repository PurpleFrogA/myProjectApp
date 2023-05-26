package com.example.myprojectapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddingUsersFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddingUsersFrag extends Fragment {
    private EditText email,phoneNum;
    private Spinner gender;
    private Button addUser;
    private FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddingUsersFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddingUsersFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AddingUsersFrag newInstance(String param1, String param2) {
        AddingUsersFrag fragment = new AddingUsersFrag();
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
        return inflater.inflate(R.layout.fragment_adding_users, container, false);
    }
    public void onStart() {
        super.onStart();
        first();
    }

    private void first() {
        fbs = FirebaseServices.getInstance();
        email = getView().findViewById(R.id.adduserEmail);
        phoneNum = getView().findViewById(R.id.adduserPhoneNum);
        gender = getView().findViewById(R.id.addusersGender);
        addUser = getView().findViewById(R.id.addusersbtn);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebaseUser();
            }

        });
    }

    private void addToFirebaseUser() {
        String emailStr, phoneNumStr, genderStr;
        genderStr = gender.getSelectedItem().toString();
        emailStr = email.getText().toString();
        phoneNumStr = phoneNum.getText().toString();

        if(emailStr.trim().isEmpty() || genderStr.trim().isEmpty() || phoneNumStr.trim().isEmpty()){
            Toast.makeText(getActivity(), "Some data are incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(emailStr,phoneNumStr,genderStr);

        fbs.getFire().collection("User")
                .add(user)
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