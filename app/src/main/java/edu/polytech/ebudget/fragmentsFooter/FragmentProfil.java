package edu.polytech.ebudget.fragmentsFooter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import edu.polytech.ebudget.FragmentChooseCalendar;
import edu.polytech.ebudget.Login;
import edu.polytech.ebudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfil extends Fragment {
    private Button logout;
    private Button calendar;

    public FragmentProfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profil, container, false);

        calendar = (Button) rootView.findViewById(R.id.changeCalendarButton);
        calendar.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putString("param1", "0");
            FragmentChooseCalendar frag = new FragmentChooseCalendar();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        logout= (Button) rootView.findViewById(R.id.logout);
        logout.setOnClickListener(click -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), Login.class));
        });

        return rootView;
    }
}