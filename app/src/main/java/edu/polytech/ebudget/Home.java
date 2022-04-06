package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private int progress = 0;
    private Button increment;
    private Button decrement;
    private TextView textviewprogress;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        increment = findViewById(R.id.button_incr);
        decrement = findViewById(R.id.button_decr);
        textviewprogress = findViewById(R.id.text_view_progress);
        progressBar = findViewById(R.id.progress_bar);

        updateProgressBar();

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress <= 90) {
                    progress += 10;
                    updateProgressBar();
                }
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress >= 10) {
                    progress -= 10;
                    updateProgressBar();
                }
            }
        });
    }

    private void updateProgressBar() {
        progressBar.setProgress(progress);
       textviewprogress.setText(progress+"%");
    }
}