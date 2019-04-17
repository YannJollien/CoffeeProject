package com.example.coffeeproject2.settings;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.coffeeproject2.BuildConfig;
import com.example.coffeeproject2.R;

import org.w3c.dom.Text;

public class SettingsAboutActivity extends AppCompatActivity {

    TextView build;
    TextView build1;
    TextView build2;
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_about);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        about = (TextView)findViewById(R.id.text_about);

        build = (TextView) findViewById(R.id.about_version);

        build1 = (TextView) findViewById(R.id.about_version1);

        build2 = (TextView) findViewById(R.id.about_version2);

        build.setText("Version " + BuildConfig.VERSION_NAME);

        build1.setText("VersionID " + Build.ID);

        build2.setText("Running on " + Build.DEVICE);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsAboutActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });
    }


}
