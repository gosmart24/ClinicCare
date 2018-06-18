package com.cybertech.cliniccare;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDetails extends AppCompatActivity {

    StudentHealthModel healthmodel;
    TextView nameTag_ed, bloodGroup_ed, x_ray_ed, treatment_ed, visit_ed;
    StudentModel model;
    TinyDB tinyDB;

    CircleImageView st_Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        tinyDB = new TinyDB(this);
        model = tinyDB.getObject("studentmodel", StudentModel.class);

        healthmodel = model.getStudentHealthModel();

        // initializzing views.
        nameTag_ed = (TextView) findViewById(R.id.nameTag);
        bloodGroup_ed = (TextView) findViewById(R.id.bloodG);
        x_ray_ed = (TextView) findViewById(R.id.x_ray_resultDisplay);
        treatment_ed = (TextView) findViewById(R.id.lastTreatment_display_st);
        visit_ed = (TextView) findViewById(R.id.visits);

        st_Icon = (CircleImageView) findViewById(R.id.st_image_details);

        // setting view values.
        nameTag_ed.setText(model.getName().toUpperCase());
        bloodGroup_ed.setText(healthmodel.getBloogroup());
        x_ray_ed.setText(healthmodel.getXrayresult());
        treatment_ed.setText(healthmodel.getLasttreatment());
        visit_ed.setText(healthmodel.getLastvisit());
        Glide.with(this).load(model.getStudentIcon()).into(st_Icon);


    }

    public void onOpenDialog_st(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.x_ray_resultDisplay:
                showDialog("X-ray Result", healthmodel,0);
                break;
            case R.id.lastTreatment_display_st:
                showDialog("Last Treatment", healthmodel,1);
                break;
            case R.id.history:
                startActivity(new Intent(StudentDetails.this, TimeLines.class));
                finish();
                break;
            case R.id.add_history:
                startActivity(new Intent(StudentDetails.this, NewTimeLine.class));
                finish();
                break;
        }
    }

    private void showDialog(String title, StudentHealthModel model,int flag) {
        // show details info about a student.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (flag == 0){
            builder.setMessage(healthmodel.getXrayresult());
        }else {
            builder.setMessage(healthmodel.getLasttreatment());
        }
        builder.setNegativeButton("Close", null);
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        // tinyDB.remove("studentmodel");
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        // tinyDB.remove("studentmodel");
        super.onStop();
    }
}
