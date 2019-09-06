package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileDoctor extends AppCompatActivity {

    // Strings.
    String UserNameDoc, IDDoc, OldPassDoc;

    // Init Views.
    MaterialEditText UserName, NewPassword, Email, Phone, Address;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        // Get View By IDs.
        UserName = (MaterialEditText) findViewById(R.id.doctor_username);
        NewPassword = (MaterialEditText) findViewById(R.id.doctor_password);
        Email = (MaterialEditText) findViewById(R.id.doctor_email);
        Phone = (MaterialEditText) findViewById(R.id.doctor_phone);
        Address = (MaterialEditText) findViewById(R.id.doctor_address);
        Submit = (Button) findViewById(R.id.doctor_submit);

        // Get Data From Intent.
        Bundle extras = getIntent().getExtras();
        // Check If extras Not Null.
        if (extras != null) {
            // Get our extras from our intent.
            // Unbundle the username.
            UserNameDoc = extras.getString("userName");

            // Set Type As Key As The Same Key Into BackgroundWorker If Statement To Check Which If I Will Work With.
            String type = "doc_data";

            // Sent Activity As Param To BackgroundWorker Class.
            @SuppressLint("StaticFieldLeak")
            BackgroundWorker backgroundWorker = new BackgroundWorker(ProfileDoctor.this) {
                protected void onPostExecute(String Response) {
                    Response = Response.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
                    Response = Response.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
                    Response = Response.replaceAll("<(.*?)\\\n", ""); //Must be underneath
                    Response = Response.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
                    Response = Response.replaceAll("&nbsp;", "");        // Remove any space.
                    Response = Response.replaceAll("&amp;", "");         // Remove any & char.

//                    Toast.makeText(context, "" + Response, Toast.LENGTH_LONG).show();

                    // Call Method
                    loadDocData(Response);
                }
            };
            // Active backgroundWorker & Send Prams [type, UserName].
            backgroundWorker.execute(type, UserNameDoc);
        }

        // When Press On Button.
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set Type As Key As The Same Key Into BackgroundWorker If Statement To Check Which If I Will Work With.
                String type = "edit_doctor";

                // Sent Activity As Param To BackgroundWorker Class.
                @SuppressLint("StaticFieldLeak")
                BackgroundWorker backgroundWorker = new BackgroundWorker(ProfileDoctor.this) {
                    protected void onPostExecute(String Response) {

                        // If Data Updated.
                        if (Response.contains("Data updated")) {
                            // GoTo DoctorHome Activity.
                            Intent intent = new Intent(ProfileDoctor.this, DoctorHome.class);
                            // Take UserName With You.
                            intent.putExtra("userName", UserName.getText().toString());
                            // Clear Top Activity.
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            Toast.makeText(ProfileDoctor.this, "Data updated",
                                    Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(ProfileDoctor.this, "Data not updated!",
                                    Toast.LENGTH_SHORT).show();
                    }
                };
                // Check If All Fields Not Empty.
                if (!UserName.getText().toString().isEmpty() && !Email.getText().toString().isEmpty()
                        && !Phone.getText().toString().isEmpty() && !Address.getText().toString().isEmpty()) {

                    // Execute All Data Into backgroundWorker.
                    backgroundWorker.execute(type, IDDoc, UserName.getText().toString(), OldPassDoc,
                            NewPassword.getText().toString(), Email.getText().toString(),
                            Phone.getText().toString(), Address.getText().toString());
                } else
                    Toast.makeText(ProfileDoctor.this, "There's empty field!",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get Data For Single Doctor By His UserName.
    private void loadDocData(String response) {
        try {
            JSONArray ResponseJsonArray = new JSONArray(response);

            JSONObject jsonObject = ResponseJsonArray.getJSONObject(0);

            JSONArray jsonArray = jsonObject.getJSONArray("doctorData");

            // 0 For Only Single Doctor.
            JSONObject jO = jsonArray.getJSONObject(0);

            IDDoc = jO.getString("Doc_ID");
            String DoctorUsername = jO.getString("Doc_Name");
            OldPassDoc = jO.getString("Doc_Password");
            String DoctorEmail = jO.getString("Doc_Email");
            String DoctorPhone = jO.getString("Doc_Phone");
            String DoctorAddress = jO.getString("Doc_Address");

            UserName.setText(DoctorUsername);
            Email.setText(DoctorEmail);
            Phone.setText("0" + DoctorPhone);
            Address.setText(DoctorAddress);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
