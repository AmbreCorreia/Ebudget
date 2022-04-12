package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Footer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer);

        //categories
        ((ImageButton)findViewById(R.id.imageButtonCategory)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), Category.class);
            this.startActivity(intent);
        });

        //notifications
        ((ImageButton)findViewById(R.id.imageButtonNotifs)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), Notification.class);
            this.startActivity(intent);
        });

        //Statistiques
        ((ImageButton)findViewById(R.id.imageButtonStats)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), Stat.class);
            this.startActivity(intent);
        });

        //Profil
        ((ImageButton)findViewById(R.id.imageButtonProfil)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), Profil.class);
            this.startActivity(intent);
        });

        //Liste de courses
        ((ImageButton)findViewById(R.id.imageButtonCourses)).setOnClickListener(click ->{
            Intent intent = new Intent(getApplicationContext(), ListeDeCourse.class);
            this.startActivity(intent);
        });
    }
}