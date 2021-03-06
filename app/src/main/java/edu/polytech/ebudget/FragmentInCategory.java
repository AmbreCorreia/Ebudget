package edu.polytech.ebudget;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.polytech.ebudget.camera.CameraActivity;
import edu.polytech.ebudget.databinding.FragmentInCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

public class FragmentInCategory extends Fragment {
    private static final String ARG_PARAM1 = "category";

    private Category category;
    private FragmentInCategoryBinding binding;

    public FragmentInCategory() {
        // Required empty public constructor
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
                    .setMessage("La supression de : " + category.name + ", sera d??finitive. Veuillez confirmer")
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

        binding.changecatbudget.setOnClickListener(click -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", category);
            bundle.putString("fragment", "InCategory");
            FragmentChangeBudget frag = new FragmentChangeBudget();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        binding.expensebudget.setText(String.valueOf(category.expense+"???"));
        binding.totalbudget.setText(String.valueOf(category.budget+"???"));

        int progress = (int) ((float)(category.expense*100)/ (float)category.budget);
        binding.progressBar.setProgress(progress);

        return binding.getRoot();
    }
}
