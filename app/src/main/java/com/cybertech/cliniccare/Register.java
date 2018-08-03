package com.cybertech.cliniccare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class Register extends AppCompatActivity {

    private EditText u_nameED, e_idED, usernameED, PhoneNOED, passwordED;
    Spinner spinner_userType;
    TinyDB tinyDB;
    private StaffsModel staffsModel;
    DatabaseReference databaseReference;
    StorageReference storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        u_nameED = (EditText) findViewById(R.id.reg_name);
        e_idED = (EditText) findViewById(R.id.reg_E_id);
        usernameED = (EditText) findViewById(R.id.reg_username);
        PhoneNOED = (EditText) findViewById(R.id.reg_phoneNo);
        passwordED = (EditText) findViewById(R.id.reg_password);
        spinner_userType = (Spinner) findViewById(R.id.reg_spinner_usertype);

        tinyDB = new TinyDB(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Clinic").child("staffs");

    }

    public void onCreateUser(View view) {
        final String useType = spinner_userType.getSelectedItem().toString();
        final String name = u_nameED.getText().toString();
        String employment_ID = e_idED.getText().toString();
        final String username = usernameED.getText().toString();
        final String pass = passwordED.getText().toString();
        String phone = PhoneNOED.getText().toString();
        staffsModel = new StaffsModel();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(employment_ID) || TextUtils.isEmpty(username) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "Required fields cannot be empty!", Toast.LENGTH_LONG).show();

        } else {
          progressDialog = ProgressDialog.show(Register.this, null, "Processing...please wait", true, true);
            final DatabaseReference ref = databaseReference.push();
            staffsModel.setKey(ref.getKey());
            staffsModel.setStaffsName(name);
            staffsModel.setEmployment_ID(employment_ID);
            staffsModel.setPhone(phone);
            staffsModel.setUsertype(useType);
            ref.setValue(staffsModel).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error has occurred with on uploading details with this Message : " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        tinyDB.putString("userpass", pass);
                        tinyDB.putString("userids", username);
                        tinyDB.putString("staffname", name);
                        tinyDB.putString("staffuser", useType);
                        tinyDB.putBoolean("imageset", false);
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                        Toast.makeText(getApplicationContext(), "Your details is successfully added to database!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error has occurred while uploading profile", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }
}
