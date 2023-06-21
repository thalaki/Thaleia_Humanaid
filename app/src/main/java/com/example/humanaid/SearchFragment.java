package com.example.humanaid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
        private SearchView searchView;
        private ListView listView;
        private ArrayList<Patient> arrayList;
        private CustomAdapter customAdapter;

        private int images[] = {R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4};
      //  private int icons[] = {R.drawable.bell, R.drawable.check, R.drawable.check};

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_search, container, false);

            // creating some appointments for testing
            ArrayList<String> appointmentList = new ArrayList<>();
            appointmentList.add("ΔΕΥ 03/04 Χαλαρωτική Μάλαξη");
            appointmentList.add("ΔΕΥ 20/03 Χαλαρωτική Μάλαξη");
            appointmentList.add("ΠΑΡ 17/03 Αθλητική Μάλαξη");
            appointmentList.add("ΠΕΜ 9/03 Χαλαρωτική Μάλαξη");
            appointmentList.add("ΤΕΤ 01/03 Θεραπευτική Μάλαξη");
            appointmentList.add("ΠΑΡ 17/03 Αθλητική Μάλαξη");
            appointmentList.add("ΠΕΜ 9/03 Χαλαρωτική Μάλαξη");
            appointmentList.add("ΤΕΤ 01/03 Θεραπευτική Μάλαξη");

            // creating some patients for testing
            Patient patient1 = new Patient("Άννα Παπαδοπούλου", "123456123", "Εγνατία 12", 1, appointmentList);
            Patient patient2 = new Patient("Χριστίνα Αλεξ", "789789123", "Ερμού 34",2, appointmentList);
            Patient patient3 = new Patient("Γεώργιος Νίκου", "112233123", "Οδός 87",3, appointmentList);
            Patient patient4 = new Patient("Σακης Σιδέρης", "135791123", "Λαλαλαντ 99",4,appointmentList);

            String names[] = {patient1.getName(), patient2.getName(), patient3.getName(), patient4.getName()};
            String amka[] = {patient1.getAmka(), patient2.getAmka(), patient3.getAmka(), patient4.getAmka()};
            String street[] = {patient1.getStreet(), patient2.getStreet(), patient3.getStreet(), patient4.getStreet()};

            arrayList = new ArrayList<>();
            for (int i = 0; i < names.length; i++) {
                Patient patient = new Patient(names[i], amka[i], street[i], images[i], appointmentList);
                arrayList.add(patient);
            }


            listView = view.findViewById(R.id.listViewT);
            searchView = view.findViewById(R.id.searchViewT);
            customAdapter = new CustomAdapter(arrayList, getActivity());
            listView.setAdapter(customAdapter);

            //code for searchView: read the text and filter options
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    customAdapter.getFilter().filter(newText);
                    return true;
                }
            });
            return view;
        }


        public  class CustomAdapter extends BaseAdapter implements Filterable {
            private List<Patient> itemsModelList;
            private List<Patient> itemsModelListFiltered;
            private Context context;

            public CustomAdapter(List<Patient> itemsModelList, Context context) {
                this.itemsModelList = itemsModelList;
                this.itemsModelListFiltered = itemsModelList;
                this.context = context;
            }

            @Override
            public int getCount() {
                return itemsModelListFiltered.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            //return the position of the patient in the list
            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.list_patients, null);

                ImageView imageView = view.findViewById(R.id.imageView);
                TextView itemName = view.findViewById(R.id.patientsName);
                TextView itemAmka = view.findViewById(R.id.patientsAmka);
                TextView itemStreet = view.findViewById(R.id.patientsStreet);
                RelativeLayout itemView = view.findViewById(R.id.itemView);


                //set the view for the SearchList with the patiens
                imageView.setImageResource(itemsModelListFiltered.get(position).getImage());
                itemName.setText(itemsModelListFiltered.get(position).getName());
                itemAmka.setText(itemsModelListFiltered.get(position).getAmka());
                itemStreet.setText(itemsModelListFiltered.get(position).getStreet());

//                //backgroung color
//                itemView.setBackgroundResource(R.drawable.list_item_selector);
//
//                // Attach a TouchListener to change the background color when touched
//                itemView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()) {
//                            case MotionEvent.ACTION_DOWN:
//                                // Set the background color to gray when touched
//                                itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));
//                                break;
//                            case MotionEvent.ACTION_UP:
//                            case MotionEvent.ACTION_CANCEL:
//                                // Set the background color back to the default
//                                itemView.setBackgroundResource(R.drawable.list_item_selector);
//                                break;
//                        }
//                        return false;
//                    }
//                });

                // clicking on a specific patient opens another fragment with the data of the patient
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PatientFragment fragment = new PatientFragment();

                        //Sending the needed data
                        Bundle args = new Bundle();
                        args.putString("name", itemsModelListFiltered.get(position).getName());
                        args.putString("amka", itemsModelListFiltered.get(position).getAmka());
                        args.putString("street", itemsModelListFiltered.get(position).getStreet());
                        args.putStringArrayList("appointmentsList",itemsModelListFiltered.get(position).getList());
                        args.putInt("imageResId", itemsModelListFiltered.get(position).getImage());
                        fragment.setArguments(args);

                        //opening the new fragment
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

                return view;
            }

            @Override
            public Filter getFilter() {
                Filter filter = new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {

                        FilterResults filterResults = new FilterResults();
                        if (constraint == null || constraint.length() == 0){
                            filterResults.count = itemsModelList.size();
                            filterResults.values = itemsModelList;
                        }else{
                            String searchStr = constraint.toString().toLowerCase();
                            List<Patient> resultData = new ArrayList<>();

                            for(Patient p: itemsModelList){
                                if(p.getName().contains(searchStr) || p.getAmka().contains(searchStr)){
                                    resultData.add(p);
                                }
                                filterResults.count = resultData.size();
                                filterResults.values = resultData;
                            }
                        }
                        return filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        itemsModelListFiltered = (List<Patient>) results.values;
                        notifyDataSetChanged();
                    }
                };
                return filter;
            }
        }
}