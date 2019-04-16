package com.example.coffeeproject2.ui.menu;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.settings.ContactActivity;
import com.example.coffeeproject2.settings.ProfileActivity;
import com.example.coffeeproject2.settings.SettingsAboutActivity;
import com.example.coffeeproject2.settings.SettingsActivity;
import com.example.coffeeproject2.ui.login.MainActivity;
import com.example.coffeeproject2.ui.plantation.PlantationViewActivity;
import com.example.coffeeproject2.ui.storage.StorageViewActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    String currentLanguage = "en", currentLang;
    TextView sumS;
    TextView sumP;
    double sumStorage;
    double sumPlantation;
    float[] am = {0, 0, 0};
    float[] hec = {0, 0, 0};
    String[] typ = {"Arabica", "Robusta", "Liberica"};
    Button profile;
    private DrawerLayout drawerLayout;
    private Locale locale;

    ArrayList<Storage> amountList;
    ArrayList<Plantation> hectareList;

    DatabaseReference databaseStorage;
    DatabaseReference databasePlantation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle("Dashboard");

        //Display the drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        databaseStorage = FirebaseDatabase.getInstance().getReference("storage");
        databasePlantation = FirebaseDatabase.getInstance().getReference("plantation");

        amountList =  new ArrayList<>();
        hectareList =  new ArrayList<>();

        databaseStorage.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sumS = (TextView) findViewById(R.id.sum_storage);
                amountList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()){
                    Storage strage = storageSnapshot.getValue(Storage.class);
                    amountList.add(strage);
                }
                for (int i = 0; i < amountList.size();i++){
                    sumStorage += amountList.get(i).getAmount();

                    sumS.setText("Storage: " + String.valueOf(sumStorage) + " Kg");
                    System.out.println(sumStorage);

                    switch (amountList.get(i).getType()) {
                        case "Arabica":
                            am[0] += amountList.get(i).getAmount();
                            break;
                        case "Robusta":
                            am[1] += amountList.get(i).getAmount();
                            break;
                        case "Liberica":
                            am[2] += amountList.get(i).getAmount();
                            break;
                    }
                    setupPieChartCoffee(am, typ);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databasePlantation.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                sumP = (TextView) findViewById(R.id.sum_plantation);
                hectareList.clear();

                for (DataSnapshot plantationSnapshot : dataSnapshot1.getChildren()){
                    Plantation plantation = plantationSnapshot.getValue(Plantation.class);
                    hectareList.add(plantation);
                }
                for (int i = 0; i < hectareList.size();i++){
                    sumPlantation += hectareList.get(i).getHectare();

                    sumP.setText("Plantation: " + String.valueOf(sumPlantation) + " Kg");
                    System.out.println(sumPlantation);

                    switch (hectareList.get(i).getType()) {
                        case "Arabica":
                            am[0] += hectareList.get(i).getHectare();
                            break;
                        case "Robusta":
                            am[1] += hectareList.get(i).getHectare();
                            break;
                        case "Liberica":
                            am[2] += hectareList.get(i).getHectare();
                            break;
                    }
                    setupPieChartPlantation(am, typ);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //get Sum Storage
        //sumStorage();

        //get Sum Plantaiton
        //sumPlantation();

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
                                Toast.makeText(MenuActivity.this, "Logged out",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case R.id.nav_about:
                                Intent i5 = new Intent(MenuActivity.this, SettingsAboutActivity.class);
                                startActivity(i5);
                                break;
                            case R.id.nav_contact:
                                System.out.println(R.id.nav_contact);
                                Intent i6 = new Intent(MenuActivity.this, ContactActivity.class);
                                startActivity(i6);
                                break;
                            case R.id.nav_lang:
                                System.out.println(R.id.nav_lang);
                                //calling changing langugage method
                                setLocale("de");
                        }
                        return true;
                    }
                });

        ImageButton ib = (ImageButton) navigationView.getHeaderView(0).findViewById(R.id.nav_button);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
    }


    //setup chart
    public void setupPieChartCoffee(float[] a, String[] b) {
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < a.length; i++) {
            pieEntries.add(new PieEntry(a[i], b[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Statistics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(30);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setData(data);
        chart.invalidate();

    }

    public void setupPieChartPlantation(float[] a, String[] b) {
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < a.length; i++) {
            pieEntries.add(new PieEntry(a[i], b[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Statistics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(30);

        PieChart chart = (PieChart) findViewById(R.id.chart2);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
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

    /*public void sumStorage() {

        sumS = (TextView) findViewById(R.id.sum_storage);

        //update RecyclerView
        for (int i = 0; i < amountList.size(); i++) {
            sumStorage += amountList.get(i).getAmount();

            sumS.setText("Storage: " + String.valueOf(sumStorage) + " Kg");
            System.out.println(sumStorage);

            switch (amountList.get(i).getType()) {
                case "Arabica":
                    am[0] += amountList.get(i).getAmount();
                    break;
                case "Robusta":
                    am[1] += amountList.get(i).getAmount();
                    break;
                case "Liberica":
                    am[2] += amountList.get(i).getAmount();
                    break;
            }
            System.out.println(sumStorage);
        }
        setupPieChartCoffee(am, typ);

    }*/

}

    /*public void sumPlantation() {
        sumP = (TextView) findViewById(R.id.sum_plantation);
        plantationViewModel = ViewModelProviders.of(this).get(PlantationViewModel.class);
        plantationViewModel.getAllPlantation().observe(this, new Observer<List<Plantation>>() {
            @Override
            public void onChanged(@Nullable List<Plantation> plantations) {
                //update RecyclerView
                for (int i = 0; i < plantations.size(); i++) {
                    sumPlantation += plantations.get(i).getHectare();
                    System.out.println(plantations.get(i).getHectare());
                    sumP.setText("Plantation: " + String.valueOf(sumPlantation) + " Ha");
                    System.out.println(sumPlantation);

                    switch (plantations.get(i).getType()) {
                        case "Arabica":
                            hec[0] += plantations.get(i).getHectare();
                            break;
                        case "Robusta":
                            hec[1] += plantations.get(i).getHectare();
                            break;
                        case "Liberica":
                            hec[2] += plantations.get(i).getHectare();
                            break;
                    }
                }
                setupPieChartPlantation(hec, typ);
            }
        });
    }*/

