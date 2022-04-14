package edu.polytech.ebudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import edu.polytech.ebudget.databinding.ActivityMainBinding;
import edu.polytech.ebudget.fragmentsFooter.FragmentCourses;
import edu.polytech.ebudget.fragmentsFooter.FragmentCategory;
import edu.polytech.ebudget.fragmentsFooter.FragmentNotif;
import edu.polytech.ebudget.fragmentsFooter.FragmentProfil;
import edu.polytech.ebudget.fragmentsFooter.FragmentRetour;
import edu.polytech.ebudget.fragmentsFooter.FragmentStats;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        replaceFragment(new FragmentRetour());

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
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}