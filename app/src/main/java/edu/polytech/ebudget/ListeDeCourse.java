package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListeDeCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_de_course);

        Button btn = (Button) findViewById(R.id.returnButton5);
        btn.setOnClickListener(this::returnButton);
    }

    private void returnButton(View v){
        Intent intent = new Intent(ListeDeCourse.this, MainActivity.class);
        startActivity(intent);
    }
}