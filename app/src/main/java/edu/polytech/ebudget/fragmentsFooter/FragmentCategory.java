package edu.polytech.ebudget.fragmentsFooter;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.polytech.ebudget.FragmentInCategory;
import edu.polytech.ebudget.categories.ICategoryAdapterListner;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Notification;
import edu.polytech.ebudget.utils.CategoryListAdapter;
import edu.polytech.ebudget.FragmentAddCategory;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.databinding.FragmentCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCategory extends Fragment implements ICategoryAdapterListner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCategoryBinding binding;

    public FragmentCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCategory newInstance(String param1, String param2) {
        FragmentCategory fragment = new FragmentCategory();
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
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), R.layout.category_list); // the adapter is a member field in the activity
        ListView lv = binding.Catlist.findViewById(R.id.Catlist);
        lv.setAdapter(adapter);

        adapter.addListner(this);

        FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Category> documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Category cat = document.toObject(Category.class);
                            documents.add(cat);
                        }

                        adapter.addAll(documents);
                    }
                });

        binding.addCategoryButton.setOnClickListener(click -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentAddCategory());
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }

    @Override
    public void onClickCategory(Category category, int position){
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        FragmentInCategory frag = new FragmentInCategory();
        frag.setArguments(bundle);
        FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, frag);
        fragmentTransaction.commit();
    }
}