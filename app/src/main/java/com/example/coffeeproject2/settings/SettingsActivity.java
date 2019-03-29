package com.example.coffeeproject2.settings;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.ui.login.MainActivity;
import com.example.coffeeproject2.ui.menu.MenuActivity;
import com.example.coffeeproject2.ui.plantation.PlantationViewActivity;

import java.util.Locale;
import java.util.prefs.Preferences;

public class SettingsActivity extends PreferenceActivity {

    ListPreference list;
    ListPreference listPreference = (ListPreference) findPreference("");
    Preference preference;
    SwitchPreference notif;
    public boolean isOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        list = (ListPreference) findPreference("languageType");

        preference = (Preference)findPreference("about");

        notif = (SwitchPreference)findPreference("switch");

        notif.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (notif.isChecked()) {
                    System.out.println("True");
                    isOn = true;
                    return true;
                }
                System.out.println("False");
                isOn = false;
                return false;
            }
        });

    }
}
