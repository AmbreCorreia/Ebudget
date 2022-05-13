package edu.polytech.ebudget.datamodels;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Item {
    public String name;
    public String category;
    public int price;
    public Date date;
    public int quantity;
    public String user;
    public boolean isBought;
    public String id;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddItem";

    public Item(){
        //requiered
    }

    public Item(String name, String category, int price, String user, boolean isBought){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = new Date();
        this.quantity = 1;
        this.user = user;
        this.isBought = isBought;
        this.id = user+name+category;
    }

    public Item(String name, String category, int price, int quantity, String user, boolean isBought){
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = new Date();
        this.quantity = quantity;
        this.user = user;
        this.isBought = isBought;
        this.id = user+name+category;
    }

    public void addToDatabase(){
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("category", category);
        item.put("price", price);
        item.put("date", date);
        item.put("quantity", quantity);
        item.put("user", user);
        item.put("isBought", isBought);
        item.put("id", id);

        database.collection(FirebasePaths.items).document(id)
                .set(item)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + id));
    }
}
