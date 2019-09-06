package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WritePrescriptionToPatient extends AppCompatActivity {
    String UserNameDoc;
    TextView Doc_FullName, Doc_ID;

    LinearLayout Linear1, Linear2, Linear3, Linear4, Linear5;

    ArrayList<String> MedicinesNameAL;
    SearchableSpinner SpnrMedicines1, SpnrMedicines2, SpnrMedicines3, SpnrMedicines4, SpnrMedicines5;

    SearchableSpinner[] CollectionOfSSMedicines;

    String SelectedMedicine1, SelectedMedicine2, SelectedMedicine3, SelectedMedicine4, SelectedMedicine5;

    String[] CollectionOfStringSelectedMedicines;

    ArrayList<String> CapsAL, DaysAL;
    SearchableSpinner HowManyCap1, HowManyCap2, HowManyCap3, HowManyCap4, HowManyCap5,
            HowManyDays1, HowManyDays2, HowManyDays3, HowManyDays4, HowManyDays5;

    SearchableSpinner[] CollectionOfSSCapSDays;

    String SelectedCap1, SelectedCap2, SelectedCap3, SelectedCap4, SelectedCap5, SelectedDay1, SelectedDay2,
            SelectedDay3, SelectedDay4, SelectedDay5;

    String[] CollectionOfStringSelectedCapsDays;

    ArrayList<String> PatientName;
    SearchableSpinner SpnrPatient;

    Button SendPrescription;
    FloatingActionButton AddSpnr;
    String SelectedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_prescription_to_patient);

        Linear1 = (LinearLayout) findViewById(R.id.linear1);
        Linear2 = (LinearLayout) findViewById(R.id.linear2);
        Linear3 = (LinearLayout) findViewById(R.id.linear3);
        Linear4 = (LinearLayout) findViewById(R.id.linear4);
        Linear5 = (LinearLayout) findViewById(R.id.linear5);

        Doc_FullName = (TextView) findViewById(R.id.doc_name);
        Doc_ID = (TextView) findViewById(R.id.doc_id);


        MedicinesNameAL = new ArrayList<>();
        SpnrMedicines1 = (SearchableSpinner) findViewById(R.id.drug_name1);

        SpnrMedicines2 = (SearchableSpinner) findViewById(R.id.drug_name2);

        SpnrMedicines3 = (SearchableSpinner) findViewById(R.id.drug_name3);

        SpnrMedicines4 = (SearchableSpinner) findViewById(R.id.drug_name4);

        SpnrMedicines5 = (SearchableSpinner) findViewById(R.id.drug_name5);

        CollectionOfSSMedicines = new SearchableSpinner[]{SpnrMedicines1, SpnrMedicines2, SpnrMedicines3,
                SpnrMedicines4, SpnrMedicines5};

        CollectionOfStringSelectedMedicines = new String[]{SelectedMedicine1, SelectedMedicine2,
                SelectedMedicine3, SelectedMedicine4, SelectedMedicine5};

        CapsAL = new ArrayList<>();
        HowManyCap1 = (SearchableSpinner) findViewById(R.id.how_many_cap1);

        HowManyCap2 = (SearchableSpinner) findViewById(R.id.how_many_cap2);

        HowManyCap3 = (SearchableSpinner) findViewById(R.id.how_many_cap3);

        HowManyCap4 = (SearchableSpinner) findViewById(R.id.how_many_cap4);

        HowManyCap5 = (SearchableSpinner) findViewById(R.id.how_many_cap5);

        DaysAL = new ArrayList<>();
        HowManyDays1 = (SearchableSpinner) findViewById(R.id.how_many_days1);

        HowManyDays2 = (SearchableSpinner) findViewById(R.id.how_many_days2);

        HowManyDays3 = (SearchableSpinner) findViewById(R.id.how_many_days3);

        HowManyDays4 = (SearchableSpinner) findViewById(R.id.how_many_days4);

        HowManyDays5 = (SearchableSpinner) findViewById(R.id.how_many_days5);

        CollectionOfSSCapSDays = new SearchableSpinner[]{HowManyCap1, HowManyCap2, HowManyCap3, HowManyCap4,
                HowManyCap5, HowManyDays1, HowManyDays2, HowManyDays3, HowManyDays4, HowManyDays5};

        CollectionOfStringSelectedCapsDays = new String[]{SelectedCap1, SelectedCap2, SelectedCap3,
                SelectedCap4, SelectedCap5, SelectedDay1, SelectedDay2, SelectedDay3, SelectedDay4, SelectedDay5};


        PatientName = new ArrayList<>();
        SpnrPatient = (SearchableSpinner) findViewById(R.id.patient_name);

        SendPrescription = (Button) findViewById(R.id.btn_send_prescription);

        AddSpnr = (FloatingActionButton) findViewById(R.id.add_spnr);

        AddSpnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Linear2.getVisibility() == View.GONE)
                    Linear2.setVisibility(View.VISIBLE);
                else if (Linear3.getVisibility() == View.GONE)
                    Linear3.setVisibility(View.VISIBLE);
                else if (Linear4.getVisibility() == View.GONE)
                    Linear4.setVisibility(View.VISIBLE);
                else if (Linear5.getVisibility() == View.GONE) {
                    Linear5.setVisibility(View.VISIBLE);
                    AddSpnr.setVisibility(View.GONE);
                }
            }
        });

        // Loop For How Many Times In One Time To Take Capsule.
        for (int times = 1; times <= 4; times++) {
            CapsAL.add(Integer.toString(times));
        }
        ArrayAdapter<String> adapterCaps = new ArrayAdapter<String>(this, R.layout.prescription_form,
                R.id.rowTextView, CapsAL);

        // Loop For How Many Days To Take Capsule.
        for (int day = 1; day <= 360; day++) {
            DaysAL.add(Integer.toString(day));
        }
        ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this, R.layout.prescription_form,
                R.id.rowTextView, DaysAL);

        for (int count = 0; count < CollectionOfSSCapSDays.length; count++) {
            // 0 -> 4 For Times Of Caps, 5->9 For Days Of Caps. Check [CollectionOfSSCapSDays].
            if (count <= 4)
                CollectionOfSSCapSDays[count].setAdapter(adapterCaps);
            else
                CollectionOfSSCapSDays[count].setAdapter(adapterDays);
        }

