package com.example.humanaid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PatientFragment extends Fragment {

    ImageView imageView;
    TextView textView, textName, textAmka, textStreet;
    ListView listView;

    private CustomAdapter customAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patientview, container, false);

        textView = view.findViewById(R.id.NoPatient);
        textName = view.findViewById(R.id.TextViewName);
        textAmka = view.findViewById(R.id.TextViewAmka);
        textStreet = view.findViewById(R.id.TextViewStreet);
        imageView = view.findViewById(R.id.imageView2);
        listView = view.findViewById(R.id.Appointments);


        Bundle args = getArguments();
        if (args != null) {
            textView.setVisibility(View.GONE);

            // receiving the data from previous fragment
            String name = args.getString("name");
            String amka = args.getString("amka");
            String street = args.getString("street");
            int imageResId = args.getInt("imageResId");
            ArrayList<String>  appointmentsList = args.getStringArrayList("appointmentsList");


            // view data on screen
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, appointmentsList);
            listView.setAdapter(adapter);
            textName.setText( name);
            textAmka.setText("ΑΜΚΑ: " + amka);
            textStreet.setText("Διεύθυνση: " + street);
            imageView.setImageResource(imageResId);
            //view data on list
            customAdapter = new CustomAdapter( getActivity(), appointmentsList);
            listView.setAdapter(customAdapter);


        }else{
            textView.setText("Δεν έχετε επιλέξει ασθενή.");
            textName.setVisibility(View.GONE);
            textAmka.setVisibility(View.GONE);
            textStreet.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
        return view;
    }

    public  class CustomAdapter   extends ArrayAdapter<String>   {
        private ArrayList<String> appointments;
        private Context context;

        public CustomAdapter(Context context, ArrayList<String> appointments) {
            super(context, 0, appointments);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // Inflate or reuse the item layout view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_appointments, parent, false);
            }

            // Get the data item for the current position
            String item = getItem(position);

            // Bind the data to the views within the item layout
            TextView textView = convertView.findViewById(R.id.appointment);
            textView.setText(item);

            return convertView;
        }




    }
}