package com.example.myprojectapp;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddingUsersFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddingUsersFrag extends Fragment {
    private static final int RESULT_LOAD_IMG = 24;
    private EditText email,phoneNum,address,name;
    private Spinner gender;
    private Button addUser,gotoHomeBtn;
    private ImageView addProfile;
    private FirebaseServices fbs;
    String loginemail = FirebaseAuth.getInstance().getCurrentUser().getEmail() ;

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
        name = getView().findViewById(R.id.adduserName);
        email = getView().findViewById(R.id.adduserEmail);
        phoneNum = getView().findViewById(R.id.adduserPhoneNum);
        gender = getView().findViewById(R.id.addusersGender);
        address =getView().findViewById(R.id.adduserAddress);
        gotoHomeBtn =getView().findViewById(R.id.addUserFragBack);
        addUser = getView().findViewById(R.id.addusersbtn);
        addProfile = getView().findViewById(R.id.addUserImage);

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        gotoHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameLayoutMain, new ItemListFragment());
                ft.commit();
            }
        });
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFirebaseUser();
                Toast.makeText(getActivity(), "Your information has been saved", Toast.LENGTH_SHORT).show();

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameLayoutMain, new ItemListFragment());
                ft.commit();
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
                addProfile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    private String UploadImageToFirebase(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) addProfile.getDrawable();
        Bitmap image = bitmapDrawable.getBitmap();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference ref =fbs.getStorage().getReference("listingPictures/" + UUID.randomUUID().toString());
        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            }
        });
        return ref.getPath();
    }
    private void addToFirebaseUser() {
        String emailStr, phoneNumStr, genderStr,addressStr,nameStr;
        nameStr = name.getText().toString();
        genderStr = gender.getSelectedItem().toString();
        emailStr = email.getText().toString();
        phoneNumStr = phoneNum.getText().toString();
        addressStr = address.getText().toString();

        if(emailStr.trim().isEmpty() || genderStr.trim().isEmpty() || phoneNumStr.trim().isEmpty()|| addressStr.trim().isEmpty()||nameStr.trim().isEmpty()){
            Toast.makeText(getActivity(), "Some data are incorrect", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(nameStr,emailStr,phoneNumStr,genderStr,addressStr,UploadImageToFirebase());

        fbs.getFire().collection("User").document(loginemail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}