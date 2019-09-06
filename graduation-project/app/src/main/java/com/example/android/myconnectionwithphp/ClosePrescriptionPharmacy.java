package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class ClosePrescriptionPharmacy extends AppCompatActivity {
    TextView ClosePreID, CloseDrugName, CloseDrugID, CloseQuantity, ClosePatientName, CloseDocName, CloseDate;

    FButton ClosePrescription;

    String OrderPrescription;
    String PharmacyID;
    String UserNamePharmacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_prescription_pharmacy);

        ClosePreID = (TextView) findViewById(R.id.close_pre_id);
        CloseDrugName = (TextView) findViewById(R.id.close_drug_name);
        CloseDrugID = (TextView) findViewById(R.id.close_drug_id);
        CloseQuantity = (TextView) findViewById(R.id.close_quantity);
        ClosePatientName = (TextView) findViewById(R.id.close_patient_name);
        CloseDocName = (TextView) findViewById(R.id.close_doc_name);
        CloseDate = (TextView) findViewById(R.id.close_date);

        ClosePrescription = (FButton) findViewById(R.id.close_prescription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            OrderPrescription = extras.getString("OrderPrescription");

            UserNamePharmacy = extras.getString("userName");
            PharmacyID = extras.getString("pharmacyID");
        }

        String[] separated = OrderPrescription.split("\n");

        ClosePreID.setText(separated[0]);   // For ID
        CloseDrugName.setText(separated[1]); // For Doc Name
        CloseDrugID.setText(separated[2]); // For Patient Name
        CloseQuantity.setText(separated[3]);     // For Date
        ClosePatientName.setText(separated[4]);    // For Drug Name
        CloseDocName.setText(separated[5]);    // For Drug Quantity
        CloseDate.setText(separated[6]);    // For Drug Quantity

        // When Click On Button.
        ClosePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String type = "close";

                    @SuppressLint("StaticFieldLeak")
                    BackgroundWorker backgroundWorker = new BackgroundWorker(ClosePrescriptionPharmacy.this) {
                        protected void onPostExecute(String Response) {

                            Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                            Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                            Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                            Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                            Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                            Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    loadSpinnerLocation(Response);
//                    loadPrescription(Response);
                        }
                    };
                    backgroundWorker.execute(type, PharmacyID, ClosePreID.getText().toString(),
                            CloseDrugID.getText().toString(), CloseQuantity.getText().toString());

                Intent intent = new Intent(ClosePrescriptionPharmacy.this, ListOfOrderPharmacy.class);
                intent.putExtra("userName", UserNamePharmacy);
                intent.putExtra("pharmacyID", PharmacyID);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
