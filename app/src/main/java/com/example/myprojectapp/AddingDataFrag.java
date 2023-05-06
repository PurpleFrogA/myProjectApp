package com.example.myprojectapp;

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
 * Use the {@link AddingDataFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddingDataFrag extends Fragment {
    private EditText name, id, weight,size;
    private Spinner spin;
    private Button addingBtn;
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
        id = getView().findViewById(R.id.adddataFragID);
        weight = getView().findViewById(R.id.adddataFragW);
        spin = getView().findViewById(R.id.adddataSpinKind);
        size = getView().findViewById(R.id.adddataFragSize);
        addingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebase();
            }

            private void addToFirebase() {
                String nameStr, idStr, weightStr, spinStr,sizeStr;

                spinStr = spin.getSelectedItem().toString();
                nameStr = name.getText().toString();
                idStr = id.getText().toString();
                weightStr = weight.getText().toString();
                sizeStr = size.getText().toString();

                if(nameStr.trim().isEmpty() || spinStr.trim().isEmpty() || idStr.trim().isEmpty()||weightStr.trim().isEmpty()||sizeStr.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some data are incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
                Item item = new Item(nameStr,idStr,weightStr,spinStr);

                fbs.getFire().collection("users_")
                        .add(item)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.e(TAG, "Error adding document", e);
                            }
                        });

            }
        });
    }
}