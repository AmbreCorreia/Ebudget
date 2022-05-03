package edu.polytech.ebudget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.polytech.ebudget.databinding.FragmentlistexpenseBinding;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Item;
import edu.polytech.ebudget.utils.ExpenseListAdapter;


public class FragmentListExpenses extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        private FragmentlistexpenseBinding binding;

        public FragmentListExpenses() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHome.
         */
        // TODO: Rename and change types and number of parameters
        public static FragmentListExpenses newInstance(String param1, String param2) {
            FragmentListExpenses fragment = new FragmentListExpenses();
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
            binding = FragmentlistexpenseBinding.inflate(inflater, container, false);

            ExpenseListAdapter adapter = new ExpenseListAdapter(getContext(), R.layout.expenselist); // the adapter is a member field in the activity
            ListView lv = binding.ExpenseList.findViewById(R.id.ExpenseList);
            lv.setAdapter(adapter);

            FirebaseFirestore.getInstance().collection(FirebasePaths.items)
                    .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                    .whereEqualTo("isBought", true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Item> documents = new ArrayList();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Item item = document.toObject(Item.class);
                                documents.add(item);
                            }
                            //TODO si le tableau est vide
                            adapter.addAll(documents);
                        }
                    });

            return binding.getRoot();
        }
    }