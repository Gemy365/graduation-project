package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.myconnectionwithphp.Common.Common;
import com.github.clans.fab.FloatingActionButton;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String UserNameDoc;

    TextView UserName;
    TextView Email;
    TextView Phone;
    TextView Address;
    TextView FullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserName = (TextView) findViewById(R.id.doctor_username);
        Email = (TextView) findViewById(R.id.doctor_email);
        Phone = (TextView) findViewById(R.id.doctor_phone);
        Address = (TextView) findViewById(R.id.doctor_address);
        FullName = (TextView) findViewById(R.id.doctor_fullname);

        if (Common.isConnectedToInternet(DoctorHome.this)) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.write_prescription);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get From putExtra
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        // Get our extras from our intent.
                        // Unbundle the username.
                        final String username = extras.getString("userName");

                        // Unbundle the password.
//                    final String password = extras.getString("password");

                        Intent intent = new Intent(DoctorHome.this, WritePrescriptionToPatient.class);
                        intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });

            FloatingActionButton SavedPrescription = (FloatingActionButton) findViewById(R.id.saved_doc_prescription);
            SavedPrescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();

                    // Get our extras from our intent.
                    // Unbundle the username.
                    final String username = extras.getString("userName");

                    // Unbundle the password.
//                    final String password = extras.getString("password");

                    Intent intent = new Intent(DoctorHome.this, SavedPrescriptionDoc.class);
                    intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            FloatingActionButton Profile = (FloatingActionButton) findViewById(R.id.profile);
            Profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();

                    // Get our extras from our intent.
                    // Unbundle the username.
                    final String username = extras.getString("userName");

                    // Unbundle the password.
//                    final String password = extras.getString("password");

                    Intent intent = new Intent(DoctorHome.this, ProfileDoctor.class);
                    intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else
            // If Not Available.
            Toast.makeText(DoctorHome.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNameDoc = extras.getString("userName");

            String type = "doc_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(DoctorHome.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadDocData(Response);
                }
            };
            backgroundWorker.execute(type, UserNameDoc);
        }

    }

    private void loadDocData(String response) {
        try {
            Log.d("myTag", response);

            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("doctorData");

            JSONObject jO = jsonArray.getJSONObject(0);

            String doctor_id = jO.getString("Doc_ID");
            String doctor_username = jO.getString("Doc_Name");
            String doctor_password = jO.getString("Doc_Password");
            String doctor_email = jO.getString("Doc_Email");
            String doctor_phone = jO.getString("Doc_Phone");
            String doctor_address = jO.getString("Doc_Address");
            String doctor_idCard = jO.getString("Doc_IDCard");
            String doctor_fullname = jO.getString("Doc_FullName");

            UserName.setText(doctor_username);
            Email.setText(doctor_email);
            Phone.setText("0" + doctor_phone);
            Address.setText(doctor_address);
            FullName.setText(doctor_fullname);


        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    // navigator bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_info) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                // Unbundle the username.
                final String username = extras.getString("userName");

                // Unbundle the password.
//                    final String password = extras.getString("password");

                Intent intent = new Intent(DoctorHome.this, ProfileDoctor.class);
                intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else if (id == R.id.nav_write_prescription) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                // Unbundle the username.
                final String username = extras.getString("userName");

                // Unbundle the password.
//                    final String password = extras.getString("password");

                Intent intent = new Intent(DoctorHome.this, WritePrescriptionToPatient.class);
                intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else if (id == R.id.nav_saved_prescription) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                // Unbundle the username.
                final String username = extras.getString("userName");

                // Unbundle the password.
//                    final String password = extras.getString("password");

                Intent intent = new Intent(DoctorHome.this, SavedPrescriptionDoc.class);
                intent.putExtra("userName", username);
//                    intent.putExtra("password", password);
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
