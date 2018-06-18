package com.cybertech.cliniccare;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
/**
 * stagent24@gmail.com
 * Created by CyberTech on 11/24/2017.
 */
public class ClinicCare extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Previous version usage.
        // Firebase.setAndroidContext(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
