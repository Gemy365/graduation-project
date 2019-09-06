package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class AllDrugsForPharmacy extends AppCompatActivity {

    ArrayList<String> AllMedicinesAL;
    SearchableSpinner SpnrAllMedicines;

    MaterialEditText AddQuantity;

    FButton SetDrug;

    String UserNamePharmacy;
    String PharmacyID;

    String DrugWorldID;
    String DrugWorldName;

    String DrugID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drugs_for_pharmacy);

        AllMedicinesAL = new ArrayList<>();
        SpnrAllMedicines = (SearchableSpinner) findViewById(R.id.pharmacy_all_drugs);

        AddQuantity = (MaterialEditText) findViewById(R.id.add_quantity);

        SetDrug = (FButton) findViewById(R.id.btn_set_drug);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            UserNamePharmacy = extras.getString("userName");
            PharmacyID = extras.getString("pharmacyID");

            String type = "pharmacy_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(AllDrugsForPharmacy.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadSpinnerMedicines(Response);
//                    loadPrescription(Response);
                }
            };
            backgroundWorker.execute(type, UserNamePharmacy);
        }


        SpnrAllMedicines.setTitle("Select Medicine");
        SpnrAllMedicines.setPositiveButton("CANCEL");
        SpnrAllMedicines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String StringSelectedMedicines =
                        SpnrAllMedicines.getSelectedItem().toString();

                String[] separated = StringSelectedMedicines.split("-");
                DrugID = separated[0];

                //                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        SetDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "update_quantity";

                @SuppressLint("StaticFieldLeak")
                BackgroundWorker backgroundWorker = new BackgroundWorker(AllDrugsForPharmacy.this) {
//                    protected void onPostExecute(String Response) {
//                        Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
//                        Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
//                        Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
//                        Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
//                        Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
//                        Response = Response.replaceAll("&amp;", "");         // Remove any & char.
//
////                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();
//
//                        loadSpinnerMedicines(Response);
////                    loadPrescription(Response);
//                    }
                };
                backgroundWorker.execute(type, PharmacyID, DrugID, AddQuantity.getText().toString());

                AddQuantity.setText("");
            }
        });
    }

    private void loadSpinnerMedicines(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("Drug_world");

            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject jO = jsonArray.getJSONObject(count);

                DrugWorldID = jO.getString("drug_world_ID");
                DrugWorldName = jO.getString("drug_world_Name");

                AllMedicinesAL.add(DrugWorldID + "-" + DrugWorldName);
            }

            SpnrAllMedicines.setAdapter(new ArrayAdapter<String>(AllDrugsForPharmacy.this,
                    R.layout.prescription_form, R.id.rowTextView, AllMedicinesAL));

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}
