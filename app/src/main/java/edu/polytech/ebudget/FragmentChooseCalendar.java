package edu.polytech.ebudget;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import edu.polytech.ebudget.databinding.ChoosecalendarBinding;
import edu.polytech.ebudget.fragmentsFooter.FragmentCourses;
import edu.polytech.ebudget.fragmentsFooter.FragmentNotif;
import edu.polytech.ebudget.utils.CalendarHelper;

public class FragmentChooseCalendar extends Fragment implements AdapterView.OnItemSelectedListener {


        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String msg;
        private String mParam2;
        private ChoosecalendarBinding bind;
        private HashMap calendars = new HashMap();

        public FragmentChooseCalendar() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FourthFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static FragmentChooseCalendar newInstance(String param1, String param2) {
            FragmentChooseCalendar fragment = new FragmentChooseCalendar();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                msg = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            bind = ChoosecalendarBinding.inflate(inflater, container, false);


            final String[] EVENT_PROJECTION = new String[]{
                    CalendarContract.Calendars._ID,
                    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
            };

            final ContentResolver cr = getActivity().getContentResolver();
            final Uri uri = CalendarContract.Calendars.CONTENT_URI;
            Cursor cur = cr.query(uri, EVENT_PROJECTION, null, null, null);

            while (cur.moveToNext()){
                calendars.put(cur.getString(1), cur.getLong(0));
            }

            ArrayAdapter array = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, calendars.keySet().toArray(new String[calendars.keySet().size()]));
            array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bind.calendarspinner.setAdapter(array);

            bind.calendarspinner.setOnItemSelectedListener(this);

            bind.OK.setOnClickListener(click ->{
                CalendarHelper.addCalendarEvent(getActivity(), getContext(), msg);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new FragmentNotif());
                fragmentTransaction.commit();
            });

            return bind.getRoot();
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Long calID = (Long) calendars.get(adapterView.getItemAtPosition(i).toString());

            Map<String, Object> data = new HashMap<>();
            data.put("calendarID", calID);

            FirebaseFirestore.getInstance().collection("preferences").document(FirebaseAuth.getInstance().getUid())
                    .set(data, SetOptions.merge());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Log.e("Firebase", "No preferences");
        }
    }

