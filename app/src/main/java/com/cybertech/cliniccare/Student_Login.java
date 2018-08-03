package com.cybertech.cliniccare;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Student_Login extends AppCompatActivity {

    EditText student_ID_ED;
    public static TinyDB tinyDB;
    public static String input = "";
    public static StudentModel studentModel;
    DatabaseReference mDatabase;
    Query firebaseSearchQuery = null;
    StudentModel model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__login);

        tinyDB = new TinyDB(this);

        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic").child("Students");
        mDatabase.keepSynced(true);

        student_ID_ED = (EditText) findViewById(R.id.matric_no_Login);


    }

    public void onSignInStudent(View view) {
        if (!TextUtils.isEmpty(student_ID_ED.getText().toString().trim())) {
            input = student_ID_ED.getText().toString().trim();
            firebaseUser firebaseUser = new firebaseUser();
            firebaseUser.execute();

        } else {
            Toast.makeText(Student_Login.this, "Required field cannot be empty!", Toast.LENGTH_SHORT).show();
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

    }

    public class firebaseUser extends AsyncTask<String, Void, Boolean> {
        ProgressDialog progressDialog;
        StudentModel student;
        public boolean found = false;

        @Override
        protected Boolean doInBackground(String... strings) {
            progressDialog.setMessage("verifying matric number...");
            firebaseSearchQuery = mDatabase;
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("TAG", " DATASNAPTSHOT: " + dataSnapshot.toString());
                    // studentModel = dataSnapshot.
                    int count = 0;

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.i("TAG", "Matric: " + data.child("matric"));

                        if (input.equalsIgnoreCase(data.child("matric").getValue(String.class))) {
                            student = data.getValue(StudentModel.class);

                            tinyDB.putObject("studentmodel", student);
                            tinyDB.putBoolean("isloaded", true);
                            startActivity(new Intent(Student_Login.this, Student_Home.class));
                            finish();
                            break;
                            //  return;
                        }

                        count++;
                        Log.i("TAG", "Count: " + count);
                        Log.i("TAG", "Count: " + dataSnapshot.getChildrenCount());
                    }
                    if (count == (dataSnapshot.getChildrenCount())) {
                        Toast.makeText(Student_Login.this, "Sorry unverify matric number!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Student_Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            };
            firebaseSearchQuery.addListenerForSingleValueEvent(listener);

            return found;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Student_Login.this, "Login Process", "Connecting to server", true, true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            //  Log.i("TAG", "found value: " + ((String.valueOf(found))));
            //  Toast.makeText(Student_Login.this, "Sorry unverify matric number!", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aBoolean);
        }

    }
}
