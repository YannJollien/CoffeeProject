package com.example.coffeeproject2.ui.storage;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.ScanActivity;
import com.example.coffeeproject2.database.StorageDatabase;
import com.example.coffeeproject2.database.entity.Storage;

import org.w3c.dom.Text;


public class StorageAddActivity extends AppCompatActivity {

    static String type;
    static double amount;
    static String date;
    public static Spinner spinner;

    public static EditText amountEdit;
    public static EditText dateEdit;

    Button save;

    StorageDatabase storageDatabase;

    public static TextView result;

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

    //set the camera item in Actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scanner_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When camera icon clicked open qr scanner
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_qr:
                //Show scanner
                startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
