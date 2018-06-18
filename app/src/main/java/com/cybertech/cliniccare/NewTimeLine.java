package com.cybertech.cliniccare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewTimeLine extends AppCompatActivity {

    TimelineModel model;
    TinyDB tinyDB;
    StudentModel studentModel;
    List<TimelineModel> timelineModels;
    EditText complian_ed, pres_ed;
    String personel;
    String key;
    DatabaseReference mReference;
   // ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_line);

        tinyDB = new TinyDB(this);
        studentModel = tinyDB.getObject("studentmodel", StudentModel.class);
        timelineModels = studentModel.getTimelineModels();
        key = studentModel.getKey();
        mReference = FirebaseDatabase.getInstance().getReference("Clinic").child("Students").child(key);


        complian_ed = (EditText) findViewById(R.id.complain_ET);
        pres_ed = (EditText) findViewById(R.id.prescription_Et);

    }

    public void onCreateNewTimeLine(View view) {

        model = new TimelineModel();
        personel = tinyDB.getString("staffname");
        String current_time = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy").format(new Date());

        if (TextUtils.isEmpty(complian_ed.getText().toString()) || TextUtils.isEmpty(pres_ed.getText().toString())) {
            if (TextUtils.isEmpty(complian_ed.getText().toString())) {
                complian_ed.setError("Field cannot be empty!");
            } else {
                pres_ed.setError("Field cannot be empty!");
            }
        } else {
           // dialog = ProgressDialog.show(NewTimeLine.this, null, "Inserting record...", true, true);
            model.setComplain(complian_ed.getText().toString());
            model.setVisitdate(current_time);
            model.setPriscriptions(pres_ed.getText().toString());
            model.setPersonal(personel.toLowerCase());
            timelineModels.add(model);

            mReference.child("studentHealthModel").child("lasttreatment").setValue(complian_ed.getText().toString());
            mReference.child("studentHealthModel").child("lastvisit").setValue(current_time);
            mReference.child("timelineModels").setValue(timelineModels).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        Toast.makeText(NewTimeLine.this, "Record added successfully!", Toast.LENGTH_SHORT).show();
                       // dialog.dismiss();
                    } else {
                        Toast.makeText(NewTimeLine.this, "Unable to insert record Process ended with error!", Toast.LENGTH_SHORT).show();
                      //  dialog.dismiss();
                    }
                }
            });
            Toast.makeText(NewTimeLine.this, "Inserting record...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NewTimeLine.this, WorkersProfile.class));
            finish();
        }

    }
}
