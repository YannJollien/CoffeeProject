package com.example.coffeeproject2.ui.storage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.ScanActivity;
import com.example.coffeeproject2.database.entity.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class StorageAddActivity extends AppCompatActivity{

    public static Spinner spinner;
    public static EditText amountEdit;
    public static EditText dateEdit;

    DatabaseReference databaseStorage;

    DatePickerDialog dpd;


    Button date;
    Button save;
    //StorageDatabase storageDatabase;

    public static TextView result;

    ArrayList<Storage> storageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add);

        databaseStorage = FirebaseDatabase.getInstance().getReference("storage");


        setTitle("Add Coffee");

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        //storageDatabase = Room.databaseBuilder(getApplicationContext(), StorageDatabase.class, "recyclerView").allowMainThreadQueries().build();

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(StorageAddActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Get the info by id
        save = (Button) findViewById(R.id.save_add_storage);

        date = (Button)findViewById(R.id.date_choser);

        amountEdit = (EditText) findViewById(R.id.add_amount);
        dateEdit = (EditText) findViewById(R.id.add_date);


        storageList = new ArrayList<>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEdit.getText().toString().equals("") || dateEdit.getText().toString().equals("")) {
                    Toast.makeText(StorageAddActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (dateCheck(dateEdit.getText().toString())) {
                        saveStorage();
                    } else {
                        Toast.makeText(StorageAddActivity.this, "date not correct",
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

                dpd = new DatePickerDialog(StorageAddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        databaseStorage.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                storageList.clear();

                for (DataSnapshot storageSnapshot : dataSnapshot.getChildren()){
                    Storage strage = storageSnapshot.getValue(Storage.class);
                    storageList.add(strage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveStorage() {
        String type = spinner.getSelectedItem().toString();
        double amount = Double.parseDouble(amountEdit.getText().toString());
        String date = dateEdit.getText().toString();

        String id = databaseStorage.push().getKey();

        Storage storage = new Storage(id,type, amount, date);

        databaseStorage.child(id).setValue(storage);
        //storageViewModel.insert(recyclerView);
        startActivity(new Intent(StorageAddActivity.this, StorageViewActivity.class));
        Toast.makeText(StorageAddActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        amountEdit.setText("");
        dateEdit.setText("");
        System.out.println(id);
        startActivity(new Intent(getApplicationContext(), StorageViewActivity.class));

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
            Toast.makeText(StorageAddActivity.this, "Date format not correct",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }



}
