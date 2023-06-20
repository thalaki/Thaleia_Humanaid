package com.example.humanaid;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.humanaid.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private boolean isLoggedIn = false;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (isLoggedIn) {
            replaceFragment(new HomeFragment());
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            replaceFragment(new LoginFragment());
            binding.bottomNavigationView.setVisibility(View.GONE);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.service:
                    replaceFragment(new ServicesFragment());
                    break;
                case R.id.physio:
                    replaceFragment(new PhysioFragment());
                    break;
            }
            return true;
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottomNavigationView();
                replaceFragment(new LoginFragment());
            }
        });
    }

    public void hideBottomNavigationView() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Rest of the code...



    // Method to be called when login is successful
    public void onLoginSuccess() {
        isLoggedIn = true;
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setVisibility(View.VISIBLE);
    }
}
