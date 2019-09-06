package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rey.material.widget.TextView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class SendPrescriptionToPharmacy extends AppCompatActivity {
    // String To Store Data Come From Intent.
    String OriginalPrescription;

    // TextViews.
    TextView PreIDTv, DocNameTv, PatientNameTv, DatePreTv;

    // TextViews  For DrugsName.
    TextView Drug_Name1, Drug_Name2, Drug_Name3, Drug_Name4, Drug_Name5;

    // Collection Of TextViews For DrugsName.
    TextView[] CollectionOfTextViewDrugs;

    // Strings  For DrugsName.
    String DrugName1, DrugName2, DrugName3, DrugName4, DrugName5;

    // Collection Of Strings  For DrugsName.
    String[] StringDrugs;

    // TextViews For Quantity Of Drugs.
    TextView Drug_Quantity1, Drug_Quantity2, Drug_Quantity3, Drug_Quantity4, Drug_Quantity5;

    // Collection Of TextViews For Quantity Of Drugs.
    TextView[] CollectionOfTextViewQuantity;

    // Strings For Quantity Of Drugs.
    String DrugQuantity1, DrugQuantity2, DrugQuantity3, DrugQuantity4, DrugQuantity5;

    // Collection Of Strings For Quantity Of Drugs.
    String[] StringDrugsQuantity;

    // ArrayLists For Pharmacies.
    ArrayList<String> Pharmacy1, Pharmacy2, Pharmacy3, Pharmacy4, Pharmacy5;

    // Collection Of ArrayLists For Pharmacies.
    ArrayList[] CollectionOfPharmaciesAL;

    // Spinners For Pharmacies.
    SearchableSpinner SpnrPharmacy1, SpnrPharmacy2, SpnrPharmacy3, SpnrPharmacy4, SpnrPharmacy5;

    // Collection Of Spinners For Pharmacies.
    SearchableSpinner[] CollectionOfPharmaciesSpnr;

    // Strings For Pharmacies.
    String SelectedPharmacy1, SelectedPharmacy2, SelectedPharmacy3, SelectedPharmacy4, SelectedPharmacy5;

    // Collection Of Strings For Pharmacies.
    String[] CollectionOfStringSelectedPharmacy;

    // Buttons To Send Data To Pharmacies.
    FButton SendDrug1Btn, SendDrug2Btn, SendDrug3Btn, SendDrug4Btn, SendDrug5Btn;

    // Collection Of Buttons To Send Data To Pharmacies.
    FButton[] CollectionOfButtons;

    // Strings For IDs.
    String SelectedDrugID;
    String SelectedPharmacyID;

    // String Of User Name Of Patient.
    String UserNamePatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_prescription_to_pharmacy);

        // Get Views By IDs.
        PreIDTv = (TextView) findViewById(R.id.pre_id);
        DocNameTv = (TextView) findViewById(R.id.doc_name);
        PatientNameTv = (TextView) findViewById(R.id.patient_name);
        DatePreTv = (TextView) findViewById(R.id.date_pre);

        SendDrug1Btn = (FButton) findViewById(R.id.btn_send_drug_1);
        SendDrug2Btn = (FButton) findViewById(R.id.btn_send_drug_2);
        SendDrug3Btn = (FButton) findViewById(R.id.btn_send_drug_3);
        SendDrug4Btn = (FButton) findViewById(R.id.btn_send_drug_4);
        SendDrug5Btn = (FButton) findViewById(R.id.btn_send_drug_5);

        CollectionOfButtons = new FButton[]{SendDrug1Btn, SendDrug2Btn, SendDrug3Btn, SendDrug4Btn, SendDrug5Btn};

        Drug_Name1 = (TextView) findViewById(R.id.drug_name1);
        Drug_Name2 = (TextView) findViewById(R.id.drug_name2);
        Drug_Name3 = (TextView) findViewById(R.id.drug_name3);
        Drug_Name4 = (TextView) findViewById(R.id.drug_name4);
        Drug_Name5 = (TextView) findViewById(R.id.drug_name5);

        CollectionOfTextViewDrugs = new TextView[]{Drug_Name1, Drug_Name2, Drug_Name3, Drug_Name4, Drug_Name5};
        StringDrugs = new String[]{DrugName1, DrugName2, DrugName3, DrugName4, DrugName5};

        Drug_Quantity1 = (TextView) findViewById(R.id.drug_quantity1);
        Drug_Quantity2 = (TextView) findViewById(R.id.drug_quantity2);
        Drug_Quantity3 = (TextView) findViewById(R.id.drug_quantity3);
        Drug_Quantity4 = (TextView) findViewById(R.id.drug_quantity4);
        Drug_Quantity5 = (TextView) findViewById(R.id.drug_quantity5);

        CollectionOfTextViewQuantity = new TextView[]{Drug_Quantity1, Drug_Quantity2, Drug_Quantity3, Drug_Quantity4, Drug_Quantity5};
        StringDrugsQuantity = new String[]{DrugQuantity1, DrugQuantity2, DrugQuantity3, DrugQuantity4, DrugQuantity5};

        Pharmacy1 = new ArrayList<>();
        Pharmacy2 = new ArrayList<>();
        Pharmacy3 = new ArrayList<>();
        Pharmacy4 = new ArrayList<>();
        Pharmacy5 = new ArrayList<>();

        CollectionOfPharmaciesAL = new ArrayList[]{Pharmacy1, Pharmacy2, Pharmacy3, Pharmacy4, Pharmacy5};

        SpnrPharmacy1 = (SearchableSpinner) findViewById(R.id.pharmacy1);
        SpnrPharmacy2 = (SearchableSpinner) findViewById(R.id.pharmacy2);
        SpnrPharmacy3 = (SearchableSpinner) findViewById(R.id.pharmacy3);
        SpnrPharmacy4 = (SearchableSpinner) findViewById(R.id.pharmacy4);
        SpnrPharmacy5 = (SearchableSpinner) findViewById(R.id.pharmacy5);

        CollectionOfPharmaciesSpnr = new SearchableSpinner[]{SpnrPharmacy1, SpnrPharmacy2, SpnrPharmacy3,
                SpnrPharmacy4, SpnrPharmacy5};

        CollectionOfStringSelectedPharmacy = new String[]{SelectedPharmacy1, SelectedPharmacy2,
                SelectedPharmacy3, SelectedPharmacy4, SelectedPharmacy5};

        // Loop For Buttons.
        for (int count = 0; count < CollectionOfButtons.length; count++) {

            // Use Value Of count Into If Statement Need Final Variable.
            final int finalCount = count;

            // When Click On Button By Its Own Index [1, 2, 3, 4, 5].
            CollectionOfButtons[count].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "determin_pharmacy";

                    // I Don't Need Response Of Server.. I Need To Send Data In  backgroundWorker.execute To Server.
                    @SuppressLint("StaticFieldLeak")
                    BackgroundWorker backgroundWorker = new BackgroundWorker(SendPrescriptionToPharmacy.this);

                    // Check If Drug Exist & Its TextView Fields Not Empty.
                    if (!CollectionOfTextViewDrugs[finalCount].getText().equals("") &&
                            CollectionOfStringSelectedPharmacy[finalCount] != null) {

                        // Split Name [Drug Or Pharmacy] From IDs.  >> Ex. [DrugID-DrugName]
                        String[] separatedIDDrug = CollectionOfTextViewDrugs[finalCount].getText().toString().split("-");
                        String[] separatedIDPharmacy = CollectionOfStringSelectedPharmacy[finalCount].split("-");

                        // Store IDs Into Strings.
                        SelectedDrugID = separatedIDDrug[0];
                        SelectedPharmacyID = separatedIDPharmacy[0];

                        // Execute backgroundWorker With Params.
                        backgroundWorker.execute(type, PreIDTv.getText().toString(), SelectedDrugID, SelectedPharmacyID);

                        Toast.makeText(SendPrescriptionToPharmacy.this, "Drugs have been sent successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SendPrescriptionToPharmacy.this, "Choose Pharmacy With Your Drug!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Get Data From Intent.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get Prescription.
            OriginalPrescription = extras.getString("OriginalPrescription");

            // Get UserName.
            UserNamePatient = extras.getString("userName");
        }

        // Loop For Pharmacies.
        for (int count = 0; count < CollectionOfPharmaciesSpnr.length; count++) {
            // Create Title & Cancel Button For Every Pharmacy.
            CollectionOfPharmaciesSpnr[count].setTitle("Select Pharmacy");
            CollectionOfPharmaciesSpnr[count].setPositiveButton("CANCEL");

            // Because count of For Loop Can't Be Final, So We Get This Value Into Own Final Variable.
            final int finalCount = count;

            // When Selected Pharmacy From Spinner.
            CollectionOfPharmaciesSpnr[count].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Store This Selected Pharmacy Into String Selected Pharmacy.
                    CollectionOfStringSelectedPharmacy[finalCount] = CollectionOfPharmaciesSpnr[finalCount].getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // DO Nothing here
                }
            });
        }

        // Split The Prescription Came From Intent.
        String[] separated = OriginalPrescription.split("\n");

        // Fixed Indexes For Every Fields.
        PreIDTv.setText(separated[0]);   // For ID
        DocNameTv.setText(separated[1]); // For Doc Name
        PatientNameTv.setText(separated[2]); // For Patient Name
        DatePreTv.setText(separated[3]);     // For Date
