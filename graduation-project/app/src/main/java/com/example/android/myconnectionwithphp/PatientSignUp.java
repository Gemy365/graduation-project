package com.example.android.myconnectionwithphp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.myconnectionwithphp.Common.Common;
import com.rengwuxian.materialedittext.MaterialEditText;

public class PatientSignUp extends AppCompatActivity {
    // Init EditText.
    MaterialEditText UserName, Password, mPassword, Email, Phone, Address, FullName;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);

        // GetViews By IDs.
        UserName = (MaterialEditText) findViewById(R.id.edit_username);
        Password = (MaterialEditText) findViewById(R.id.edit_password);
        mPassword = (MaterialEditText) findViewById(R.id.edit_matched_password);
        Email = (MaterialEditText) findViewById(R.id.edit_email);
        Phone = (MaterialEditText) findViewById(R.id.edit_phone);
        Address = (MaterialEditText) findViewById(R.id.edit_address);
        FullName = (MaterialEditText) findViewById(R.id.edit_full_name);

        Register = (Button) findViewById(R.id.btn_register);

        if (Common.isConnectedToInternet(PatientSignUp.this)) {

            // When Click On Register Button.
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get All Of User's Input.
                    String username = UserName.getText().toString();
                    String password = Password.getText().toString();
                    String matchedPassword = mPassword.getText().toString();
                    String email = Email.getText().toString();
                    String phone = Phone.getText().toString();
                    String address = Address.getText().toString();
                    String fullName = FullName.getText().toString();

                    // Check Into BackgroundWorker Which IF Statement I Will Work With.
                    String type = "signup_patient";

                    // Call BackgroundWorker & Execute Params.
                    BackgroundWorker backgroundWorker = new BackgroundWorker(PatientSignUp.this);
                    backgroundWorker.execute(type, username, password, matchedPassword, email, phone, address, fullName);
                }
            });
        } else
            // If Not Available.
            Toast.makeText(PatientSignUp.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
    }
}
