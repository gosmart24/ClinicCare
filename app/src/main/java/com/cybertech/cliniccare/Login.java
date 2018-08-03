package com.cybertech.cliniccare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username_Ed, pas_Ed;
    TinyDB tinyDB;
    String storePass, storeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tinyDB = new TinyDB(this);
        storePass = tinyDB.getString("userpass");
        storeUser = tinyDB.getString("userids");


        username_Ed = (EditText) findViewById(R.id.user_login_id);
        pas_Ed = (EditText) findViewById(R.id.pass_login_id);

    }

    public void onSignIn(View view) {
        if (TextUtils.isEmpty(username_Ed.getText().toString().trim()) || TextUtils.isEmpty(pas_Ed.getText().toString().trim())) {
            if (TextUtils.isEmpty(username_Ed.getText().toString().trim())) {
                username_Ed.setError(getString(R.string.inputerror));
            } else if (TextUtils.isEmpty(pas_Ed.getText().toString().trim()))
                pas_Ed.setError(getString(R.string.inputerror));
        } else {
            String username = username_Ed.getText().toString();
            String pass = pas_Ed.getText().toString();
            if (storeUser.equals(username)) {
                if (storePass.equals(pass)) {
                    //Login Successful do more here.
                    startActivity(new Intent(Login.this, WorkersProfile.class));
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Login.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Login.this, "invalid Username!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", null);
        builder.setMessage("Do you want to really Exit this App?");
        builder.setTitle("Exit");
        builder.show();

        //super.onBackPressed();
    }

    public void onRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }
}
