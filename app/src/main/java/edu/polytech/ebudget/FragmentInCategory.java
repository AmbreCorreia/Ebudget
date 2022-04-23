package edu.polytech.ebudget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import edu.polytech.ebudget.camera.CameraActivity;
import edu.polytech.ebudget.databinding.FragmentAddcategoryBinding;
import edu.polytech.ebudget.databinding.FragmentInCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

public class FragmentInCategory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String category;
    private FragmentInCategoryBinding binding;

    public FragmentInCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FourthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInCategory newInstance(String param1, String param2) {
        FragmentInCategory fragment = new FragmentInCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInCategoryBinding.inflate(inflater, container, false);

        binding.textView3.setText(category);

        binding.additemcat.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putString("param1", category);
            FragmentAddItemCategory frag = new FragmentAddItemCategory();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        binding.buttonImage.setOnClickListener(click ->{
            Intent intentCamera = new Intent(getActivity(), CameraActivity.class);
            startActivity(intentCamera);
        });

        return binding.getRoot();
    }

}
