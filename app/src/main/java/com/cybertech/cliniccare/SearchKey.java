package com.cybertech.cliniccare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SearchKey extends AppCompatActivity {

    RadioGroup seachKeyGroup;
    RadioButton radioName, radioId, radioMatric, radioDepart;
    TinyDB tinyDB;
    String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_key);
        tinyDB = new TinyDB(this);

        seachKeyGroup = (RadioGroup) findViewById(R.id.radioGroupKey);
        radioName = (RadioButton) findViewById(R.id.radioName);
        radioId = (RadioButton) findViewById(R.id.radioIdNO);
        radioMatric = (RadioButton) findViewById(R.id.radioMatricNo);
        radioDepart = (RadioButton) findViewById(R.id.radioDepart);


        int id = seachKeyGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioName:
                tinyDB.putString("searchkey", "name");
                break;

            case R.id.radioIdNO:
                tinyDB.putString("searchkey", "id");
                break;

            case R.id.radioMatricNo:
                tinyDB.putString("searchkey", "matric");
                break;

            case R.id.radioDepart:
                tinyDB.putString("searchkey", "depart");
                break;

            default:
                tinyDB.putString("searchkey", "name");
                break;

        }

        searchKey = tinyDB.getString("searchkey");

        if (searchKey.equals("name")) {
            radioName.setChecked(true);
        } else if (searchKey.equals("id")) {
            radioId.setChecked(true);
        } else if (searchKey.equals("matric")) {
            radioMatric.setChecked(true);
        } else if (searchKey.equals("depart")) {
            radioDepart.setChecked(true);
        }

    }

    public void onsaveSearchKey(View view) {

        int id = seachKeyGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioName:
                tinyDB.putString("searchkey", "name");
                Toast.makeText(SearchKey.this, "name has been set as query key", Toast.LENGTH_LONG).show();
                break;

            case R.id.radioIdNO:
                tinyDB.putString("searchkey", "id");
                Toast.makeText(SearchKey.this, "student id has been set as query key", Toast.LENGTH_LONG).show();

                break;

            case R.id.radioMatricNo:
                tinyDB.putString("searchkey", "matric");
                Toast.makeText(SearchKey.this, "matric Number has been set as query key", Toast.LENGTH_LONG).show();

                break;

            case R.id.radioDepart:
                tinyDB.putString("searchkey", "depart");
                Toast.makeText(SearchKey.this, "depart has been set as query key", Toast.LENGTH_LONG).show();

                break;

        }
        startActivity(new Intent(SearchKey.this, WorkersProfile.class));
        finish();
    }
}
