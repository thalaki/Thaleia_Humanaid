package com.example.humanaid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.humanaid.PhysioInfo;
import com.example.humanaid.R;
import com.example.humanaid.ServiceInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private DatabaseReference physiosRef;
    private EditText serviceIdEditText;
    private EditText serviceNameEditText;
    private EditText serviceDescriptionEditText;
    private EditText serviceCostEditText;
    private Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        categorySpinner = view.findViewById(R.id.category_spinner);
        serviceIdEditText = view.findViewById(R.id.service_id);
        serviceNameEditText = view.findViewById(R.id.service_name);
        serviceDescriptionEditText = view.findViewById(R.id.service_description);
        serviceCostEditText = view.findViewById(R.id.service_cost);
        addButton = view.findViewById(R.id.mAinButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categorySpinner.getSelectedItem().toString();
                String serviceId = serviceIdEditText.getText().toString();
                String serviceName = serviceNameEditText.getText().toString();
                String serviceDescription = serviceDescriptionEditText.getText().toString();
                String serviceCost = serviceCostEditText.getText().toString();

                // Validate the input data (you can add your validation logic here)

                // Create a ServiceInfo object to store the data
                ServiceInfo serviceInfo = new ServiceInfo(category, serviceId, serviceName, serviceDescription, serviceCost);

                // Save the data to Firebase
                DatabaseReference servicesRef = FirebaseDatabase.getInstance().getReference().child("services");
                servicesRef.push().setValue(serviceInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();

                                SuccessFragment successFragment = new SuccessFragment();

                                requireActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout, successFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to add data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                // Clear the EditText fields
                serviceIdEditText.setText("");
                serviceNameEditText.setText("");
                serviceDescriptionEditText.setText("");
                serviceCostEditText.setText("");

            }
        });
        Button cancelButton = view.findViewById(R.id.SecButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the EditText fields
                serviceIdEditText.setText("");
                serviceNameEditText.setText("");
                serviceDescriptionEditText.setText("");
                serviceCostEditText.setText("");
                categorySpinner.setSelection(0);
            }
        });

        // Create an empty list to hold category names
        List<String> categoryNames = new ArrayList<>();

        // Add the default item
        categoryNames.add(getString(R.string.spinner_prompt));

        // Create the adapter with the category names list
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categoryNames);
        categorySpinner.setAdapter(adapter);

        // Create a reference to the "Physios" node in the database
        physiosRef = FirebaseDatabase.getInstance().getReference().child("PhysioInfo").child("Physios");

        // Set up the value event listener
        physiosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the previous data in the list
                categoryNames.clear();

                // Add the default item
                categoryNames.add(getString(R.string.spinner_prompt));

                // Iterate through the dataSnapshot to retrieve physio names
                for (DataSnapshot physioSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve the PhysioInfo object
                    PhysioInfo physioInfo = physioSnapshot.getValue(PhysioInfo.class);
                    if (physioInfo != null) {
                        // Get the physio name and add it to the list
                        String physioName = physioInfo.getPhysioName();
                        categoryNames.add(physioName);
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error case if data retrieval is unsuccessful
                Log.e("Firebase", "Error retrieving physio names: " + databaseError.getMessage());
            }
        });

        return view;
    }
}
