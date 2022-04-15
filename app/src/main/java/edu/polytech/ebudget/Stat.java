package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Stat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stats);
        returnButton();
    }

    private void returnButton(){
        Button btn = (Button) findViewById(R.id.returnButton3);
        btn.setOnClickListener(click ->{
            Intent intent = new Intent(Stat.this, Home.class);
            startActivity(intent);
        });
    }
}