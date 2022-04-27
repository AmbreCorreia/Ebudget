package edu.polytech.ebudget.datamodels;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Category implements Parcelable {
    public String name;
    public int budget;
    public String user;
    public String id;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private static final String TAG = "AddCategory";

    public Category(){}

    public Category(String name, int budget, String user){
        this.name = name;
        this.budget = budget;
        this.user = user;
        this.id = user+name;
    }

    protected Category(Parcel in) {
        name = in.readString();
        budget = in.readInt();
        user = in.readString();
        id = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public void addToDatabase(){
        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        category.put("budget", budget);
        category.put("user", user);
        category.put("id", id);

        database.collection("categories").document(id)
                .set(category)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + id))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(budget);
        parcel.writeString(user);
        parcel.writeString(id);
    }
}