//        HowManyCap2.setAdapter(adapterCaps);
//        HowManyCap3.setAdapter(adapterCaps);
//        HowManyCap4.setAdapter(adapterCaps);
//        HowManyCap5.setAdapter(adapterCaps);
//
//
//
//        HowManyDays1.setAdapter(adapterDays);
//        HowManyDays2.setAdapter(adapterDays);
//        HowManyDays3.setAdapter(adapterDays);
//        HowManyDays4.setAdapter(adapterDays);
//        HowManyDays5.setAdapter(adapterDays);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNameDoc = extras.getString("userName");

            String type = "doc_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(WritePrescriptionToPatient.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    loadDocData(Response);

                    loadSpinnerMedicines(Response);

//                    loadSpinnerMedicines2(Response);
//
//                    loadSpinnerMedicines3(Response);
//
//                    loadSpinnerMedicines4(Response);
//
//                    loadSpinnerMedicines5(Response);

                    loadSpinnerPatient(Response);
                }
            };
            backgroundWorker.execute(type, UserNameDoc);
        }

        for (int count = 0; count < CollectionOfSSMedicines.length; count++) {

            CollectionOfSSMedicines[count].setTitle("Select Medicine");
            CollectionOfSSMedicines[count].setPositiveButton("CANCEL");

            // Because count of For Loop Can't Be Final, So We Get This Value Into Own Final Variable.
            final int finalCount = count;

            CollectionOfSSMedicines[count].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    CollectionOfStringSelectedMedicines[finalCount] =
                            CollectionOfSSMedicines[finalCount].getSelectedItem().toString();

                    //                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // DO Nothing here
                }
            });
        }

