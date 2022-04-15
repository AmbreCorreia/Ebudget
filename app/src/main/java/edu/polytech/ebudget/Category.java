package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_category);

        Button goLogin = (Button) findViewById(R.id.button_decr_fruits);

        returnButton();

    }

    private void returnButton(){
        Button btn = (Button) findViewById(R.id.returnButton1);
        btn.setOnClickListener(click ->{
            Intent intent = new Intent(Category.this, Home.class);
            startActivity(intent);
        });
    }
}