package com.cybertech.cliniccare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewAppointment extends AppCompatActivity {

    private RecyclerView bookrecyclerView;
    private TinyDB tinyDB;
    private StudentModel studentModel;
    Query firebaseSearchQuery = null;
    DatabaseReference mDatabase;
    Context ctx;
    String personelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        bookrecyclerView = (RecyclerView) findViewById(R.id.book_Recyclerview);
        bookrecyclerView.setHasFixedSize(true);
        bookrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic").child("books");
        mDatabase.keepSynced(true);

        tinyDB = new TinyDB(this);
        personelName = tinyDB.getString("staffname");
        //  studentModel = tinyDB.getObject("studentmodel", StudentModel.class);
        firebaseUserSearch();
    }

    private void firebaseUserSearch() {
        //  Toast.makeText(ViewAppointment.this, "Searching...", Toast.LENGTH_LONG).show(); studentmatric
        firebaseSearchQuery = mDatabase.orderByChild("flag");

        FirebaseRecyclerAdapter<BookingModel, ViewAppointment.Holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookingModel, ViewAppointment.Holder>(
                BookingModel.class,
                R.layout.booking_row,
                ViewAppointment.Holder.class,
                firebaseSearchQuery
        ) {

            @Override
            public ViewAppointment.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.booking_row, parent, false);
                return new ViewAppointment.Holder(itemView);
            }

            @Override
            protected void populateViewHolder(ViewAppointment.Holder viewHolder, final BookingModel model, int position) {

                ctx = getApplicationContext();
                viewHolder.setAppointments(model);


                viewHolder.cardLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDetails(model);
                    }
                });

            }
        };

        bookrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public void showDetails(final BookingModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAppointment.this);
        builder.setTitle("Appointment by: " + model.getStudent());
        builder.setMessage("Message: " + model.getMessage() + "\n sent at: " + model.getTime());
        builder.setCancelable(true);
        builder.setNegativeButton("Busy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String current_time = new SimpleDateFormat("hh:mm:ss a dd-MM-yyyy").format(new Date());
                mDatabase.child(model.getKey()).child("personel").setValue(personelName);
                mDatabase.child(model.getKey()).child("response").setValue(current_time);
                mDatabase.child(model.getKey()).child("responsetime").setValue("Busy please try again later! ").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ViewAppointment.this, "Appointment rejected for now!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewAppointment.this, "Error rejecting Appointment!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ViewAppointment.this, SetAppointmentTime.class));
                finish();
            }
        });
        builder.show();
    }

    public class Holder extends RecyclerView.ViewHolder {
        View view;
        TextView name_TV, complain_TV, _dateTV;
        CardView cardLayout;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;
            complain_TV = (TextView) view.findViewById(R.id.booking_complain_row);
            name_TV = (TextView) view.findViewById(R.id.book_name_row);
            _dateTV = (TextView) view.findViewById(R.id.booking_row_date);
            cardLayout = (CardView) view.findViewById(R.id.bookingCard);
        }

        public void setAppointments(BookingModel model) {
            complain_TV.setText(model.getMessage());
            name_TV.setText(model.getStudent());
            _dateTV.setText(model.getTime());
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewAppointment.this, WorkersProfile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
