package com.cybertech.cliniccare;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * stagent24@gmail.com
 * Created by CyberTech on 11/24/2017.
 */
public class ClinicCare extends Application {

    TinyDB tinyDB;

    @Override
    public void onCreate() {
        super.onCreate();

        tinyDB = new TinyDB(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        if (!checkToken()) {
            String token = FirebaseInstanceId.getInstance().getToken();
            // saving token to share pref storage
            if (!TextUtils.isEmpty(token)) {
                tinyDB.putString("token", token);
            } else {
                Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkToken() {
        boolean istokenReady;
        String regId = tinyDB.getString("token");
        if (!TextUtils.isEmpty(regId)) {
            istokenReady = true;
        } else {
            istokenReady = false;
        }
        return istokenReady;
    }
}
