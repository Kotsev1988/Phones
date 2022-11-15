package com.example.phones.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.phones.R;
import com.example.phones.model.Phones;
import com.example.phones.ui.PhonesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){
            PhonesFragment phonesFragment = new PhonesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, phonesFragment)
                    .commit();
        }

      /* if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            PhonesFragment phonesFragment = new PhonesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, phonesFragment)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentDetails, DetailsFragment.newInstance(0))
                    .commit();
        } else {
            PhonesFragment phonesFragment = new PhonesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, phonesFragment)
                    .commit();
        }*/
    }
}