package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LimitOfDrugsPharmacy extends AppCompatActivity {
    ArrayList<String> LimitOfDrugsAL;
    ListView LimitOfDrugs;

    String UserNamePharmacy;
//    String PharmacyID;

    String ID, DrugName, Quantity, Date, Time;

    String CollectionOfData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_of_drugs_pharmacy);

        LimitOfDrugsAL = new ArrayList<>();
        LimitOfDrugs = (ListView) findViewById(R.id.limit_of_drugs);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNamePharmacy = extras.getString("userName");
//            PharmacyID = extras.getString("pharmacyID");

            String type = "pharmacy_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(LimitOfDrugsPharmacy.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadLimitDrugs(Response);

                }
            };
            backgroundWorker.execute(type, UserNamePharmacy);
        }

    }

    private void loadLimitDrugs(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("Categories_reached_the_limit");

            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject jO = jsonArray.getJSONObject(count);

                ID = jO.getString("id");
                DrugName = jO.getString("drug_name");
                Quantity = jO.getString("quantity");
                Date = jO.getString("date");
                Time = jO.getString("time");

                CollectionOfData = "ID: " + ID + "\n" + "DrugName: " + DrugName + "\n" + "Quantity: " + Quantity + "\n"
                        + "Date: " + Date + "\n" + "Time: " + Time + "\n";
                LimitOfDrugsAL.add(CollectionOfData);
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        LimitOfDrugs.setAdapter(new ArrayAdapter<String>(LimitOfDrugsPharmacy.this,
                R.layout.prescription_form, R.id.rowTextView, LimitOfDrugsAL));
    }
}
