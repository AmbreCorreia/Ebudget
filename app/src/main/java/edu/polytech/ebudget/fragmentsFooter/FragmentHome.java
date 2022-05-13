package edu.polytech.ebudget.fragmentsFooter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import edu.polytech.ebudget.FragmentChangeBudget;
import edu.polytech.ebudget.FragmentListExpenses;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.databinding.ActivityHomeBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private ActivityHomeBinding binding;
    private Category cat;

    public FragmentHome() {
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
        binding = ActivityHomeBinding.inflate(inflater, container, false);

        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("name", "default")
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()){
                        cat = document.toObject(Category.class);
                        binding.textViewProgress.setText(String.valueOf(cat.budget));
                        int progress = (int) ((float)(cat.expense*100)/ (float)cat.budget);
                        binding.progressBar.setProgress(progress);
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