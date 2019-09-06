package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfOrderPharmacy extends AppCompatActivity {
    ArrayList<String> ListOfOrderAL;
    ListView ListOfOrder;

    String UserNamePharmacy;
    String PharmacyID;

    String OrderID, OrderDrugName, DrugOrderID, Quantity,  OrderPatient, DOrderDoctor, OrderDate;

    String CollectionOfData;
    String Prescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_order_pharmacy);

        ListOfOrderAL = new ArrayList<>();
        ListOfOrder = (ListView) findViewById(R.id.list_of_order);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNamePharmacy = extras.getString("userName");
            PharmacyID = extras.getString("pharmacyID");

            String type = "pharmacy_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(ListOfOrderPharmacy.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadOrderOfDrugs(Response);

                }
            };
            backgroundWorker.execute(type, UserNamePharmacy);
        }
        ListOfOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Prescription = ListOfOrderAL.get(position);
                Intent intent = new Intent(ListOfOrderPharmacy.this, ClosePrescriptionPharmacy.class);
                intent.putExtra("OrderPrescription", Prescription);
                intent.putExtra("userName", UserNamePharmacy);
                intent.putExtra("pharmacyID", PharmacyID);

                startActivity(intent);
            }
        });
    }

    private void loadOrderOfDrugs(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("list_of_order");

            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject jO = jsonArray.getJSONObject(count);

                OrderID = jO.getString("prescription_id_order");
                OrderDrugName = jO.getString("drug_name_order");
                DrugOrderID = jO.getString("drug_id_order");
                Quantity = jO.getString("quantity_demand_order");

                // GoTo The Next Object.
                count++;
                JSONObject JO = jsonArray.getJSONObject(count);

                OrderPatient = JO.getString("patient_name_order");
                DOrderDoctor = JO.getString("doctor_name_order");
                OrderDate = JO.getString("date_order");
//                Time = jO.getString("time");

                CollectionOfData = OrderID + "\n" + OrderDrugName + "\n" + DrugOrderID + "\n" +
                        Quantity + "\n" + OrderPatient + "\n" + DOrderDoctor + "\n" + OrderDate + "\n";
                ListOfOrderAL.add(CollectionOfData);
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        ListOfOrder.setAdapter(new ArrayAdapter<String>(ListOfOrderPharmacy.this,
                R.layout.prescription_form, R.id.rowTextView, ListOfOrderAL));
    }
}
