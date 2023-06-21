package com.example.humanaid;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private static final String TAG = "CalendarFragment";
    private ArrayList<dataUser> list = new ArrayList<>();
    private AdapterItem adapterItem;
    private RecyclerView recyclerView;
    private Context context;

    private String[] monthNames = new String[]{"Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάϊος", "Ιούνιος", "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        context = requireContext();
        recyclerView = rootView.findViewById(R.id.recyclerView);

        CalendarView calendarView = rootView.findViewById(R.id.calendarView);

        String currentDate = new SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(new Date());

        TextView datesValue = rootView.findViewById(R.id.datesTextView);
        datesValue.setText(currentDate);

        createData(currentDate);


        adapterItem = new AdapterItem(context, list);
        recyclerView.setAdapter(adapterItem);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String selectedMonth = monthNames[month];
                String selectedDates = dayOfMonth + " " + selectedMonth + " " + year + ":";
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                TextView datesValue = calendarView.findViewById(R.id.datesTextView);
                datesValue.setText(selectedDates);

                list.clear();

                createData(selectedDate);

                adapterItem.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void createData(String selectedDate) {

        list.clear();

        if (selectedDate.equals("2023-4-24")) {
            list.add(new dataUser("Παπαδόπουλος", "Μαρίνος", "17:00 - 18:00", "Χαλαρωτική Μαλάξη"));
            list.add(new dataUser("Παπαδοπούλου", "Μαρία", "18:00 - 19:00", "Χαλαρωτική Μάλαξη"));
            list.add(new dataUser("Αναστασιάδου", "Μαρία", "19:00 - 20:00", "Αθλητική Μάλαξη"));
        } else if (selectedDate.equals("2023-4-25")) {
            list.add(new dataUser("Παπαδόπουλος", "Νίκος", "17:00 - 18:00", "Χαλαρωτική Μαλάξη"));
            list.add(new dataUser("Παπαδοπούλου", "Ιωάννα", "18:00 - 19:00", "Αθλητική Μάλαξη"));
            list.add(new dataUser("Παπαδόπουλος", "Γιάννης", "19:00 - 20:00", "Χαλαρωτική Μαλάξη"));
        }
    }
}

