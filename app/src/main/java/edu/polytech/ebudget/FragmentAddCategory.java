package edu.polytech.ebudget;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.polytech.ebudget.databinding.FragmentAddcategoryBinding;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link edu.polytech.ebudget.fragmentsFooter.FragmentProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AddCategory";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentAddcategoryBinding binding;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    public FragmentAddCategory() {
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
    public static FragmentAddCategory newInstance(String param1, String param2) {
        FragmentAddCategory fragment = new FragmentAddCategory();
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
        binding = FragmentAddcategoryBinding.inflate(inflater, container, false);

        binding.addButton.setOnClickListener(click -> {
            String name = binding.nameInput.getText().toString().trim();
            int budget = Integer.parseInt(binding.budgetInput.getText().toString().trim());
            String user = FirebaseAuth.getInstance().getUid();
            add(name, budget, user);
        });

        return binding.getRoot();
    }

    private void add(String name, int budget, String user) {
        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        category.put("budget", budget);
        category.put("user", user);

        database.collection("categories")
                .add(category)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new FragmentCategory());
        fragmentTransaction.commit();
    }
}