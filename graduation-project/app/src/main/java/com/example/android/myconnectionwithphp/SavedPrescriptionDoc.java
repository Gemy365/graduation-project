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

public class SavedPrescriptionDoc extends AppCompatActivity {
    ArrayList<String> SavedPrescription;
    ListView listView;

    String Prescription;
    String UserNameDoc;

    String PreID;
    String DocName;
    String PatientName;
    String DrugName;
    String QuantityDemand;
    String DatePre;

    int FirstDrugOfPatient = 0;
    int CountOfDrugs = 0;

    String AllDrugs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_prescription_doc);
        listView = (ListView) findViewById(R.id.list);
        SavedPrescription = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNameDoc = extras.getString("userName");

            String type = "doc_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(SavedPrescriptionDoc.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadPrescription(Response);

                }
            };
            backgroundWorker.execute(type, UserNameDoc);
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                prescription = SavedPrescriptionDoc.get(position);
//                Intent intent = new Intent(SavedPrescriptionDoc.this, SendPrescriptionToPharmacy.class);
//                intent.putExtra("medicineName", prescription);
//                startActivity(intent);
////                Toast.makeText(ReadPrescriptionsFromDoctor.this, "Click On Prescription No. " + prescription,
////                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void loadPrescription(String response) {
        try {

            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("prescriptionDataDoctor");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jO = jsonArray.getJSONObject(i);

                while (i != jsonArray.length() -1 && (jsonArray.getJSONObject(i).getString("Patient_Name")
                        .equals(jsonArray.getJSONObject(i+1).getString("Patient_Name")))){

                    /**
                     * 0 1
                     * 1 2
                     * 2 3
                     * 3 4
                     * 4 5
                     * 5!6
                     */

                    CountOfDrugs++; // 5
                    i++; // 5

                    if(i == jsonArray.length() -1) {
                        Log.d("i", String.valueOf(i));
                        break;
                    }
                }

//                CountOfDrugs = jO.getString("count");
                PreID = jO.getString("Prescription_ID");
                DocName = jO.getString("DocName");
                PatientName = jO.getString("Patient_Name");
                DatePre = jO.getString("Date_Pre");

                while (CountOfDrugs >= FirstDrugOfPatient){
                    DrugName = jsonArray.getJSONObject(CountOfDrugs).getString("drug_Data");
                    QuantityDemand = jsonArray.getJSONObject(CountOfDrugs).getString("quantity_demand");
                    Log.d("Prescription", String.valueOf(CountOfDrugs));

                    AllDrugs += "\n" + DrugName + "\n" + QuantityDemand + "\n";
                    CountOfDrugs--;
                }


                Prescription = PreID + "\n" + DocName + "\n" + PatientName + "\n" +
                        AllDrugs + "\n" + DatePre;
                SavedPrescription.add(Prescription);

                // Reset The Collection Of Drugs For The Same Patient.
                AllDrugs = "";
                // Reset The Count Of Drugs For The Same Patient.
                CountOfDrugs = i + 1;
                FirstDrugOfPatient = i + 1;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setAdapter(new ArrayAdapter<String>(SavedPrescriptionDoc.this,
                R.layout.prescription_form, R.id.rowTextView, SavedPrescription));
    }
}
