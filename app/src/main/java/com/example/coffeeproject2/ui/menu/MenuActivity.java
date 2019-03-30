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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
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

    double amArab = 0;
    double amRob = 0;
    double amL = 0;

    float[] am = {0,0,0};
    float[] hec = {0,0,0};
    String[] typ = {"Arabica" , "Robusta" , "Liberica" };

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
    //setup chart
    public void setupPieChartCoffee(float [] a, String [] b){
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < a.length; i++){
            pieEntries.add(new PieEntry(a[i], b[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Statistics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        chart.invalidate();

    }

    public void setupPieChartPlantation(float [] a, String [] b){
        List<PieEntry> pieEntries = new ArrayList<>();

        for(int i = 0; i < a.length; i++){
            pieEntries.add(new PieEntry(a[i], b[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Statistics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart2);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        chart.invalidate();

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
                System.out.println(storages.size());

                //update RecyclerView
                for (int i = 0; i < storages.size(); i++) {
                    sumStorage +=storages.get(i).getAmount();

                    System.out.println(storages.get(i).getAmount());
                    sumS.setText("Storage: " + String.valueOf(sumStorage)+ " Kg");
                    System.out.println(sumStorage);

                    switch(storages.get(i).getType()){
                        case "Arabica":
                            am[0] += storages.get(i).getAmount();
                            break;
                        case "Robusta":
                            am[1] += storages.get(i).getAmount();
                        case "Liberica":
                            am[2] += storages.get(i).getAmount();
                    }


                }
                System.out.println("Hier");
                for(int j = 0; j < am.length; j++){
                    System.out.println(am[j]);
                }
                setupPieChartCoffee(am, typ);

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
                    sumP.setText("Plantation: " +String.valueOf(sumPlantation) + " Ha");
                    System.out.println(sumPlantation);

                    switch(plantations.get(i).getType()){
                        case "Arabica":
                            hec[0] += plantations.get(i).getHectare();
                            break;
                        case "Robusta":
                            hec[1] += plantations.get(i).getHectare();
                        case "Liberica":
                            hec[2] += plantations.get(i).getHectare();
                    }
                }
                setupPieChartPlantation(hec, typ);
            }
        });
    }
}
