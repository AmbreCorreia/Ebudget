package edu.polytech.ebudget;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.polytech.ebudget.camera.CameraActivity;
import edu.polytech.ebudget.databinding.FragmentInCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

public class FragmentInCategory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "category";

    // TODO: Rename and change types of parameters
    private Category category;
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
            category = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInCategoryBinding.inflate(inflater, container, false);

        binding.textView3.setText(category.name);

        binding.additemcat.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", category);
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

        binding.deletecat.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setMessage("La supression de : " + category.name + ", sera définitive. Veuillez confirmer")
                    .setNeutralButton("Annuler", null)
                    .setNeutralButton("Supprimer", (dialogInterface, i) -> {
                        System.out.println("click sur supprimer");

            FirebaseFirestore.getInstance().collection(FirebasePaths.categories).
                    document(category.id)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentCategory());
            fragmentTransaction.commit();
            });
            builder.show();
        });
        return binding.getRoot();
    }

}
