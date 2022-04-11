package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //categories
        ((ImageButton)findViewById(R.id.imageButton5)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), Category.class);
            this.startActivity(intent);
        });
    }
}