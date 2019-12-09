package com.jamie.devonrocksandstones.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.fragments.AddStoneFragment;
import com.jamie.devonrocksandstones.fragments.HiddenStonesFragment;
import com.jamie.devonrocksandstones.fragments.HomeFragment;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;


public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If user is logged in, display the homepage fragment, function called if made past onStart.
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);

        displayFragment(new HomeFragment());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayout, fragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Checks to see if user logged in, if not, display sign-up page.
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_add_stone:
                fragment = new AddStoneFragment();
                break;
            case R.id.menu_hidden_stones:
                fragment = new HiddenStonesFragment();
                break;
            case R.id.menu_logout:
                logout();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }

        return false;
    }

    private void logout() {
        //Logout clears user data from device storage and returns to login page.
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
