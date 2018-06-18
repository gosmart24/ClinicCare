package com.cybertech.cliniccare;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class WorkersProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private static final int IMAGE_CODE = 120;
    SearchView searchView;
    MenuItem searchMenuitem;
    DatabaseReference mDatabase;
    Query firebaseSearchQuery = null;
    RecyclerView recyclerView;
    private TinyDB tinyDB;
    private Uri url;

    CircleImageView profileIcon;
    TextView staff_nameTV, staff_typeTV;
    private String staffName = "";
    private String stafftype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_workers_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference("Clinic").child("Students");
        mDatabase.keepSynced(true);

        recyclerView = (RecyclerView) findViewById(R.id.base_recyclerview_Doctors);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        profileIcon = (CircleImageView) findViewById(R.id.staff_image);
        staff_nameTV = (TextView) findViewById(R.id.staff_name);
        staff_typeTV = (TextView) findViewById(R.id.staff_Usertype);


        tinyDB = new TinyDB(this);

        staffName = tinyDB.getString("staffname");
        stafftype = tinyDB.getString("staffuser");
        boolean isImageSet = tinyDB.getBoolean("imageset");

        String searchKey = tinyDB.getString("searchkey");

        // Toast.makeText(WorkersProfile.this, "Name : " + staffName  + " USER : " + stafftype, Toast.LENGTH_LONG).show();


        // staff_nameTV.setText("SAMUEL ADAKOLE");
        // staff_typeTV.setText("DOCTOR");
        if (isImageSet) {
            profileIcon.setImageURI(Uri.parse(tinyDB.getString("profileimage")));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseUserSearch("");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // logout from here.
            System.exit(0);

        } else if (id == R.id.nav_search_key) {
            // select a search key from here.
            startActivity(new Intent(getApplicationContext(), SearchKey.class));

        } else if (id == R.id.nav_add_student) {
            // select a search key from here.
            startActivity(new Intent(getApplicationContext(), AddStudent.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            url = data.getData();
            //uploadImage(url);
            profileIcon.setImageURI(url);
            // profileIcon.

            tinyDB.putString("profileimage", url.toString());
            tinyDB.putBoolean("imageset", true);

        }
    }

    public void getImageProfile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(WorkersProfile.this, "Searching...", Toast.LENGTH_LONG).show();
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


        FirebaseRecyclerAdapter<StudentModel, Holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<StudentModel, Holder>(
                StudentModel.class,
                R.layout.student_row_card,
                Holder.class,
                firebaseSearchQuery
        ) {

            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_row_card, parent, false);
                return new Holder(itemView);
            }

            @Override
            protected void populateViewHolder(Holder viewHolder, final StudentModel model, int position) {

                viewHolder.setStudentDetails(getApplicationContext(), model);

                viewHolder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent st_detail = new Intent(getApplicationContext(), StudentDetails.class);
                        // st_detail.putExtra("model", model.toString());
                        tinyDB.putObject("studentmodel", model);
                        WorkersProfile.this.startActivity(st_detail);
                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
            Glide.with(context).load(model.getStudentIcon()).into(st_Icon);

        }

    }
}
