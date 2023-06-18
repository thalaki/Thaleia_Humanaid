package com.example.humanaid;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.humanaid.PhysioInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhysioFragment extends Fragment {

  private EditText physioNameEdt, addressEdit, afmEdt;
  private Button sendDatabtn;

  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;

  PhysioInfo physioInfo;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_physio, container, false);

    physioNameEdt = view.findViewById(R.id.name_Physio);
    addressEdit = view.findViewById(R.id.adress_Physio);
    afmEdt = view.findViewById(R.id.afm_Physio);

    firebaseDatabase = FirebaseDatabase.getInstance();
    databaseReference = firebaseDatabase.getReference("EmployeeInfo");

    physioInfo = new PhysioInfo();

    sendDatabtn = view.findViewById(R.id.mAinButton);

    sendDatabtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name = physioNameEdt.getText().toString();
        String address = addressEdit.getText().toString();
        String afm = afmEdt.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(afm)) {
          Toast.makeText(getActivity(), "Παρακαλώ εισάγετε δεδομένα", Toast.LENGTH_SHORT).show();
        } else {
          addDataToFirebase(name, address, afm);
        }
      }
    });

    return view;
  }

  private void addDataToFirebase(String name, String address, String afm) {
    // Create a new PhysioInfo object and set its properties
    PhysioInfo physio = new PhysioInfo();
    physio.setName(name);
    physio.setAddress(address);
    physio.setAfm(afm);

    // Get a new child reference under "EmployeeInfo" to push the data
    DatabaseReference physioRef = databaseReference.child("Physios").push();

    // Set the value of the new child reference with the PhysioInfo object
    physioRef.setValue(physio)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_SHORT).show();
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to add data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            });
  }
}
