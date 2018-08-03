package com.cybertech.cliniccare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    View navHeader;
    TinyDB tinyDB;
    StudentModel studentModel;
    private CircleImageView profileIcon;
    private TextView student_nameTV;
    private TextView studentMatricTV;
    private TextView st_home_nameTV, st_bloodTV, st_XrayTV, st_lastVisit;
    private CircleImageView profileIcon_home;
    private TextView dept_TV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tinyDB = new TinyDB(this);
        studentModel = tinyDB.getObject("studentmodel", StudentModel.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Home.this, BookAppointment.class).putExtra("st_back", 2));
                finish();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view_student_home);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        profileIcon = (CircleImageView) navHeader.findViewById(R.id.student_profile_image);
        student_nameTV = (TextView) navHeader.findViewById(R.id.student_name);
        studentMatricTV = (TextView) navHeader.findViewById(R.id.student_matricNO);

        profileIcon_home = (CircleImageView) findViewById(R.id.st_home_picture);
        st_home_nameTV = (TextView) findViewById(R.id.st_home_nameTag);
        st_bloodTV = (TextView) findViewById(R.id.st_home_bloodG_result);
        st_XrayTV = (TextView) findViewById(R.id.st_home_DisplayXRAY_result);
        dept_TV = (TextView) findViewById(R.id.st_home_dept);
        st_lastVisit = (TextView) findViewById(R.id.st_home_visits_Date);

        student_nameTV.setText(studentModel.getName().toUpperCase());
        studentMatricTV.setText(studentModel.getMatric());

        st_home_nameTV.setText(studentModel.getName().toUpperCase());
        st_bloodTV.setText(studentModel.getStudentHealthModel().getBloogroup());
        st_XrayTV.setText(studentModel.getSchool().toUpperCase());
        dept_TV.setText(studentModel.getDepartment().toUpperCase());
        st_lastVisit.setText(studentModel.getLevel());

        Glide.with(this)
                .load(studentModel.getStudentIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileIcon);

        Glide.with(this)
                .load(studentModel.getStudentIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileIcon_home);


    }

    @Override
    public void onBackPressed() {
        boolean answer = false;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Student_Home.this, Student_Login.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.setMessage("Do you want to really logout of this App?");
            builder.show();
            builder.setTitle("Exit");
            // super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student__home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_medical_record) {
            startActivity(new Intent(Student_Home.this, TimeLines.class).putExtra("st_back", 1));
            finish();

        } else if (id == R.id.nav_book_appoint) {
            startActivity(new Intent(Student_Home.this, BookAppointment.class).putExtra("st_back", 2));
            finish();

        } else if (id == R.id.nav_my_appoint) {
            startActivity(new Intent(Student_Home.this, MyAppointments.class));
            finish();

        } else if (id == R.id.nav_logout_st) {
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
