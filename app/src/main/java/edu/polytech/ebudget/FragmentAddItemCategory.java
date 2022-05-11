package edu.polytech.ebudget;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import edu.polytech.ebudget.databinding.FragmentAdditemCategoryBinding;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Item;
import edu.polytech.ebudget.datamodels.Notification;
import edu.polytech.ebudget.datamodels.Preference;
import edu.polytech.ebudget.datamodels.notiffactory.AbstractNotifFactory;
import edu.polytech.ebudget.datamodels.notiffactory.IThreshold;
import edu.polytech.ebudget.datamodels.notiffactory.NotifPhoneFactory;
import edu.polytech.ebudget.datamodels.notiffactory.NotifPhoneImage;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;

public class FragmentAddItemCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATEGORY = "category";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Category category;
    private String mParam2;
    private FragmentAdditemCategoryBinding bind;

    public FragmentAddItemCategory() {
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
    public static FragmentAddItemCategory newInstance(String param1, String param2) {
        FragmentAddItemCategory fragment = new FragmentAddItemCategory();
        Bundle args = new Bundle();
        args.putString(CATEGORY, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments() != null) {
                category = getArguments().getParcelable(CATEGORY);
            }
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bind = FragmentAdditemCategoryBinding.inflate(inflater, container, false);

        bind.addButton.setOnClickListener(click -> {
            Log.d("BORDEL", "pourquoi");
            String name = bind.nameInput.getText().toString().trim();
            int price = Integer.parseInt(bind.priceInput.getText().toString().trim());
            String user = FirebaseAuth.getInstance().getUid();

            Item item = new Item(name, category.name, price, user, true);
            item.addToDatabase();

            //update category expense
            Map<String, Object> data = new HashMap<>();
            data.put("expense", category.expense + price);
            FirebaseFirestore.getInstance().collection(FirebasePaths.categories).document(category.id)
                    .set(data, SetOptions.merge());

            this.check(category, item, user);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentCategory());
            fragmentTransaction.commit();
        });

        return bind.getRoot();
    }

    private void check(Category category, Item item, String user){
        Log.d("NOTIF", "dans check");
        FirebaseFirestore.getInstance().collection(FirebasePaths.preferences)
                .whereEqualTo("user", user)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Preference pref = document.toObject(Preference.class);
                        if(pref.notifictionEnabled){
                            FirebaseFirestore.getInstance().collection(FirebasePaths.notifications)
                                    .whereEqualTo("user", user)
                                    .whereEqualTo("category", category.name)
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        for(QueryDocumentSnapshot notif : task1.getResult()){
                                            Notification notification = notif.toObject(Notification.class);
                                            Log.d("NOTIF", "requete");
                                            int actual = (int) ((float)(category.expense*100)/ (float)category.budget);
                                            int future = (int) ((float)((category.expense + item.price*item.quantity)*100)/ (float)category.budget);
                                            AbstractNotifFactory factory = new NotifPhoneFactory();
                                            NotifPhoneImage notifImg = factory.createNotification(null, notification.category, notification.description, notification.id, notification.user,notification.threshold, true);
                                            if(actual < notification.threshold && future > notification.threshold){
                                                Log.d("NOTIF", "dans if");
                                                notifImg.sendNotif(new NotificationCompat.Builder(getActivity().getApplicationContext(), "channel1"), 0,getResources(), IThreshold.ALERT_IMG);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}