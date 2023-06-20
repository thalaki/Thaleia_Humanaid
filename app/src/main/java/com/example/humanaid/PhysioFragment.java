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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.humanaid.PhysioInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhysioFragment extends Fragment {

  private EditText physioNameEdt, addressEdit, afmEdt;
  private Button sendDatabtn, logoutButton;

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
    boolean isNameValid = isValidName(name);
    boolean isAddressValid = isValidAddress(address);
    boolean isAfmValid = isValidAfm(afm);

    if (!isNameValid) {
      Toast.makeText(getActivity(), "Λάθος στοιχεία, παρακαλώ καταχωρήστε σωστά το όνομα.", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!isAddressValid) {
      Toast.makeText(getActivity(), "Λάθος διευθυνσή. παρακαλώ καταχωρήστε διευθυνσή και αριθμό.", Toast.LENGTH_SHORT).show();
      return;
    }

    if (!isAfmValid) {
      Toast.makeText(getActivity(), "Λάθος ΑΦΜ, παρακαλώ πληκτρολογήστε το σωστό.", Toast.LENGTH_SHORT).show();
      return;
    }

    // All input values are valid, proceed with adding data to Firebase
    PhysioInfo physio = new PhysioInfo();
    physio.setName(name);
    physio.setAddress(address);
    physio.setAfm(afm);

    DatabaseReference physioRef = databaseReference.child("Physios").push();

    physioRef.setValue(physio)
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
  }

  private boolean isValidName(String name) {
    // Validate that the name contains only letters (no digits or special characters)
    String pattern = "^[a-zA-Z]+$";
    return name.matches(pattern);
  }

  private boolean isValidAddress(String address) {
    // Validate that the address contains only letters, numbers, and spaces (no special characters)
    String pattern = "^[a-zA-Z0-9 ]+$";
    return address.matches(pattern);
  }

  private boolean isValidAfm(String afm) {
    // Validate that the AFM contains only numbers (no letters or special characters)
    String pattern = "^[0-9]+$";
    return afm.matches(pattern);
  }
}
