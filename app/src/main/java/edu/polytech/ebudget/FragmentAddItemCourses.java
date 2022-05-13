package edu.polytech.ebudget;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import edu.polytech.ebudget.databinding.FragmentAdditemCourseBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Item;
import edu.polytech.ebudget.fragmentsFooter.FragmentCourses;

public class FragmentAddItemCourses extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentAdditemCourseBinding bind;
    private Category category;

    public FragmentAddItemCourses() {
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
        bind = FragmentAdditemCourseBinding.inflate(inflater, container, false);

        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList documents = new ArrayList();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Category cat = document.toObject(Category.class);
                        documents.add(cat.name);
                    }

                    ArrayAdapter array = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, documents);
                    array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bind.categorySpinner.setAdapter(array);
                });

        bind.categorySpinner.setOnItemSelectedListener(this);

        bind.addButton.setOnClickListener(click -> {
            String name = bind.nameInput.getText().toString().trim();
            int price = Integer.parseInt(bind.priceInput.getText().toString().trim());
            int quantity = Integer.parseInt(bind.quantityInput.getText().toString().trim());
            String user = FirebaseAuth.getInstance().getUid();

            new Item(name, category.name, price, quantity, user, false).addToDatabase();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentCourses());
            fragmentTransaction.commit();
        });

        return bind.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String catName = adapterView.getItemAtPosition(i).toString();
        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("name", catName)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        category = document.toObject(Category.class);
                    }
                });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.e("Firebase", "No category found");
    }
}