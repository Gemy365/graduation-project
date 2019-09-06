package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionButton;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // String To Store UserName Of Patient.
    String UserNamePatient;

    // Init TextViews.
    TextView UserName, Email, Phone, Address, FullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // GetViews By IDs.
        UserName = (TextView) findViewById(R.id.patient_username);
        Email = (TextView) findViewById(R.id.patient_email);
        Phone = (TextView) findViewById(R.id.patient_phone);
        Address = (TextView) findViewById(R.id.patient_address);
        FullName = (TextView) findViewById(R.id.patient_fullname);

        // When Click Of Fab Button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.read_prescription);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get From putExtra
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    // Get our extras from our intent.
                    final String username = extras.getString("userName");

                    // Goto ReadPrescriptionsFromDoctor Activity.
                    Intent intent = new Intent(PatientHome.this, ReadPrescriptionsFromDoctor.class);
                    intent.putExtra("userName", username);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        // When Click Of Fab Button.
        FloatingActionButton Profile = (FloatingActionButton) findViewById(R.id.profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();

                // Get our extras from our intent.
                final String username = extras.getString("userName");

                // Goto ProfilePatient Activity.
                Intent intent = new Intent(PatientHome.this, ProfilePatient.class);
                intent.putExtra("userName", username);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // Internal Widget Of JAVA.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Internal Widget Of JAVA.
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // GetIntent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            UserNamePatient = extras.getString("userName");

            String type = "patient_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(PatientHome.this) {
                protected void onPostExecute(String Response) {
                    // Call loadProfilePatient Method.
                    loadProfilePatient(Response);

                }
            };
            // Execute With Params.
            backgroundWorker.execute(type, UserNamePatient);
        }

    }

    // loadProfilePatient Method.
    private void loadProfilePatient(String response) {
        try {
            // Json Array Of Response Of Server.
            JSONArray ResponseJsonArray = new JSONArray(response);

            // Json Object Of Response Of Server.
            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            // Json Array Of Response Of Server.
            JSONArray jsonArray = jsonObject.getJSONArray("patientData");

            // Json Object Of Response Of Server.
            JSONObject jO = jsonArray.getJSONObject(0);

            // Get Data From JSON Response Of Server By KEYS.
            String patient_id = jO.getString("Patient_ID");
            String patient_username = jO.getString("Patient_UserName");
            String patient_password = jO.getString("Patient_Password");
            String patient_email = jO.getString("Patient_Email");
            String patient_phone = jO.getString("Patient_Phone");
            String patient_address = jO.getString("Patient_Address");
            String patient_fullname = jO.getString("Patient_FullName");

            // Store Them Into Fields.
            UserName.setText(patient_username);
            Email.setText(patient_email);
            Phone.setText("0" + patient_phone);
            Address.setText(patient_address);
            FullName.setText(patient_fullname);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Internal Widget Of JAVA.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Internal Widget Of JAVA.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_home, menu);
        return true;
    }

    // Internal Widget Of JAVA.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    // Check Menu In The Side Of Screen.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_info) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PatientHome.this, ProfilePatient.class);
                intent.putExtra("userName", username);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            // Handle the camera action
        } else if (id == R.id.nav_read_prescription) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PatientHome.this, ReadPrescriptionsFromDoctor.class);
                intent.putExtra("userName", username);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else if (id == R.id.nav_saved_prescription) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PatientHome.this, SavedPrescriptionPatient.class);
                intent.putExtra("userName", username);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    Snackbar mSnackbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        final LinearLayout layout = findViewById(R.id.layout_main);
//        mSnackbar = Snackbar.make(layout, R.string.press_back_again, Snackbar.LENGTH_SHORT);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mSnackbar.isShown()) {
//            super.onBackPressed();
//        } else {
//            mSnackbar.show();
//        }
//    }
}
