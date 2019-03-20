package com.example.coffeeproject2.ui.storage;

import android.arch.persistence.room.Room;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.StorageDatabase;
import com.example.coffeeproject2.database.entity.Storage;


public class StorageAddActivity extends AppCompatActivity {

    String type;
    double amount;
    String date;
    Spinner spinner;

    EditText amountEdit;
    EditText dateEdit;

    Button save;

    StorageDatabase storageDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        storageDatabase = Room.databaseBuilder(getApplicationContext(), StorageDatabase.class,"storage").allowMainThreadQueries().build();

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Get the info by id
        save = (Button)findViewById(R.id.save_add_storage);

        amountEdit = (EditText)findViewById(R.id.add_amount);
        dateEdit = (EditText)findViewById(R.id.add_date);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = spinner.getSelectedItem().toString();

                amount = Double.parseDouble(amountEdit.getText().toString());
                date = dateEdit.getText().toString();

                Storage storage = new Storage();
                storage.setType(type);
                storage.setAmount(amount);
                storage.setDate(date);

                storageDatabase.storageDao().insertStorage(storage);

                Toast.makeText(StorageAddActivity.this, "Saved",
                        Toast.LENGTH_LONG).show();
                amountEdit.setText("");
                dateEdit.setText("");
            }
        });
    }

}
