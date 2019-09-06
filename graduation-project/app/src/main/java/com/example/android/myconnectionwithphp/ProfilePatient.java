package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePatient extends AppCompatActivity {

    // Strings To Store Data.
    String UserNamePatient, PatientID, OldPassPatient;

    // Init EditText.
    MaterialEditText UserName, NewPassword, Email, Phone, Address;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        // Get Views By IDs.
        UserName = (MaterialEditText) findViewById(R.id.patient_username);
        NewPassword = (MaterialEditText) findViewById(R.id.patient_password);
        Email = (MaterialEditText) findViewById(R.id.patient_email);
        Phone = (MaterialEditText) findViewById(R.id.patient_phone);
        Address = (MaterialEditText) findViewById(R.id.patient_address);

        Submit = (Button) findViewById(R.id.patient_submit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Get our extras from our intent.
            UserNamePatient = extras.getString("userName");

            String type = "patient_data";

            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(ProfilePatient.this) {
                protected void onPostExecute(String Response) {

                    // Call loadProfilePatient Method.
                    loadProfilePatient(Response);

                }
            };
            // Execute Params.
            backgroundWorker.execute(type, UserNamePatient);
        }

        // When Click On Button.
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "edit_patient";

                @SuppressLint("StaticFieldLeak")
                BackgroundWorker backgroundWorker = new BackgroundWorker(ProfilePatient.this) {
                    protected void onPostExecute(String Response) {

                        // If Response Has Data updated.
                        if (Response.contains("Data updated")) {

                            // GoTo Home Activity.
                            Intent intent = new Intent(ProfilePatient.this, PatientHome.class);
                            intent.putExtra("userName", UserName.getText().toString());

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            Toast.makeText(ProfilePatient.this, "Data updated",
                                    Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(ProfilePatient.this, "Data not updated!",
                                    Toast.LENGTH_SHORT).show();
                    }
                };
                // If All Fields Not Empty.
                if(!UserName.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()
                        && !Phone.getText().toString().isEmpty() && !Address.getText().toString().isEmpty()) {

                    // Execute Params.
                    backgroundWorker.execute(type, PatientID, UserName.getText().toString(), OldPassPatient,
                            NewPassword.getText().toString(), Email.getText().toString(),
                            Phone.getText().toString(), Address.getText().toString());
                } else
                    Toast.makeText(ProfilePatient.this, "There's empty field!",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    // loadProfilePatient Method.
    private void loadProfilePatient(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("patientData");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jO = jsonArray.getJSONObject(i);

                PatientID = jO.getString("Patient_ID");
                String patient_username = jO.getString("Patient_UserName");
                OldPassPatient = jO.getString("Patient_Password");
                String patient_email = jO.getString("Patient_Email");
                String patient_phone = jO.getString("Patient_Phone");
                String patient_address = jO.getString("Patient_Address");

                // Set Text For Views.
                UserName.setText(patient_username);
                Email.setText(patient_email);
                Phone.setText("0" + patient_phone);
                Address.setText(patient_address);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
