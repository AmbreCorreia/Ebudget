package edu.polytech.ebudget.datamodels;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Category {
    public String name;
    public int budget;
    public String user;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddCategory";

    public Category(){}

    public Category(String name, int budget, String user){
        this.name = name;
        this.budget = budget;
        this.user = user;
    }

    public void addToDatabase(){
        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        category.put("budget", budget);
        category.put("user", user);

        database.collection("categories")
                .add(category)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}
