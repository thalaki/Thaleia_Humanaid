package com.example.humanaid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesFragment extends Fragment {

    private Spinner categorySpinner;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        categorySpinner = view.findViewById(R.id.category_spinner);

        // Create a reference to the "categories" node in the database
        DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference().child("categories");

        // Create an empty list to hold category names
        List<String> categoryNames = new ArrayList<>();

        // Create the adapter with the category names list
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryNames);
        categorySpinner.setAdapter(adapter);

        // Set up the value event listener
        categoriesRef.child("physioName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the previous data in the list
                categoryNames.clear();

                // Add the new category name to the list
                String physioName = dataSnapshot.getValue(String.class);
                if (physioName != null) {
                    categoryNames.add(physioName);
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case if data retrieval is unsuccessful
                Log.e("Firebase", "Error retrieving category name: " + databaseError.getMessage());
            }
        });

        return view;
    }
}