//        CollectionOfTextViewDrugs[0].setText(separated[4]);    // For First Drug Name
//        CollectionOfTextViewQuantity[0].setText(separated[5]);    // For First Drug Quantity

        // We Will Start Work From Index 4.
        int count = 4;
        // Number Of Index DrugName & Quantity TextViews.
        int counterDrugs = 0;
        // Number Of Index Pharmacies ArrayList & Spinners.
        int counterPharmacy = 0;

        // Loop.
        while (count < separated.length) {
            // UpperCase [Trick For Me] Means This Index Is Drug Name.
            if (separated[count].equals(separated[count].toUpperCase())) {
                CollectionOfTextViewDrugs[counterDrugs].setText(separated[count]);    // For Drug Name
                CollectionOfTextViewQuantity[counterDrugs].setText(separated[count + 1]);    // For Drug Quantity

                // Increase 1 For Index Of Drug Name & Quantity TextViews.
                counterDrugs++;

                // +2 Cause We Add Drug Name & Quantity.
                count += 2;
                // Otherwise Means This Index Is Pharmacies Name.
            }

            while (count < separated.length && !separated[count].equals(separated[count].toUpperCase())) {
                // Add Pharmacy Name To Array List.
                CollectionOfPharmaciesAL[counterPharmacy].add(separated[count]);
                // Increase 1 For Count.
                count++;
            }

            // Set Adapter For Every Spinners By Its Index.
            CollectionOfPharmaciesSpnr[counterPharmacy].setAdapter(new ArrayAdapter<String>(SendPrescriptionToPharmacy.this,
                    R.layout.prescription_form, R.id.rowTextView, CollectionOfPharmaciesAL[counterPharmacy]));

            // Increase 1 For Index Of Pharmacy.
            counterPharmacy++;

        }
    }
}