package com.example.coffeeproject2.ui.menu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.coffeeproject2.ui.plantation.PlantationActivity;
import com.example.coffeeproject2.ProfileActivity;
import com.example.coffeeproject2.R;
import com.example.coffeeproject2.SettingsActivity;
import com.example.coffeeproject2.ui.storage.StorageActivity;

import java.util.Calendar;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    Button profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_storage:
                                Intent i1 = new Intent(MenuActivity.this, StorageActivity.class);
                                startActivity(i1);
                                break;
                            case R.id.nav_plantation:
                                Intent i2 = new Intent(MenuActivity.this, PlantationActivity.class);
                                startActivity(i2);
                                break;
                            case R.id.nav_settings:
                                Intent i3 = new Intent(MenuActivity.this, SettingsActivity.class);
                                startActivity(i3);
                                break;
                        }
                        return true;
                    }
                });

        ImageButton ib = (ImageButton)navigationView.getHeaderView(0).findViewById(R.id.nav_button);
        ib.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });

    }

    //Open the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
