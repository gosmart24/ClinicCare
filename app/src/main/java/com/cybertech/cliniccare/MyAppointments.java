package com.cybertech.cliniccare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyAppointments extends AppCompatActivity {

    private RecyclerView bookrecyclerView;
    private TinyDB tinyDB;
    private StudentModel studentModel;
    Query firebaseSearchQuery = null;
    DatabaseReference mDatabase;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);


        bookrecyclerView = (RecyclerView) findViewById(R.id.myAppointment_Recyclerview);
        bookrecyclerView.setHasFixedSize(true);
        bookrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic").child("books");
        mDatabase.keepSynced(true);

        tinyDB = new TinyDB(this);

        studentModel = tinyDB.getObject("studentmodel", StudentModel.class);
        String searchText = studentModel.getMatric();
        firebaseUserSearch(searchText);
    }


    private void firebaseUserSearch(String search) {
        //  Toast.makeText(ViewAppointment.this, "Searching...", Toast.LENGTH_LONG).show();
        //firebaseSearchQuery = mDatabase.orderByChild("flag");
        firebaseSearchQuery = mDatabase.orderByChild("studentmatric").startAt(search).endAt(search + "\uf8ff");

        FirebaseRecyclerAdapter<BookingModel, MyAppointments.Holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BookingModel, MyAppointments.Holder>(
                BookingModel.class,
                R.layout.booking_row,
                MyAppointments.Holder.class,
                firebaseSearchQuery
        ) {

            @Override
            public MyAppointments.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.booking_row, parent, false);
                return new MyAppointments.Holder(itemView);
            }

            @Override
            protected void populateViewHolder(MyAppointments.Holder viewHolder, final BookingModel model, int position) {

                ctx = getApplicationContext();
                viewHolder.setAppointments(model);
                Log.i("TAG", model.toString());


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

    public void showDetails(BookingModel model) {
        String responesMSG = (model.getResponse().equals("0")) ? "pending" : model.getResponse();
        String responesTime = (model.getResponsetime().equals("0")) ? "pending" : model.getResponse();
        String personel = (model.getPersonel().equals("0")) ? "pending" : model.getPersonel();
        AlertDialog.Builder builder = new AlertDialog.Builder(MyAppointments.this);
        builder.setTitle("Appointment");
        builder.setMessage("Message: " + model.getMessage() + "\nResponse: " + responesMSG + "\nPersonel: " + personel + "\nTime: " + responesTime);
        builder.setCancelable(true);
        builder.setNegativeButton("Close", null);
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
        startActivity(new Intent(MyAppointments.this, Student_Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
