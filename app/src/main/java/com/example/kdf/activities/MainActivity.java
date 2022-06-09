package com.example.kdf.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.kdf.R;
import com.example.kdf.fragments.CartFragment;
import com.example.kdf.fragments.HomeFragment;
import com.example.kdf.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                return true;

            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CartFragment()).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                return true;
        }
        return false;
    }
}