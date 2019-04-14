package com.example.coffeeproject2.ui.storage;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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


import java.util.List;


public class StorageEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.coffeeproject2.ui.storage.EXTRA_ID";
    public static final String EXTRA_TYPE =
            "com.example.coffeeproject2.ui.storage.EXTRA_TYPE";
    public static final String EXTRA_AMOUNT =
            "com.example.coffeeproject2.ui.storage.EXTRA_AMOUNT";
    public static final String EXTRA_DATE =
            "com.example.coffeeproject2.ui.storage.EXTRA_DATE;";


    public static TextView result;
    public Spinner spinner;
    public EditText amountEdit;
    public EditText dateEdit;

    Button save;
    Button cancel;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_edit);

        dateEdit = findViewById(R.id.add_date);
        amountEdit = findViewById(R.id.add_amount);
        spinner = findViewById(R.id.spinner);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        setTitle("Edit Coffee");

        spinner.setSelection(2);



        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(StorageEditActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner


        spinner.setAdapter(adapter);

        //Get the info by id
        save = (Button) findViewById(R.id.save_add_storage);
        cancel = (Button) findViewById(R.id.btn_cancel);

        amountEdit = (EditText) findViewById(R.id.add_amount);
        dateEdit = (EditText) findViewById(R.id.add_date);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEdit.getText().toString().equals("") || dateEdit.getText().toString().equals("")) {
                    Toast.makeText(StorageEditActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Text nicht leer");
                    if (dateCheck(dateEdit.getText().toString())) {
                        //saveStorage();
                    } else {
                        Toast.makeText(StorageEditActivity.this, "date not correct",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //spinner.setSelection(getIndex(spinner, intent.getStringExtra(EXTRA_TYPE)));
    }

    /*private void saveStorage() {
        String type = spinner.getSelectedItem().toString();
        String amount = amountEdit.getText().toString();
        String date = dateEdit.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_DATE, date);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }*/

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
            Toast.makeText(StorageEditActivity.this, "Date format not correct",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
