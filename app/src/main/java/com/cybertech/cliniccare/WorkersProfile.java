package com.cybertech.cliniccare;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;


public class WorkersProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int IMAGE_CODE = 120;

    private TinyDB tinyDB;
    private Uri url;
    NavigationView navigationView;
    View navHeader;

    CircleImageView profileIcon;
    TextView staff_nameTV, staff_typeTV, tap_hint;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view_staff);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);


        profileIcon = (CircleImageView) navHeader.findViewById(R.id.staff_image_View);
        staff_nameTV = (TextView) navHeader.findViewById(R.id.staff_name_View);
        staff_typeTV = (TextView) navHeader.findViewById(R.id.staff_userType);
        tap_hint = (TextView) navHeader.findViewById(R.id.tap_hint);


        tinyDB = new TinyDB(this);

        staffName = tinyDB.getString("staffname");
        stafftype = tinyDB.getString("staffuser");
        boolean isImageSet = tinyDB.getBoolean("imageset");

        if (stafftype.equalsIgnoreCase("doctor")) {
            FirebaseMessaging.getInstance().subscribeToTopic("doctor");

        } else if (stafftype.equalsIgnoreCase("Pharmacist")) {
            FirebaseMessaging.getInstance().subscribeToTopic("Pharmacist");
        }
        // FirebaseInstanceId.getInstance().getToken();

        // Toast.makeText(WorkersProfile.this, "Name : " + staffName  + " USER : " + stafftype, Toast.LENGTH_LONG).show();
        if (isImageSet) {
            profileIcon.setImageURI(Uri.parse(tinyDB.getString("profileimage")));
            tap_hint.setVisibility(View.GONE);
            staff_nameTV.setText(staffName);
            staff_typeTV.setText(stafftype);
        } else {
            profileIcon.setImageResource(R.drawable.stethoscope);
            staff_nameTV.setText(staffName);
            staff_typeTV.setText(stafftype);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(WorkersProfile.this, Login.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", null);
            builder.setMessage("Do you want to really Logout?");
            builder.setTitle("Exit");
            builder.show();
            // super.onBackPressed();
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

    public void onViewAppointments(View view) {
        startActivity(new Intent(WorkersProfile.this, ViewAppointment.class));
        finish();
    }

    public void onSearchStudents(View view) {
        startActivity(new Intent(WorkersProfile.this, ViewStudentRecords.class));
        finish();
    }

}
