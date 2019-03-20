package com.example.coffeeproject2;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    TextView mail;
    TextView state;
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        name = (TextView)findViewById(R.id.text_name);
        mail = (TextView)findViewById(R.id.text_email);
        version = (TextView)findViewById(R.id.text_version);
        state = (TextView)findViewById(R.id.text_state);

        name.setText("Yann");
        mail.setText("jollienyann@yahoo.fr");
        state.setText("User");
        version.setText(Build.VERSION.RELEASE);
    }
}
