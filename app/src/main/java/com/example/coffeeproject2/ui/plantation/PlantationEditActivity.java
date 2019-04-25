package com.example.coffeeproject2.ui.plantation;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.ui.storage.StorageEditActivity;
import com.example.coffeeproject2.ui.storage.StorageViewActivity;
import com.example.coffeeproject2.util.OnAsyncEventListener;
import com.example.coffeeproject2.viewmodel.plantation.PlantationListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PlantationEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.coffeeproject2.ui.plantation.EXTRA_ID";
    public static final String EXTRA_TYPE =
            "com.example.coffeeproject2.ui.plantation.EXTRA_TYPE";
    public static final String EXTRA_HECTARE =
            "com.example.coffeeproject2.ui.plantation.EXTRA_HECTARE";
    public static final String EXTRA_DATE =
            "com.example.coffeeproject2.ui.plantation.EXTRA_DATE;";

    public Spinner spinner;
    public EditText hectareEdit;
    public EditText dateEdit;
    public String id_selected;
    Button save;

    Button date;

    DatePickerDialog dpd;

    DatabaseReference reference;
    DatabaseReference databasePlantation;
    ArrayList<Plantation> plantationList;

    PlantationListViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_edit);

        dateEdit = findViewById(R.id.add_date);
        hectareEdit = findViewById(R.id.add_hectare);
        spinner = findViewById(R.id.spinner);

        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PlantationEditActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        databasePlantation = FirebaseDatabase.getInstance().getReference("plantation");
        reference = FirebaseDatabase.getInstance().getReference().child("plantation");

        plantationList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id_test = "";
                Intent intent = getIntent();
                id_test = intent.getStringExtra(EXTRA_ID);
                id_selected = id_test;
                hectareEdit.setText(intent.getStringExtra(EXTRA_HECTARE));
                dateEdit.setText(intent.getStringExtra(EXTRA_DATE));



                String type_spinner = intent.getStringExtra(EXTRA_TYPE);


                switch(type_spinner){
                    case "Arabica":
                        spinner.setSelection(0);
                        break;
                    case "Robusta":
                        spinner.setSelection(1);
                        break;
                    case "Liberica":
                        spinner.setSelection(2);
                        break;
                }

                System.out.println(intent.getStringExtra(EXTRA_TYPE));
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Plantation plantation = dataSnapshot1.getValue(Plantation.class);

                    plantationList.add(plantation);
                    for (int i = 0; i < plantationList.size();i++){
                        System.out.println(plantationList.get(i).getId());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.plantation_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();


        dateEdit.setText(intent.getStringExtra(EXTRA_DATE));
        hectareEdit.setText(intent.getStringExtra(EXTRA_HECTARE));
        spinner.setSelection(2);


        spinner = (Spinner) findViewById(R.id.spinner);

        //set Titel of view
        setTitle("Edit Plantation");
        //Get the info by id
        save = (Button) findViewById(R.id.save_add_plantation);

        date = (Button)findViewById(R.id.date_choser);


        hectareEdit = (EditText) findViewById(R.id.add_hectare);
        dateEdit = (EditText) findViewById(R.id.add_date);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hectareEdit.getText().toString().equals("") || dateEdit.getText().toString().equals("")) {
                    Toast.makeText(PlantationEditActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (dateCheck(dateEdit.getText().toString())) {
                        savePlantation();
                    } else {
                        Toast.makeText(PlantationEditActivity.this, "date not correct",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        spinner.setSelection(getIndex(spinner, intent.getStringExtra(EXTRA_TYPE)));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(PlantationEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateEdit.setText(mDayOfMonth + ".0" +mMonth+ "." + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

    }

    private void savePlantation() {
        String type = spinner.getSelectedItem().toString();
        String hectare = hectareEdit.getText().toString();
        String date = dateEdit.getText().toString();


        Plantation plantation = new Plantation( id_selected,  type,  Double. parseDouble(hectare), date);
        for(int i = 0; i < plantationList.size(); i++){
            if(plantationList.get(i).getId().equals(id_selected)){
                plantationList.set(i, plantation);

            }
        }

        //reference.child(plantation.getId()).setValue(plantation);
        PlantationListViewModel.Factory factory = new PlantationListViewModel.Factory(getApplication(), id_selected);
        model = ViewModelProviders.of(this,factory).get(PlantationListViewModel.class);
        model.updatePlantation(plantation, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        startActivity(new Intent(PlantationEditActivity.this, PlantationViewActivity.class));
        Toast.makeText(PlantationEditActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        hectareEdit.setText("");
        dateEdit.setText("");
        startActivity(new Intent(getApplicationContext(), PlantationViewActivity.class));
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
        finish();

    }

    //index finder
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }

    public boolean dateCheck(String date) {
        try {
            int a = Integer.parseInt(date.substring(0, 2));
            int b = Integer.parseInt(date.substring(3, 5));
            int c = Integer.parseInt(date.substring(6, 10));

            if (c > 1900 && c < 2400) {
                if (b < 13 && b > 0) {
                    if (b == 1 || b == 3 || b == 5 || b == 7 || b == 8 || b == 10 || b == 12) {
                        if (a > 0 && a < 32) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    if (b == 2 || b == 4 || b == 6 || b == 9 || b == 11) {
                        if (a > 0 && a < 31) {
                            return true;
                        }
                        return false;
                    }
                } else {
                    return false;
                }

            } else {
                return false;
            }
        } catch (StringIndexOutOfBoundsException a) {
            Toast.makeText(PlantationEditActivity.this, "Date format not correct",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
