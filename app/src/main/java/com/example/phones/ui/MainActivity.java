package com.example.phones.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.phones.R;
import com.example.phones.model.Phones;
import com.example.phones.ui.PhonesFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initToolbarDrawer();

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

    private void initToolbarDrawer() {
        Toolbar toolbar = initToolbar();
       DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
       ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.about, R.string.exit);
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
       actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.action_drawer_about:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("")
                                .replace(R.id.fragment, new AboutAppFragment())
                                .commit();
                        drawerLayout.close();
                        return true;

                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }

                return false;
            }
        });

    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}