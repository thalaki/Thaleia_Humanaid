package com.example.humanaid;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.humanaid.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivityDoctor extends AppCompatActivity {

    ActivityMainBinding binding;
    private FloatingActionButton myButton;
    private ImageButton imagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment( new HomeFragment());

        //setting searchButton and navigation
        myButton = findViewById(R.id.searchMain);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.history:
                    replaceFragment(new PatientFragment());
                    break;
                case R.id.calendar:
                    replaceFragment(new CalendarFragment());
                    break;
                case R.id.request:
                    replaceFragment(new AppointmentsFragment());
                    break;
                case R.id.add:
                    replaceFragment(new AddAppointmentFragment());
                    break;
            }
            return true;
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               replaceFragment(new SearchFragment());
            }
        });

        //button to add new patient
        imagebtn = findViewById(R.id.add_button);
        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AddPatientFragment());
            }
        });




    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentMAnager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentMAnager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}