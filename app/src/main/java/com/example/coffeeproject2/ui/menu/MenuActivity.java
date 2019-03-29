package com.example.coffeeproject2.ui.menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.PlantationViewModel;
import com.example.coffeeproject2.StorageViewModel;
import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.settings.ProfileActivity;
import com.example.coffeeproject2.R;
import com.example.coffeeproject2.settings.SettingsAboutActivity;
import com.example.coffeeproject2.settings.SettingsActivity;
import com.example.coffeeproject2.ui.login.MainActivity;
import com.example.coffeeproject2.ui.plantation.PlantationViewActivity;
import com.example.coffeeproject2.ui.storage.StorageViewActivity;

import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private Locale locale;
    String currentLanguage = "en", currentLang;

    StorageViewModel storageViewModel;
    PlantationViewModel plantationViewModel;

    TextView sumS;
    TextView sumP;
    double sumStorage;
    double sumPlantation;

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

        //get Sum Storage
        sumStorage();

        //get Sum Plantaiton
        sumPlantation();


        //Calling the items and tell them what to do
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_storage:
                                Intent i1 = new Intent(MenuActivity.this, StorageViewActivity.class);
                                startActivity(i1);
                                break;
                            case R.id.nav_plantation:
                                Intent i2 = new Intent(MenuActivity.this, PlantationViewActivity.class);
                                startActivity(i2);
                                break;
                            case R.id.nav_settings:
                                Intent i3 = new Intent(MenuActivity.this, SettingsActivity.class);
                                startActivity(i3);
                                break;
                            case R.id.nav_logout:
                                Intent i4 = new Intent(MenuActivity.this, MainActivity.class);
                                startActivity(i4);
                                Toast.makeText(MenuActivity.this, "Logged uot",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_about:
                                Intent i5 = new Intent(MenuActivity.this, SettingsAboutActivity.class);
                                startActivity(i5);
                                break;
                            case R.id.nav_lang:
                                //calling changing langugage method
                                setLocale("de");

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

    //Change language method
    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            locale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(MenuActivity.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }

    public void sumStorage(){
        sumS = (TextView)findViewById(R.id.sum_storage);
        storageViewModel = ViewModelProviders.of(this).get(StorageViewModel.class);
        storageViewModel.getAllStorage().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(@Nullable List<Storage> storages) {
                //update RecyclerView
                for (int i = 0; i < storages.size(); i++) {
                    sumStorage +=storages.get(i).getAmount();
                    System.out.println(storages.get(i).getAmount());
                    sumS.setText(String.valueOf(sumStorage));
                    System.out.println(sumStorage);
                }
            }
        });
    }

    public void sumPlantation(){
        sumP = (TextView)findViewById(R.id.sum_plantation);
        plantationViewModel = ViewModelProviders.of(this).get(PlantationViewModel.class);
        plantationViewModel.getAllPlantation().observe(this, new Observer<List<Plantation>>() {
            @Override
            public void onChanged(@Nullable List<Plantation> plantations) {
                //update RecyclerView
                for (int i = 0; i < plantations.size(); i++) {
                    sumPlantation +=plantations.get(i).getHectare();
                    System.out.println(plantations.get(i).getHectare());
                    sumP.setText(String.valueOf(sumPlantation));
                    System.out.println(sumPlantation);
                }
            }
        });
    }
}
