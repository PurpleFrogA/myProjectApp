package com.example.myprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoWelocme();
        //gotoLoginFragment();
        //gotoitemList();
        //gotoAddUser();
        //gotoAddFrag();
        //gotoSearchFrag();
    }

    private void gotoWelocme() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new WelcomingFragment());
        ft.commit();
    }

    private void gotoSearchFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new SearchFragment());
        ft.commit();
    }

    private void gotoAddUser() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AddingUsersFrag());
        ft.commit();
    }

    private void gotoitemList() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ItemListFragment());
        ft.commit();
    }

    private void gotoAddFrag() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AddingDataFrag());
        ft.commit();
    }

    private void gotoLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LoginFragment());
        ft.commit();
    }
}