package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedPrescriptionPatient extends AppCompatActivity {
    // ArrayList For ListViews.
    ArrayList<String> SavedPrescription;
    ListView listView;

    // Strings To Store Data Into Them.
    String PreID, DocName, PatientName, DatePre, DrugName, Quantity, Pharmacies = "", Status;

    String CollectionOfDrugNameQuantityPhrmacy = "", UserNamePatient, Prescription;

    // Init Int Values.
    int FirstDrugOfPatient = 0;
    int CountOfDrugs = 0;

    // String To Store All Drugs Into It.
    String AllDrugs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_prescription_patient);

        // GetViews By IDs.
        listView = (ListView) findViewById(R.id.list);
        SavedPrescription = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            UserNamePatient = extras.getString("userName");

            String type = "patient_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(SavedPrescriptionPatient.this) {
                protected void onPostExecute(String Response) {
                    // Call loadPrescription Method.
                    loadPrescription(Response);

                }
            };
            // Execute Params.
            backgroundWorker.execute(type, UserNamePatient);
        }
    }

    // When Call loadAllPrescriptions Method.
    private void loadPrescription(String response) {
        try {
            // Json Array Of Response Of Server.
            JSONArray ResponseJsonArray = new JSONArray(response);

            // Json Object Of Response Of Server.
            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            // Json Array Of Response Of Server.
            JSONArray jsonArray = jsonObject.getJSONArray("prescriptionDataPatient");

            // Loop To Get All Informations.
            for (int count = 0; count < jsonArray.length(); count++) {

                // Get Json Object By Its Index.
                JSONObject jO = jsonArray.getJSONObject(count);

                // Check If Json Object Has Prescription_ID.
                if (jsonArray.getJSONObject(count).has("Prescription_ID")) {
                    PreID = jO.getString("Prescription_ID");
                    DocName = jO.getString("DocName");
                    PatientName = jO.getString("Patient_Name");
                    DatePre = jO.getString("Date_Pre");
                    // Increase Count By 1.
                    count++;
                }

                // Loop To Check Json Object Not Equal Length Of Array & Has pharmacy_avalable Or drug_Data.
                // Loop To Get All Drugs & All Pharmacies Available.
                while (count != jsonArray.length() &&
                        (jsonArray.getJSONObject(count).has("pharmacy_avalable") ||
                                jsonArray.getJSONObject(count).has("drug_Data"))) {

                    // Check If Json Object Has drug_Data.
                    // If Statement Cause It One Drug In Once Time.
                    if (jsonArray.getJSONObject(count).has("drug_Data")) {
                        DrugName = jsonArray.getJSONObject(count).getString("drug_Data");
                        Quantity = jsonArray.getJSONObject(count).getString("quantity_demand");
                        Status = jsonArray.getJSONObject(count).getString("status");
                        // Increase Count By 1.
                        count++;
                    }

                    // Loop To Check Json Object Not Equal Length Of Array & Has pharmacy_avalable.
                    // Loop To Get All Pharmacies Available.
                    while (count != jsonArray.length() &&
                            jsonArray.getJSONObject(count).has("pharmacy_avalable")) {

                        // "0" Means  Patient Didn't Make Order For His Drug.
                        if (Status.equals("0")) {
                            Pharmacies += jsonArray.getJSONObject(count).getString("pharmacy_avalable");
                            Pharmacies += "\n";
                        }
                        // Increase Count By 1.
                        count++;
                    }

                    // Store All Data Into One String To Make All Of This Data Into One Item.
                    CollectionOfDrugNameQuantityPhrmacy += DrugName + "\n" + Quantity + "\n" + Pharmacies;
                    // Reset String Of Pharmacies.
                    Pharmacies = "";
                }

                // Store All Data Into One String.
                Prescription = PreID + "\n" + DocName + "\n" + PatientName + "\n" +
                        DatePre + "\n" + CollectionOfDrugNameQuantityPhrmacy + "\n";

                // Add Prescription String Into PrescriptionPatient ArrayList.
                SavedPrescription.add(Prescription);

                // Reset String Of CollectionOfDrugNameQuantityPhrmacy.
                CollectionOfDrugNameQuantityPhrmacy = "";

                // We Will Add + 1 In Condition Of [For Loop] So We Need To Decrease 1 Here.
                if (count != jsonArray.length()) {
                    // Decrease Count By 1.
                    count--;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Adapt ListView.
        listView.setAdapter(new ArrayAdapter<String>(SavedPrescriptionPatient.this,
                R.layout.prescription_form, R.id.rowTextView, SavedPrescription));
    }
}
