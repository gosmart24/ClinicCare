package com.cybertech.cliniccare;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ViewStudentRecords extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerView;
    private TinyDB tinyDB;
    DatabaseReference mDatabase;
    Query firebaseSearchQuery = null;
    SearchView searchView;
    MenuItem searchMenuitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_records);

        recyclerView = (RecyclerView) findViewById(R.id.base_recyclerview_StudentRecord);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic").child("Students");
        mDatabase.keepSynced(true);

        tinyDB = new TinyDB(this);
        String searchKey = tinyDB.getString("searchkey");


        firebaseUserSearch("");
    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(ViewStudentRecords.this, "Searching...", Toast.LENGTH_LONG).show();
        String key = tinyDB.getString("searchkey");
        firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        if (key.equals("name")) {
            firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        } else if (key.equals("id")) {
            firebaseSearchQuery = mDatabase.orderByChild("studentId").startAt(searchText).endAt(searchText + "\uf8ff");

        } else if (key.equals("matric")) {
            firebaseSearchQuery = mDatabase.orderByChild("matric").startAt(searchText).endAt(searchText + "\uf8ff");

        } else if (key.equals("depart")) {
            firebaseSearchQuery = mDatabase.orderByChild("department").startAt(searchText).endAt(searchText + "\uf8ff");

        }


        FirebaseRecyclerAdapter<StudentModel, ViewStudentRecords.Holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StudentModel, ViewStudentRecords.Holder>(
                StudentModel.class,
                R.layout.student_row_card,
                ViewStudentRecords.Holder.class,
                firebaseSearchQuery
        ) {

            @Override
            public ViewStudentRecords.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_row_card, parent, false);
                return new ViewStudentRecords.Holder(itemView);
            }

            @Override
            protected void populateViewHolder(ViewStudentRecords.Holder viewHolder, final StudentModel model, int position) {

                viewHolder.setStudentDetails(getApplicationContext(), model);

                viewHolder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent st_detail = new Intent(getApplicationContext(), StudentDetails.class);
                        // st_detail.putExtra("model", model.toString());
                        tinyDB.putObject("studentmodel", model);
                        ViewStudentRecords.this.startActivity(st_detail);
                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        firebaseUserSearch(query);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workers_profile, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuitem = menu.findItem(R.id._searchbar);
        searchView = (SearchView) searchMenuitem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Name");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id._searchbar) {
            // open search bar from here.
            // Toast.makeText(WorkersProfile.this, "open search bar from here", Toast.LENGTH_LONG).show();
        }
        return true;

    }


    public static class Holder extends RecyclerView.ViewHolder {

        TextView name_TV, matric_TV, level_TV;
        View view;
        LinearLayout card;
        ImageView st_Icon;

        public Holder(View itemView) {
            super(itemView);
            view = itemView;
            name_TV = (TextView) view.findViewById(R.id.name_search);
            matric_TV = (TextView) view.findViewById(R.id.matric_search);
            level_TV = (TextView) view.findViewById(R.id.level_search);
            card = view.findViewById(R.id.student_card);
            st_Icon = view.findViewById(R.id.image_search);

        }

        public void setStudentDetails(Context context, StudentModel model) {
            name_TV.setText(model.getName().toUpperCase());
            matric_TV.setText("Matric No.: " + model.getMatric());
            level_TV.setText("Level: " + model.getLevel());
            Glide.with(context)
                    .load(model.getStudentIcon())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(st_Icon);

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewStudentRecords.this, WorkersProfile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}
