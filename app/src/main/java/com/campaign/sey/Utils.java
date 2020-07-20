package com.campaign.sey;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.core.location.LocationManagerCompat;

public class Utils {


    static String PERSON = "sey";
    static String VISION = "vision";
    static String ACHIEVEMENT = "achievement";


    static final String HOME_INFO = "home_info";
    static final String CHAT = "chat_room";

    static String EDUCATIONAL_ACHIEVEMENT = "edu_ach";
    static String HEALTH_ACHIEVEMENT = "health_ach";
    static String JOB_ACHIEVEMENT = "job_ach";
    static String INFRASTRUCTURE_ACHIEVEMENT = "ins_ach";

    static String EDUCATIONAL_VISION = "edu_vision";
    static String HEALTH_VISION  = "health_vision";
    static String JOB_VISION  = "job_vision";
    static String INFRASTRUCTURE_VISION = "ins_vision";

//
//    public static void hideProgressDialog(Context context) {
//        ProgressDialog mProgressDialog = new ProgressDialog(context);
//            mProgressDialog.dismiss();
//    }
//
//    public static void showProgressDialog(Context context, String title, String msg) {
//    ProgressDialog.show(context, title, msg, true, true);
//    }


    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
