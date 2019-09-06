package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfDrugsPharmacy extends AppCompatActivity {
    ArrayList<String> ListOfDrugsAL;
    ListView ListOfDrugs;

    String UserNamePharmacy;
//    String PharmacyID;

    String ID, DrugName, Quantity, Date, Time;

    String CollectionOfData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_drugs_pharmacy);

        ListOfDrugsAL = new ArrayList<>();
        ListOfDrugs = (ListView) findViewById(R.id.list_of_drugs);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNamePharmacy = extras.getString("userName");
//            PharmacyID = extras.getString("pharmacyID");

            String type = "pharmacy_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(ListOfDrugsPharmacy.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadStockOfDrugs(Response);

                }
            };
            backgroundWorker.execute(type, UserNamePharmacy);
        }

    }

    private void loadStockOfDrugs(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("Stock_of_drug");

            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject jO = jsonArray.getJSONObject(count);

                ID = jO.getString("id");
                DrugName = jO.getString("drug_name");
                Quantity = jO.getString("quantity");
                Date = jO.getString("date");
                Time = jO.getString("time");

                CollectionOfData = "ID: " + ID + "\n" + "DrugName: " + DrugName + "\n" + "Quantity: " + Quantity + "\n"
                        + "Date: " + Date + "\n" + "Time: " + Time + "\n";
                ListOfDrugsAL.add(CollectionOfData);
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        ListOfDrugs.setAdapter(new ArrayAdapter<String>(ListOfDrugsPharmacy.this,
                R.layout.prescription_form, R.id.rowTextView, ListOfDrugsAL));
    }
}
