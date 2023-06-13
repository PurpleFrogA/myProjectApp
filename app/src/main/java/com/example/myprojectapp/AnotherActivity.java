package com.example.myprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.myprojectapp.databinding.ActivityAnotherBinding;

public class AnotherActivity extends AppCompatActivity {

    ActivityAnotherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnotherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gotoItemListFragment();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.information:
                    replaceFragment(new AboutUsFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.home:
                    replaceFragment(new ItemListFragment());
                    break;
                case R.id.add:
                    replaceFragment(new AddingDataFrag());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });
    }

    private void gotoItemListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutAnother, new ItemListFragment());
        ft.commit();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayoutAnother , fragment);
        fragmentTransaction.commit();
    }
}