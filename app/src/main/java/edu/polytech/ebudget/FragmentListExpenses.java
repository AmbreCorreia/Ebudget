package edu.polytech.ebudget;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import edu.polytech.ebudget.databinding.FragmentlistexpenseBinding;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Item;
import edu.polytech.ebudget.utils.ExpenseListAdapter;


public class FragmentListExpenses extends Fragment {
        private FragmentlistexpenseBinding binding;

        public FragmentListExpenses() {
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
            binding = FragmentlistexpenseBinding.inflate(inflater, container, false);

            ExpenseListAdapter adapter = new ExpenseListAdapter(getContext(), R.layout.expenselist); // the adapter is a member field in the activity
            ListView lv = binding.ExpenseList.findViewById(R.id.ExpenseList);
            lv.setAdapter(adapter);

            FirebaseFirestore.getInstance().collection(FirebasePaths.items)
                    .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                    .whereEqualTo("isBought", true)
                    .get()
                    .addOnCompleteListener(task -> {
                        ArrayList<Item> documents = new ArrayList();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            Item item = document.toObject(Item.class);
                            documents.add(item);
                        }
                        //TODO si le tableau est vide
                        adapter.addAll(documents);
                    });

            return binding.getRoot();
        }
    }