package com.cybertech.cliniccare;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookAppointment extends AppCompatActivity {


    TimelineModel model;
    TinyDB tinyDB;
    StudentModel studentModel;
    EditText complian_ed, pres_ed;
    String personel;
    String key;
    BookingModel bookingModel;
    DatabaseReference mReference;
    private int st_back;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tinyDB = new TinyDB(this);
        studentModel = tinyDB.getObject("studentmodel", StudentModel.class);
        mReference = FirebaseDatabase.getInstance().getReference("Clinic").child("books");
        key = studentModel.getKey();
        complian_ed = (EditText) findViewById(R.id.complain_ET_Booking);
        bookingModel = new BookingModel();

        st_back = getIntent().getIntExtra("st_back", 0);
    }

    public void onBookAppointment(View view) {

        // personel = tinyDB.getString("staffname");
        String current_time = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy").format(new Date());

        if (TextUtils.isEmpty(complian_ed.getText().toString())) {
            Toast.makeText(BookAppointment.this, "Sorry required field cannot be empty!", Toast.LENGTH_SHORT).show();

        } else {
            DatabaseReference reference = mReference.push();
            dialog = ProgressDialog.show(BookAppointment.this, null, "Inserting record...", true, true);
            bookingModel.setMessage(complian_ed.getText().toString());
            bookingModel.setTime(current_time);
            bookingModel.setResponse("0");
            bookingModel.setResponsetime("0");
            bookingModel.setPersonel("0");
            bookingModel.setFlag("0");
            bookingModel.setConfirmation("0");
            bookingModel.setStudent(studentModel.getName());
            bookingModel.setStudentmatric(studentModel.getMatric());
            bookingModel.setKey(reference.getKey());
            reference.setValue(bookingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Response.Listener<String> successListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookAppointment.this);
                                builder.setTitle("Book Appointment")
                                        .setMessage("You have successfully booked an  Appointment!")
                                        .setCancelable(true)
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                BookAppointment.this.startActivity(new Intent(BookAppointment.this, Student_Home.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        };
                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookAppointment.this);
                                builder.setTitle("Book Appointment")
                                        .setMessage("Appointment broadcast failed please try again later!")
                                        .setCancelable(true)
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                BookAppointment.this.startActivity(new Intent(BookAppointment.this, Student_Home.class));
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        };
                        NotificationConnection connection = new NotificationConnection(complian_ed.getText().toString(), "New Appointment", "doctor", successListener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(BookAppointment.this);
                        queue.add(connection);


                        // Toast.makeText(BookAppointment.this, , Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(BookAppointment.this, "Unable to book appointment now please again later!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });
            //Toast.makeText(BookAppointment.this, "Inserting record...", Toast.LENGTH_SHORT).show();
            //  startActivity(new Intent(BookAppointment.this, WorkersProfile.class));
            //  finish();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookAppointment.this, Student_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
