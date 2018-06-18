package com.cybertech.cliniccare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudent extends AppCompatActivity {

    private static final int IMAGE_CODE = 100;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private Uri url = null;
    private StorageReference storageReference;
    private CircleImageView profileIcon;
    private String st_name;
    private TextView st_matric_ED;
    private TextView st_Id_ED;
    private TextView st_name_ED;
    private String st_matricNo;
    private String st_Id;
    private String parentNo;
    private TextView st_ParentNo;
    private Spinner spinner_schools, spinner_depart, spinner_level;
    StudentModel studentModel;

    String current_time = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy").format(new Date());
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_student);

        profileIcon = (CircleImageView) findViewById(R.id.add_image);
        st_name_ED = (TextView) findViewById(R.id.add_st_name);
        st_Id_ED = (TextView) findViewById(R.id.add_st_id);
        st_matric_ED = (TextView) findViewById(R.id.add_matricNo);
        st_ParentNo = (TextView) findViewById(R.id.add_parentNo);
        spinner_level = (Spinner) findViewById(R.id.add_spinner_level);
        spinner_depart = (Spinner) findViewById(R.id.add_spinner_depart);
        spinner_schools = (Spinner) findViewById(R.id.add_spinner_schools);


        databaseReference = FirebaseDatabase.getInstance().getReference("Clinic").child("Students");
        storageReference = FirebaseStorage.getInstance().getReference().child("images");

    }

    public void onSubmit(View view) {
        st_name = st_name_ED.getText().toString().trim().toLowerCase();
        st_Id = st_Id_ED.getText().toString().trim().toLowerCase();
        st_matricNo = st_matric_ED.getText().toString().trim().toLowerCase();
        parentNo = st_ParentNo.getText().toString().trim();
        String level = spinner_level.getSelectedItem().toString();
        String department = spinner_depart.getSelectedItem().toString();
        String school = spinner_schools.getSelectedItem().toString();

        studentModel = new StudentModel();

        studentModel.setName(st_name);
        studentModel.setStudentId(st_Id);
        studentModel.setMatric(st_matricNo);
        studentModel.setParentPhone(parentNo);
        studentModel.setLevel(level);
        studentModel.setDepartment(department);
        studentModel.setSchool(school);

        studentModel.setStudentHealthModel(sampleHealthDatails());
        studentModel.setTimelineModels(timelineModelList());

        if (TextUtils.isEmpty(st_name) || TextUtils.isEmpty(st_Id) || TextUtils.isEmpty(st_matricNo) || TextUtils.isEmpty(parentNo)) {
            Toast.makeText(getApplicationContext(), "Required fields cannot be empty!", Toast.LENGTH_SHORT).show();

        } else {
            uploadImage(url);

        }


    }

    public List<TimelineModel> timelineModelList() {
        List<TimelineModel> modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TimelineModel model = new TimelineModel();
            String current_time = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy").format(new Date());
            model.setComplain("feeling cool and vomiting");
            model.setPersonal("Doctor");
            model.setPriscriptions("get fever tablets for now.");
            model.setVisitdate(current_time);
            modelList.add(model);
        }

        return modelList;
    }

    public void getImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }

    public StudentHealthModel sampleHealthDatails() {
        StudentHealthModel model = new StudentHealthModel();
        String current_time = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy").format(new Date());
        int siz;
      /*  if (studentModel.getTimelineModels().size() >= 1) {
            siz = studentModel.getTimelineModels().size();
            model.setLastvisit(studentModel.getTimelineModels().get(siz - 1).getVisitdate());

        } else {
            model.setLastvisit(current_time);
        } */
        model.setLastvisit(current_time);
        model.setBloogroup("AB Rh-");
        model.setLasttreatment("maleria");
        model.setXrayresult("Normal");
        return model;
    }

    public static String randomName() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            url = data.getData();
            //uploadImage(url);
            profileIcon.setImageURI(url);
        }
    }

    public void uploadImage(Uri uriImag) {
        if (uriImag != null) {
            dialog = ProgressDialog.show(AddStudent.this, null, "Inserting record...", true, true);

            final DatabaseReference ref = databaseReference.push();
            StorageReference storage = storageReference.child(randomName());
            storage.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadURL = taskSnapshot.getDownloadUrl();
                    studentModel.setStudentIcon(downloadURL.toString());
                    studentModel.setKey(ref.getKey());
                    ref.setValue(studentModel).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error has occurred with on uploading image with this Message : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                startActivity(new Intent(AddStudent.this, WorkersProfile.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "Your Student details successfully added to database!", Toast.LENGTH_LONG).show();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error has occurred while uploading profile picture", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });


        } else {
            Toast.makeText(this, "Please select a profile Image!", Toast.LENGTH_LONG).show();
        }
    }
}
