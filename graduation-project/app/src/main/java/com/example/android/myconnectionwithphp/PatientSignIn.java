package com.example.android.myconnectionwithphp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.myconnectionwithphp.Common.Common;
import com.rengwuxian.materialedittext.MaterialEditText;

public class PatientSignIn extends AppCompatActivity {

    // Init EditText & Buttons.
    MaterialEditText UserName, Password;
    Button LogIn, SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_in);

        // Get Views By IDs.
        UserName = (MaterialEditText) findViewById(R.id.edit_username);
        Password = (MaterialEditText) findViewById(R.id.edit_password);
        LogIn = (Button) findViewById(R.id.btn_signin);
        SignUp = (Button) findViewById(R.id.btn_signup);


        if (Common.isConnectedToInternet(PatientSignIn.this)) {
            // When Press On LogIn Button.
            LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get User's Input.
                    final String username = UserName.getText().toString();
                    final String password = Password.getText().toString();
                    String type = "login_patient";

                    @SuppressLint("StaticFieldLeak")
                    BackgroundWorker backgroundWorker = new BackgroundWorker(PatientSignIn.this) {
                        protected void onPostExecute(String result) {
                            // Check If Result Of Server Has exist Word.
                            if (result.contains("exist")) {
                                // Goto PatientHome Activity.
                                Intent intent = new Intent(PatientSignIn.this, PatientHome.class);
                                // Take With You UserNAme.
                                intent.putExtra("userName", username);
                                // Clear All Previous Activity To Prevent Crashing.
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Start Activity.
                                startActivity(intent);

                                // Reset UserName & Password.
                                UserName.setText("");
                                Password.setText("");

                                // OtherWist Result Of Server Has Not exist Word.
                            } else
                                Toast.makeText(PatientSignIn.this, "Please enter valid userName/NewPassword!",
                                        Toast.LENGTH_SHORT).show();
                        }
                    };
                    // Execute This Params.
                    backgroundWorker.execute(type, username, password);

                }
            });

            // When Press On SignUp Button
            SignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Goto PatientSignUp Activity.
                    Intent intent = new Intent(PatientSignIn.this, PatientSignUp.class);
                    startActivity(intent);
                }
            });
        } else
            // If Internet Connection Not Available.
            Toast.makeText(PatientSignIn.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();

    }
}
