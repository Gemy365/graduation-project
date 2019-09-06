package com.example.android.myconnectionwithphp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myconnectionwithphp.Common.Common;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class MainActivity extends AppCompatActivity {
    // Init Views.
    TextView Patient, Doctor, Pharmacist;

    // For Refresh food.
    SwipeRefreshLayout swipeRefreshLayout;

    // Global Var Of Handler.
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Make Constructor From Handler.
        mHandler = new Handler();

        // Init Call Runnable Method After 0.5sec For Animation.
        mHandler.postDelayed(m_Runnable,500);

        Patient = (TextView) findViewById(R.id.text_patient);
        Doctor = (TextView) findViewById(R.id.text_doctor);
        Pharmacist = (TextView) findViewById(R.id.text_pharmacist);

        // Get View By ID For Refresh View When Get Menu Down By Your Finger To Refresh.
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        // Set Colors For Refresh If The Refresh Need Long Time.
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // With Refreshing.
                // Check If There's InterNet Connection.
                if (Common.isConnectedToInternet(MainActivity.this)) {
                    Patient.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, PatientSignIn.class);
                            startActivity(intent);
                        }
                    });

                    Doctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, DoctorSignIn.class);
                            startActivity(intent);
                        }
                    });

                    Pharmacist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, PharmacistSignIn.class);
                            startActivity(intent);
                        }
                    });
                    // Stop Loop Of Refresh.
                    swipeRefreshLayout.setRefreshing(false);
                }
                // With Refreshing.
                // Check If There's NOT InterNet Connection.
                else {
                    Patient.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // If Not Available.
                            Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Doctor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // If Not Available.
                            Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Pharmacist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // If Not Available.
                            Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // If Not Available.
                    Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                }
//                // Stop Refreshing After 2sec.
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Stop Loop Of Refresh.
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
            }
        });
        // WithOut Refreshing.
        // Check If There's InterNet Connection.
        if (Common.isConnectedToInternet(MainActivity.this)) {
            Patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PatientSignIn.class);
                    startActivity(intent);
                }
            });

            Doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DoctorSignIn.class);
                    startActivity(intent);
                }
            });

            Pharmacist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PharmacistSignIn.class);
                    startActivity(intent);
                }
            });
            // Stop Loop Of Refresh.
            swipeRefreshLayout.setRefreshing(false);
        }
        // WithOut Refreshing.
        // Check If There's NOT InterNet Connection.
        else {
            Patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If Not Available.
                    Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                }
            });

            Doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If Not Available.
                    Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                }
            });

            Pharmacist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If Not Available.
                    Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
                }
            });
            // If Not Available.
            Toast.makeText(MainActivity.this, "Please check your connection..!!", Toast.LENGTH_SHORT).show();
        }
//        // Stop Refreshing After 2sec.
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Stop Loop Of Refresh.
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2000);
    }

    // Auto Refresh Activity Method.
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            // get View By ID.
            HTextView hTextView = (HTextView) findViewById(R.id.text);

            // The Fake Text You Want Appear Before Making Animation.
            hTextView.setText("IIITERIAQIII");

            // be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
            // hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/font-name.ttf"));

            // U Can Change LINE To Another Type Of Animations.
            hTextView.setAnimateType(HTextViewType.LINE);

            // The Original Text You Want Appear After Making Animation.
            hTextView.animateText("TERIAQ"); // animate

            // Call Runnable Method Every 2sec.
            mHandler.postDelayed(m_Runnable, 2000);
        }

    };//end runnable
}