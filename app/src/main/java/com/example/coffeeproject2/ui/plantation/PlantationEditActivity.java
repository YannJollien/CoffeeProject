package com.example.coffeeproject2.ui.plantation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coffeeproject2.PlantationViewModel;
import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.PlantationDatabase;
import com.example.coffeeproject2.database.entity.Plantation;

import java.util.List;

public class PlantationEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.coffeeproject2.ui.storage.EXTRA_ID";
    public static final String EXTRA_TYPE =
            "com.example.coffeeproject2.ui.storage.EXTRA_TYPE";
    public static final String EXTRA_HECTARE =
            "com.example.coffeeproject2.ui.storage.EXTRA_HECTARE";
    public static final String EXTRA_DATE =
            "com.example.coffeeproject2.ui.storage.EXTRA_DATE;";

    public Spinner spinner;
    public EditText hectareEdit;
    public EditText dateEdit;
    Button save;
    PlantationDatabase plantationDatabase;
    private PlantationViewModel plantationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_edit);

        dateEdit = findViewById(R.id.add_date);
        hectareEdit = findViewById(R.id.add_hectare);
        spinner = findViewById(R.id.spinner);

        plantationViewModel = ViewModelProviders.of(this).get(PlantationViewModel.class);
        plantationViewModel.getAllPlantation().observe(this, new Observer<List<Plantation>>() {
            @Override
            public void onChanged(@Nullable List<Plantation> plantations) {
                //update RecyclerView
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

        plantationDatabase = Room.databaseBuilder(getApplicationContext(), PlantationDatabase.class, "plantation").allowMainThreadQueries().build();

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PlantationEditActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //set Titel of view
        setTitle("Edit Plantation");
        //Get the info by id
        save = (Button) findViewById(R.id.save_add_plantation);

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

    }

    private void savePlantation() {
        String type = spinner.getSelectedItem().toString();
        String hectare = hectareEdit.getText().toString();
        String date = dateEdit.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_HECTARE, hectare);
        data.putExtra(EXTRA_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
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
