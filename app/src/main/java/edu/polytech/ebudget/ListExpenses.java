package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ListExpenses extends AppCompatActivity {

    //private FloatingActionButton fab;
    //private DatabaseReference budgetRef;
    //private FirebaseAuth mAuth;
    //private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_expenses);

        //mAuth = FirebaseAuth.getInstance();

        /*fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });*/
    }

    private void addItem(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_item_layout, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final Spinner itemSpinner = findViewById(R.id.itemSpinner);
        final EditText amount = findViewById(R.id.amount);
        final Button cancel = findViewById(R.id.cancel);
        final Button add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String budgetAmount = amount.getText().toString();
                String budgetItem = itemSpinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(budgetAmount)){
                    amount.setError("Amount is required");
                    return;
                }
                if(budgetItem.equals("Select an item")){
                    Toast.makeText(ListExpenses.this, "Select a valid item", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }
}