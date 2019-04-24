package com.example.coffeeproject2.ui.plantation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Plantation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class PlantationAddActivity extends AppCompatActivity {

    public static Spinner spinner;
    public static EditText hectareEdit;
    public static EditText dateEdit;

    DatabaseReference databasePlantation;

    DatePickerDialog dpd;


    Button save;
    Button date;
    //StorageDatabase storageDatabase;

    public static TextView result;

    ArrayList<Plantation> plantationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_add);

        databasePlantation = FirebaseDatabase.getInstance().getReference("plantation");


        setTitle("Add Plantation");

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.plantation_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        //storageDatabase = Room.databaseBuilder(getApplicationContext(), StorageDatabase.class, "recyclerView").allowMainThreadQueries().build();

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PlantationAddActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Get the info by id
        save = (Button) findViewById(R.id.save_add_plantation);
        date = (Button)findViewById(R.id.date_choser);

        hectareEdit = (EditText) findViewById(R.id.add_hectare);
        dateEdit = (EditText) findViewById(R.id.add_date);


        plantationList = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hectareEdit.getText().toString().equals("") || dateEdit.getText().toString().equals("")) {
                    Toast.makeText(PlantationAddActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (dateCheck(dateEdit.getText().toString())) {
                        savePlantation();
                    } else {
                        Toast.makeText(PlantationAddActivity.this, "date not correct",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(PlantationAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateEdit.setText(mDayOfMonth + "." +mMonth+ "." + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        databasePlantation.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plantationList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()){
                    Plantation plantation = storageSnapshot.getValue(Plantation.class);
                    plantationList.add(plantation);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void savePlantation() {
        String type = spinner.getSelectedItem().toString();
        double hectare = Double.parseDouble(hectareEdit.getText().toString());
        String date = dateEdit.getText().toString();

        String id = databasePlantation.push().getKey();

        Plantation plantation = new Plantation(id, type, hectare, date);

        databasePlantation.child(id).setValue(plantation);
        //storageViewModel.insert(recyclerView);
        startActivity(new Intent(PlantationAddActivity.this, PlantationViewActivity.class));
        Toast.makeText(PlantationAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        hectareEdit.setText("");
        dateEdit.setText("");
        System.out.println(id);
        startActivity(new Intent(getApplicationContext(), PlantationViewActivity.class));

    }



    //date chack
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
            Toast.makeText(PlantationAddActivity.this, "Date format not correct",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
