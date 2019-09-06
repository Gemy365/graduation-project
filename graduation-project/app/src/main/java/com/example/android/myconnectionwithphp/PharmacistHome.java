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
import android.widget.Toast;

import com.example.android.myconnectionwithphp.Common.Common;
import com.github.clans.fab.FloatingActionButton;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PharmacistHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String UserNamePharmacist;
    String PharmacyID;

    TextView UserName;
    TextView Phone;
    TextView Address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserName = (TextView) findViewById(R.id.pharmacist_username);
        Phone = (TextView) findViewById(R.id.pharmacist_phone);
        Address = (TextView) findViewById(R.id.pharmacist_address);

        if (Common.isConnectedToInternet(PharmacistHome.this)) {
            // When Click Of Fab Button.
            FloatingActionButton UpdateDrugs = (FloatingActionButton) findViewById(R.id.update_drugs);
            UpdateDrugs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get From putExtra
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        // Get our extras from our intent.
                        final String username = extras.getString("userName");

                        // Goto ReadPrescriptionsFromDoctor Activity.
                        Intent intent = new Intent(PharmacistHome.this, AllDrugsForPharmacy.class);
                        intent.putExtra("userName", username);
                        intent.putExtra("pharmacyID", PharmacyID);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });

            // When Click Of Fab Button.
            FloatingActionButton StockDrugs = (FloatingActionButton) findViewById(R.id.stock_drugs);
            StockDrugs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();

                    // Get our extras from our intent.
                    final String username = extras.getString("userName");

                    // Goto ProfilePatient Activity.
                    Intent intent = new Intent(PharmacistHome.this, ListOfDrugsPharmacy.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("pharmacyID", PharmacyID);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            // When Click Of Fab Button.
            FloatingActionButton MinimumDrugsQuantity = (FloatingActionButton) findViewById(R.id.minimum_drugs_quantity);
            MinimumDrugsQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();

                    // Get our extras from our intent.
                    final String username = extras.getString("userName");

                    // Goto ProfilePatient Activity.
                    Intent intent = new Intent(PharmacistHome.this, LimitOfDrugsPharmacy.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("pharmacyID", PharmacyID);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            // When Click Of Fab Button.
            FloatingActionButton ListOrder = (FloatingActionButton) findViewById(R.id.list_order);
            ListOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = getIntent().getExtras();

                    // Get our extras from our intent.
                    final String username = extras.getString("userName");

                    // Goto ProfilePatient Activity.
                    Intent intent = new Intent(PharmacistHome.this, ListOfOrderPharmacy.class);
                    intent.putExtra("userName", username);
                    intent.putExtra("pharmacyID", PharmacyID);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

        } else
            // If Not Available.
            Toast.makeText(PharmacistHome.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();

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
            UserNamePharmacist = extras.getString("userName");

            String type = "pharmacy_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(PharmacistHome.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                  Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();
                    loadPharmacyData(Response);
                }
            };
            backgroundWorker.execute(type, UserNamePharmacist);
        }

    }

    private void loadPharmacyData(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("pharmacyData");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jO = jsonArray.getJSONObject(i);

                PharmacyID = jO.getString("pharmacy_ID");
                String pharmacy_username = jO.getString("pharmacy_Name");
                String pharmacy_password = jO.getString("pharmacy_Password");
                String pharmacy_address = jO.getString("pharmacy_Address");
                String pharmacy_phone = jO.getString("pharmacy_Phone");

                UserName.setText(pharmacy_username);
                Phone.setText("0" + pharmacy_phone);
                Address.setText(pharmacy_address);
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pharmacist_home, menu);
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

        if (id == R.id.all_drugs_for_pharmacy) {
            // Get From putExtra
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                // Unbundle the username.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PharmacistHome.this, AllDrugsForPharmacy.class);
                intent.putExtra("userName", username);
                intent.putExtra("pharmacyID", PharmacyID);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            // Handle the camera action
        } else if (id == R.id.list_of_drugs_pharmacy) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PharmacistHome.this, ListOfDrugsPharmacy.class);
                intent.putExtra("userName", username);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        } else if (id == R.id.limit_of_drugs_pharmacy) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                // Get our extras from our intent.
                final String username = extras.getString("userName");

                Intent intent = new Intent(PharmacistHome.this, LimitOfDrugsPharmacy.class);
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
