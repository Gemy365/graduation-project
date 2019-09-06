package com.example.android.myconnectionwithphp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

// Implements ZXingScannerView.ResultHandler.
public class QRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        // Call ZXingScannerView Constructor & Send This [QRCodeActivity] As Param.
        ScannerView = new ZXingScannerView(this);
        // Set Content View Of This Constructor.
        setContentView(ScannerView);
    }

    // Call handleResult Override Method.
    @Override
    public void handleResult(Result result) {

        // Store Text Result Of Scanning Into separated Array Of String To Make Split For Result.
        // UserName-Password So We Need To Split [-].
        String[] separated = result.getText().split("-");

        // Try To Avoid Crash If I Don't Come From PharmacistSignIn Activity.
        try {
            // 0 >> Contains UserName
            DoctorSignIn.UserName.setText(separated[0]);

            // 1 >> Contains Password
            DoctorSignIn.Password.setText(separated[1]);

        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        // Try To Avoid Crash If I Don't Come From DoctorSignIn Activity.
        try {
            // 0 >> Contains UserName
            PharmacistSignIn.UserName.setText(separated[0]);

            // 1 >> Contains Password
            PharmacistSignIn.Password.setText(separated[1]);

        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        // Active Back Pressed To Back To DoctorSignIn.
        onBackPressed();
    }

    // When Start.
    @Override
    protected void onPostResume() {
        super.onPostResume();

        // set Result Handler Take This [QRCodeActivity] As Param.
        ScannerView.setResultHandler(this);
        // Start Camera To Start Scanning.
        ScannerView.startCamera();
    }

    // When Stop
    @Override
    protected void onPause() {
        super.onPause();
        // Stop Camera To Stop Scanning.
        ScannerView.stopCamera();
    }
}
