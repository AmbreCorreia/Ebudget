package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import edu.polytech.ebudget.databinding.ActivityMainBinding;
import edu.polytech.ebudget.fragmentsFooter.FragmentCourses;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;
import edu.polytech.ebudget.fragmentsFooter.FragmentHome;
import edu.polytech.ebudget.fragmentsFooter.FragmentNotif;
import edu.polytech.ebudget.fragmentsFooter.FragmentProfil;
import edu.polytech.ebudget.fragmentsFooter.FragmentStats;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        replaceFragment(new FragmentHome());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.category:
                    replaceFragment(new FragmentCategory());
                    break;
                case R.id.notification:
                    replaceFragment(new FragmentNotif());
                    break;
                case R.id.statistique:
                    replaceFragment(new FragmentStats());
                    break;
                case R.id.profil:
                    replaceFragment(new FragmentProfil());
                    break;
                case R.id.course:
                    replaceFragment(new FragmentCourses());
                    break;
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home0) {
            replaceFragment(new FragmentHome());
        }
        return super.onOptionsItemSelected(item);
    }
}