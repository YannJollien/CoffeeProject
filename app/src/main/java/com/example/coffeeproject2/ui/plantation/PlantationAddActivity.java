package com.example.coffeeproject2.ui.plantation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class PlantationAddActivity extends AppCompatActivity {

    public static Spinner spinner;

    public static EditText hectareEdit;
    public static EditText dateEdit;

    private PlantationViewModel plantationViewModel;

    Button save;

    PlantationDatabase plantationDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_add);

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

        plantationDatabase = Room.databaseBuilder(getApplicationContext(), PlantationDatabase.class,"plantation").allowMainThreadQueries().build();

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Get the info by id
        save = (Button)findViewById(R.id.save_add_plantation);

        hectareEdit = (EditText)findViewById(R.id.add_hectare);
        dateEdit = (EditText)findViewById(R.id.add_date);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlantation();
            }
        });

    }

    private void savePlantation(){
        String type = spinner.getSelectedItem().toString();
        double hectare = Double.parseDouble(hectareEdit.getText().toString());
        String date = dateEdit.getText().toString();
        Plantation plantation = new Plantation(type,hectare,date);
        plantationViewModel.insert(plantation);
        Toast.makeText(PlantationAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        hectareEdit.setText("");
        dateEdit.setText("");
    }
}
