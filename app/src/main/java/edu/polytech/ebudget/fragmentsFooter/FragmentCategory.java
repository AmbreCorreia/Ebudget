package edu.polytech.ebudget.fragmentsFooter;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.utils.CategoryListAdapter;
import edu.polytech.ebudget.FragmentAddCategory;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.databinding.FragmentCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment{
    private FragmentCategoryBinding binding;

    public FragmentCategory() {
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
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), R.layout.category_list); // the adapter is a member field in the activity
        ListView lv = binding.Catlist.findViewById(R.id.Catlist);
        lv.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<Category> documents = new ArrayList();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Category cat = document.toObject(Category.class);
                        documents.add(cat);
                    }
                    adapter.addAll(documents);
                });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("category", adapter.getItem(position));
            FragmentInCategory frag = new FragmentInCategory();
            frag.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, frag);
            fragmentTransaction.commit();
        });

        binding.addCategoryButton.setOnClickListener(click -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentAddCategory());
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }
}