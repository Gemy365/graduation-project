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

public class PharmacistSignIn extends AppCompatActivity {

    // public static Allow Me Use It Into QRCodeActivity.
    public static MaterialEditText UserName, Password;

    Button LogIn, ScanQRCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_sign_in);

        UserName = (MaterialEditText) findViewById(R.id.edit_username);
        Password = (MaterialEditText) findViewById(R.id.edit_password);
        LogIn = (Button) findViewById(R.id.btn_signin);
        ScanQRCodeBtn = (Button) findViewById(R.id.scan_btn);

        if (Common.isConnectedToInternet(PharmacistSignIn.this)) {
            // When Click On Scan Btn.
            ScanQRCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PharmacistSignIn.this, QRCodeActivity.class);
                    startActivity(intent);
                }
            });

            // When Click On LogIn Btn.
            LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = UserName.getText().toString();
                    final String password = Password.getText().toString();
                    String type = "login_pharmacist";

                    @SuppressLint("StaticFieldLeak")
                    BackgroundWorker backgroundWorker = new BackgroundWorker(PharmacistSignIn.this) {
                        protected void onPostExecute(String result) {
//                            result = result.replaceAll("\\<.*?>(.*?)\\<.*?>", "");  //Removes all items between brackets
//                            result = result.replaceAll("<(.*?)\\>", "");  //Removes all items in brackets
//                            result = result.replaceAll("<(.*?)\\\n", ""); //Must be underneath
//                            result = result.replaceFirst("(.*?)\\>", "");    //Removes any connected item to the last bracket
//                            result = result.replaceAll("&nbsp;", "");        // Remove any space.
//                            result = result.replaceAll("&amp;", "");         // Remove any & char.

//                      Toast.makeText(context, "" + result, Toast.LENGTH_LONG).show();
                            // check the server ok
                            // toast exist mohy found before profile doctor
                            if (result.contains("exist")) {
                                Intent intent = new Intent(PharmacistSignIn.this, PharmacistHome.class);
                                // putExtra take user name == mohy
                                intent.putExtra("userName", username);
//                                 intent.putExtra("password", password);
                                //flag responsible create loop in activity and not crash program
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                // Reset UserName & Password.
                                UserName.setText("");
                                Password.setText("");
                            } else
                                Toast.makeText(PharmacistSignIn.this, "Please enter valid userName/NewPassword!",
                                        Toast.LENGTH_SHORT).show();
                        }
                    };
                    backgroundWorker.execute(type, username, password);
                }
            });

    } else
            // If Not Available.
            Toast.makeText(PharmacistSignIn.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();

}
}
