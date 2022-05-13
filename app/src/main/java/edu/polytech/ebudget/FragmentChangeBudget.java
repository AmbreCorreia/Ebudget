package edu.polytech.ebudget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;
import edu.polytech.ebudget.databinding.ChangebudgetBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;
import edu.polytech.ebudget.fragmentsFooter.FragmentHome;

public class FragmentChangeBudget extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "category";
    private static final String ARG_PARAM2 = "fragment";

    private ChangebudgetBinding binding;
    private Category cat;
    private String fragment;

    public FragmentChangeBudget() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cat = getArguments().getParcelable(ARG_PARAM1);
            fragment = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ChangebudgetBinding.inflate(inflater, container, false);

        binding.button2.setOnClickListener(click -> {
            int budget = Integer.parseInt(binding.inputChangeBudget.getText().toString().trim());

            Map<String, Object> data = new HashMap<>();
            data.put("budget", budget);

            FirebaseFirestore.getInstance().collection(FirebasePaths.categories).document(cat.id)
                    .set(data, SetOptions.merge());

            Fragment frag;
            switch (fragment){
                case "Home":
                    frag = new FragmentHome();
                    break;
                default:
                    frag = new FragmentCategory();
                    break;
            }
            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }
}