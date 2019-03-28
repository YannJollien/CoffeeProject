package com.example.coffeeproject2.settings;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.ui.login.MainActivity;
import com.example.coffeeproject2.ui.menu.MenuActivity;
import com.example.coffeeproject2.ui.plantation.PlantationViewActivity;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity {

    ListPreference list;
    ListPreference listPreference = (ListPreference) findPreference("");
    Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        list = (ListPreference) findPreference("languageType");

        preference = (Preference)findPreference("about");

        int index = Integer.parseInt(list.getValue());

        System.out.println(index);

        if (index==2) {
            changeLang( "de");
        }

    }

    public void changeLang(String lang) {
        Configuration config = getBaseContext().getResources().getConfiguration();

        switch (lang) {
            case "de":
                config.locale = Locale.GERMAN;
                break;
        }
        getResources().updateConfiguration(config,getResources().getDisplayMetrics());
    }
}
