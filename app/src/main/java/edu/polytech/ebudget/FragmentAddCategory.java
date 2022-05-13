package edu.polytech.ebudget;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import edu.polytech.ebudget.databinding.FragmentAddcategoryBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

public class FragmentAddCategory extends Fragment {
    private FragmentAddcategoryBinding binding;

    public FragmentAddCategory() {
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
        binding = FragmentAddcategoryBinding.inflate(inflater, container, false);

        binding.addButton.setOnClickListener(click -> {
            String name = binding.nameInput.getText().toString().trim();
            int budget = Integer.parseInt(binding.budgetInput.getText().toString().trim());
            String user = FirebaseAuth.getInstance().getUid();

            new Category(name, budget, user).addToDatabase();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentCategory());
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }
}