package com.example.android.myconnectionwithphp.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

    // Method To Check InterNet.
    public  static boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo info[] = connectivityManager.getAllNetworkInfo();

            if(info != null){

                // Infinity info, When Network Is Connected, & Always Return True.
                for(int i = 0; i < info.length; i++){
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        // If No Connection.
        return false;
    }
}