//        SpnrMedicines2.setTitle("Select Medicine");
//        SpnrMedicines2.setPositiveButton("CANCEL");
//        SpnrMedicines2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SelectedMedicine2 = SpnrMedicines2.getSelectedItem().toString();
////                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//
//        SpnrMedicines3.setTitle("Select Medicine");
//        SpnrMedicines3.setPositiveButton("CANCEL");
//        SpnrMedicines3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SelectedMedicine3 = SpnrMedicines3.getSelectedItem().toString();
////                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//
//        SpnrMedicines4.setTitle("Select Medicine");
//        SpnrMedicines4.setPositiveButton("CANCEL");
//        SpnrMedicines4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SelectedMedicine4 = SpnrMedicines4.getSelectedItem().toString();
////                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });
//
//        SpnrMedicines5.setTitle("Select Medicine");
//        SpnrMedicines5.setPositiveButton("CANCEL");
//        SpnrMedicines5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                SelectedMedicine5 = SpnrMedicines5.getSelectedItem().toString();
////                Toast.makeText(getApplicationContext(),Medicine,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });

        for (int count = 0; count < CollectionOfSSCapSDays.length; count++) {
            if (count <= 4)
                CollectionOfSSCapSDays[count].setTitle("How Many Times Of Capsule");
            else
                CollectionOfSSCapSDays[count].setTitle("How Many Days Of Capsule");
            CollectionOfSSCapSDays[count].setPositiveButton("CANCEL");

            // Because count of For Loop Can't Be Final, So We Get This Value Into Own Final Variable.
            final int finalCount = count;

            CollectionOfSSCapSDays[count].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Store SelectedItem Into selectedActivity String.
                    CollectionOfStringSelectedCapsDays[finalCount] =
                            CollectionOfSSCapSDays[finalCount].getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

//        HowManyCap2.setTitle("How Many Times Of Capsule");
//        HowManyCap2.setPositiveButton("CANCEL");
//        HowManyCap2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedCap2 = HowManyCap2.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyCap3.setTitle("How Many Times Of Capsule");
//        HowManyCap3.setPositiveButton("CANCEL");
//        HowManyCap3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedCap3 = HowManyCap3.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyCap4.setTitle("How Many Times Of Capsule");
//        HowManyCap4.setPositiveButton("CANCEL");
//        HowManyCap4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedCap4 = HowManyCap4.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyCap5.setTitle("How Many Times Of Capsule");
//        HowManyCap5.setPositiveButton("CANCEL");
//        HowManyCap5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedCap5 = HowManyCap5.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        HowManyDays1.setTitle("How Many Days Of Capsule");
//        HowManyDays1.setPositiveButton("CANCEL");
//        HowManyDays1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedDay1 = HowManyDays1.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyDays2.setTitle("How Many Days Of Capsule");
//        HowManyDays2.setPositiveButton("CANCEL");
//        HowManyDays2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedDay2 = HowManyDays2.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyDays3.setTitle("How Many Days Of Capsule");
//        HowManyDays3.setPositiveButton("CANCEL");
//        HowManyDays3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedDay3 = HowManyDays3.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyDays4.setTitle("How Many Days Of Capsule");
//        HowManyDays4.setPositiveButton("CANCEL");
//        HowManyDays4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedDay4 = HowManyDays4.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        HowManyDays5.setTitle("How Many Days Of Capsule");
//        HowManyDays5.setPositiveButton("CANCEL");
//        HowManyDays5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                // Store SelectedItem Into selectedActivity String.
//                SelectedDay5 = HowManyDays5.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        SpnrPatient.setTitle("Select Patient");
        SpnrPatient.setPositiveButton("CANCEL");
        SpnrPatient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedPatient = SpnrPatient.getSelectedItem().toString();
//                Toast.makeText(getApplicationContext(),Patient,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        SendPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "write_prescription";
                String DocID = Doc_ID.getText().toString();
//                String DocFullName = Doc_FullName.getText().toString();

                BackgroundWorker backgroundWorker = new BackgroundWorker(getBaseContext());
                backgroundWorker.execute(type, DocID, CollectionOfStringSelectedMedicines[0],
                        CollectionOfStringSelectedMedicines[1], CollectionOfStringSelectedMedicines[2],
                        CollectionOfStringSelectedMedicines[3], CollectionOfStringSelectedMedicines[4],
                        CollectionOfStringSelectedCapsDays[0], CollectionOfStringSelectedCapsDays[1],
                        CollectionOfStringSelectedCapsDays[2], CollectionOfStringSelectedCapsDays[3],
                        CollectionOfStringSelectedCapsDays[4], CollectionOfStringSelectedCapsDays[5],
                        CollectionOfStringSelectedCapsDays[6], CollectionOfStringSelectedCapsDays[7],
                        CollectionOfStringSelectedCapsDays[8], CollectionOfStringSelectedCapsDays[9],
                        SelectedPatient);

//                Toast.makeText(WritePrescriptionToPatient.this, DocNameStr + " " + " " + Medicine
//                        + " " + " " +  Patient, Toast.LENGTH_SHORT).show();

                Toast.makeText(WritePrescriptionToPatient.this, "Prescription has be sent!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(WritePrescriptionToPatient.this, DoctorHome.class);
                intent.putExtra("userName", UserNameDoc);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void loadDocData(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("doctorData");

            JSONObject jO = jsonArray.getJSONObject(0);

            String name = jO.getString("Doc_FullName");
            Doc_FullName.setText(name);

            String id = jO.getString("Doc_ID");
            Doc_ID.setText(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // array list
    private void loadSpinnerMedicines(final String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("Medicines");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jO = jsonArray.getJSONObject(i);

                String id = jO.getString("DrugID");
                String name = jO.getString("DrugName");
                MedicinesNameAL.add(name + "-" + id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (SearchableSpinner CollectionOfSSMedicine : CollectionOfSSMedicines) {
            // array adapter responsible array list ==  MedicinesName
            CollectionOfSSMedicine.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
                    R.layout.prescription_form, R.id.rowTextView, MedicinesNameAL));
        }
    }

//
//    // array list
//    private void loadSpinnerMedicines2(final String response) {
//        try {
//            JSONArray ResponseJsonArray = new JSONArray(response);
//
//            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);
//
//            JSONArray jsonArray = jsonObject.getJSONArray("Medicines");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jO = jsonArray.getJSONObject(i);
//
//                String id = jO.getString("DrugID");
//                String name = jO.getString("DrugName");
//                MedicinesNameAL.add(name + "-" + id);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // array adapter responsible array list ==  MedicinesName
//        SpnrMedicines2.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
//                R.layout.prescription_form, R.id.rowTextView, MedicinesNameAL));
//    }
//
//
//    // array list
//    private void loadSpinnerMedicines3(final String response) {
//        try {
//            JSONArray ResponseJsonArray = new JSONArray(response);
//
//            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);
//
//            JSONArray jsonArray = jsonObject.getJSONArray("Medicines");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jO = jsonArray.getJSONObject(i);
//
//                String id = jO.getString("DrugID");
//                String name = jO.getString("DrugName");
//                MedicinesNameAL.add(name + "-" + id);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // array adapter responsible array list ==  MedicinesName
//        SpnrMedicines3.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
//                R.layout.prescription_form, R.id.rowTextView, MedicinesNameAL));
//    }
//
//
//    // array list
//    private void loadSpinnerMedicines4(final String response) {
//        try {
//            JSONArray ResponseJsonArray = new JSONArray(response);
//
//            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);
//
//            JSONArray jsonArray = jsonObject.getJSONArray("Medicines");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jO = jsonArray.getJSONObject(i);
//
//                String id = jO.getString("DrugID");
//                String name = jO.getString("DrugName");
//                MedicinesNameAL.add(name + "-" + id);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // array adapter responsible array list ==  MedicinesName
//        SpnrMedicines4.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
//                R.layout.prescription_form, R.id.rowTextView, MedicinesNameAL));
//    }
//
//
//    // array list
//    private void loadSpinnerMedicines5(final String response) {
//        try {
//            JSONArray ResponseJsonArray = new JSONArray(response);
//
//            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);
//
//            JSONArray jsonArray = jsonObject.getJSONArray("Medicines");
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jO = jsonArray.getJSONObject(i);
//
//                String id = jO.getString("DrugID");
//                String name = jO.getString("DrugName");
//                MedicinesNameAL.add(name + "-" + id);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        // array adapter responsible array list ==  MedicinesName
//        SpnrMedicines5.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
//                R.layout.prescription_form, R.id.rowTextView, MedicinesNameAL));
//    }

    private void loadSpinnerPatient(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("Patient");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jO = jsonArray.getJSONObject(i);

                String patient_id = jO.getString("Patient_ID");
                String patient_name = jO.getString("Patient_Name");
                PatientName.add(patient_id + "-" + patient_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpnrPatient.setAdapter(new ArrayAdapter<String>(WritePrescriptionToPatient.this,
                R.layout.prescription_form, R.id.rowTextView, PatientName));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WritePrescriptionToPatient.this, DoctorHome.class);
        intent.putExtra("userName", UserNameDoc);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}