package edu.polytech.ebudget.fragmentsFooter;

import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.polytech.ebudget.FragmentAddItemCourses;
import edu.polytech.ebudget.R;
import edu.polytech.ebudget.datamodels.Category;
import edu.polytech.ebudget.datamodels.FirebasePaths;
import edu.polytech.ebudget.datamodels.Item;
import edu.polytech.ebudget.utils.CourseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourses extends Fragment {
    private Button add;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();

    public FragmentCourses() {
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
        View rootview =  inflater.inflate(R.layout.activity_liste_de_course, container, false);

        add = (Button) rootview.findViewById(R.id.AddItemCourse);
        add.setOnClickListener(click -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FragmentAddItemCourses());
            fragmentTransaction.commit();
        });

        CourseListAdapter adapter = new CourseListAdapter(getContext(), R.layout.itemcourse); // the adapter is a member field in the activity
        ListView lv = rootview.findViewById(R.id.listcourse);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection(FirebasePaths.items)
                .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("isBought", false)
                .get()
                .addOnCompleteListener(task -> {
                    ArrayList<Item> documents = new ArrayList();
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Item item = document.toObject(Item.class);
                        documents.add(item);
                    }
                    if(!documents.isEmpty()) adapter.addAll(documents);
                    else {adapter.add(new Item("Aucun objet dans la liste", "", 0, "", false));}
                });

        rootview.findViewById(R.id.buyButton).setOnClickListener(click -> {
            //iterate on list items
            for (int i = 0; i < lv.getCount(); i++) {
                CheckBox checkbox = (CheckBox) lv.getChildAt(i).findViewById(R.id.checkBox);
                if (checkbox.isChecked()) {
                    Item item = (Item) lv.getAdapter().getItem(i);
                    //update isBought of the item
                    Map<String, Object> data = new HashMap<>();
                    data.put("isBought", true);
                    db.collection(FirebasePaths.items).document(item.id)
                            .set(data, SetOptions.merge());

                    //update category expense
                    FirebaseFirestore.getInstance().collection(FirebasePaths.categories)
                            .whereEqualTo("user", FirebaseAuth.getInstance().getUid())
                            .whereEqualTo("name", item.category)
                            .get()
                            .addOnCompleteListener(task -> {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Category category = document.toObject(Category.class);

                                    Map<String, Object> cat = new HashMap<>();
                                    cat.put("expense", category.expense + item.price * item.quantity);
                                    FirebaseFirestore.getInstance().collection(FirebasePaths.categories).document(category.id)
                                            .set(cat, SetOptions.merge());
                                }
                            });
                }
            }
            Toast.makeText(getContext(),"Confirmed!", Toast.LENGTH_SHORT).show();
            this.refresh();
        });

        return rootview;
    }

    private void refresh(){
        FragmentManager fragmentManager = getParentFragmentManager();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            fragmentManager.beginTransaction().detach(this).commitNow();
            fragmentManager.beginTransaction().attach(this).commitNow();
        } else {
            fragmentManager.beginTransaction().detach(this).attach(this).commit();
        }
    }
}