package com.example.coffeeproject2.ui.storage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.adapter.StorageAdapterView;
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.util.OnAsyncEventListener;
import com.example.coffeeproject2.viewmodel.storage.StorageListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
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
    public String id_selected;

    Button save;
    Button cancel;
    Button date;

    DatePickerDialog dpd;

    DatabaseReference reference;
    DatabaseReference databaseStorage;
    ArrayList<Storage> storageList;

    StorageListViewModel model;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_edit);
/**
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_storage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final StorageAdapter storageAdapter = new StorageAdapter();
        recyclerView.setAdapter(storageAdapter);
*/
        dateEdit = findViewById(R.id.add_date);
        amountEdit = findViewById(R.id.add_amount);
        spinner = findViewById(R.id.spinner);

        // Create  an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(StorageEditActivity.this, R.array.types_array, R.layout.custom_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        databaseStorage = FirebaseDatabase.getInstance().getReference("storage");
        reference = FirebaseDatabase.getInstance().getReference().child("storage");

        storageList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id_test = "";
                Intent intent = getIntent();
                id_test = intent.getStringExtra(EXTRA_ID);
                id_selected = id_test;
                System.out.println("--------------------------------------------------------------------------");
                System.out.println(id_test);
                amountEdit.setText(intent.getStringExtra(EXTRA_AMOUNT));
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

                    Storage storage = dataSnapshot1.getValue(Storage.class);

                    storageList.add(storage);
                    for (int i = 0; i < storageList.size();i++){
                        System.out.println(storageList.get(i).getId());
                    }

                }

/**
                storageAdapter.setStorage(storageList);
                adapter = new StorageAdapterView(getApplicationContext(), storageList);
                recyclerView.setAdapter(storageAdapter);
 */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        //------------------ test- --------------------



        System.out.println("--------------------------------------------------------------------------");

        setTitle("Edit Coffee");

        //spinner.setSelection(2);




        //Get the info by id
        save = (Button) findViewById(R.id.save_add_storage);

        date = (Button)findViewById(R.id.date_choser);

        amountEdit = (EditText) findViewById(R.id.add_amount);
        dateEdit = (EditText) findViewById(R.id.add_date);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEdit.getText().toString().equals("") || dateEdit.getText().toString().equals("")) {
                    Toast.makeText(StorageEditActivity.this, "empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (dateCheck(dateEdit.getText().toString())) {
                        saveStorage();
                    } else {
                        Toast.makeText(StorageEditActivity.this, "date not correct",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        //spinner.setSelection(getIndex(spinner, intent.getStringExtra(EXTRA_TYPE)));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(StorageEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDayOfMonth) {
                        dateEdit.setText(mDayOfMonth + ".0" +mMonth+ "." + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });
    }

    private void saveStorage() {
        String type = spinner.getSelectedItem().toString();
        String amount = amountEdit.getText().toString();
        String date = dateEdit.getText().toString();

        System.out.println("--------------------------------------------------------------Datum in SaveStorage: " + date);


        Storage storage = new Storage( id_selected,  type,  Double. parseDouble(amount), date);
        for(int i = 0; i < storageList.size(); i++){
            if(storageList.get(i).getId().equals(id_selected)){
                storageList.set(i, storage);

            }
        }

        StorageListViewModel.Factory factory = new StorageListViewModel.Factory(getApplication(), id_selected);
        model = ViewModelProviders.of(this,factory).get(StorageListViewModel.class);
        model.updateStorage(storage, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        System.out.println("--------------------------------------------------------------Datum in SaveStorage: " + storage.getDate());
        startActivity(new Intent(StorageEditActivity.this, StorageViewActivity.class));
        Toast.makeText(StorageEditActivity.this, "Saved",
                Toast.LENGTH_LONG).show();
        amountEdit.setText("");
        dateEdit.setText("");
        startActivity(new Intent(getApplicationContext(), StorageViewActivity.class));
        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
        finish();
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
