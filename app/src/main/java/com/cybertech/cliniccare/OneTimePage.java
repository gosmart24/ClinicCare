package com.cybertech.cliniccare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class OneTimePage extends AppCompatActivity {
    SharedPreferences preferences;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_page);

        tinyDB = new TinyDB(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isSet = preferences.getBoolean("isSetpref", false);
        if (isSet) {

            if (tinyDB.getInt("login") == 1) {
                startActivity(new Intent(OneTimePage.this, Student_Login.class));
                finish();
            } else if (tinyDB.getInt("login") == 2) {
                startActivity(new Intent(OneTimePage.this, Login.class));
                finish();
            }
        }

    }

    public void onLoadStudent(View view) {
        preferences.edit().putBoolean("isSetpref", true).apply();
        tinyDB.putInt("login", 1);
        startActivity(new Intent(OneTimePage.this, Student_Login.class));
        finish();
    }

    public void onLoadStaff(View view) {
        preferences.edit().putBoolean("isSetpref", true).apply();
        tinyDB.putInt("login", 2);
        startActivity(new Intent(OneTimePage.this, Login.class));
        finish();
    }
}
