package edu.polytech.ebudget.fragmentsFooter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import edu.polytech.ebudget.FragmentChangeBudget;
import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.FragmentListExpenses;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.databinding.ActivityHomeBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityHomeBinding binding;
    private Category cat;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ActivityHomeBinding.inflate(inflater, container, false);

        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("name", "default")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()){
                            cat = document.toObject(Category.class);
                            binding.textViewProgress.setText(String.valueOf(cat.budget));
                            int progress = (int) ((float)(cat.expense*100)/ (float)cat.budget);
                            binding.progressBar.setProgress(progress);
                        }
                    }
                });

        binding.expenselistbutton.setOnClickListener(click -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentListExpenses());
            fragmentTransaction.commit();
        });

        binding.budgetButton.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", cat);
            bundle.putString("fragment", "Home");
            FragmentChangeBudget frag = new FragmentChangeBudget();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }
}