package com.campaign.sey;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_REGISTERED = "IsRegistered";
    private static final String IS_VERIFIED = "IsVerified";
    private static final String PHONE = "phone";
    private static final String IMAGE = "image";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOCATION = "location";
    private static final String DROP_LOCATION = "drop_location";
    private static final String PICK_LOCATION = "pick_location";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isRegistered() {
        return pref.getBoolean(IS_REGISTERED, false);
    }

    public void setRegistered(boolean isRegistered) {
        editor.putBoolean(IS_REGISTERED, isRegistered);
        editor.commit();
    }

    public boolean isVerified() {
        return pref.getBoolean(IS_VERIFIED, true);
    }

    public void setVerified(boolean isVerified) {
        editor.putBoolean(IS_VERIFIED, isVerified);
        editor.commit();
    }

    public String getUserPhone() {
        return pref.getString(PHONE, "");
    }

    public void setUserPhone(String UserPhone) {
        editor.putString(PHONE, UserPhone);
        editor.commit();
    }


    public String getImageUrl() {
        return pref.getString(IMAGE, "");
    }

    public void setImageUrl(String image) {
        editor.putString(IMAGE, image);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(NAME, "");
    }

    public void setUserName(String UserName) {
        editor.putString(NAME, UserName);
        editor.commit();
    }

    public String getUserLocation() {
        return pref.getString(LOCATION, "");
    }

    public void setUserLocation(String s) {
        editor.putString(LOCATION, s);
        editor.commit();
    }



    public String getUserEmail() {
        return pref.getString(EMAIL, "");
    }

    public void setUserEmail(String s) {
        editor.putString(EMAIL, s);
        editor.commit();
    }



    public String getDropLocation() {
        return pref.getString(DROP_LOCATION, "");
    }

    public void setDropLocation(String s) {
        editor.putString(DROP_LOCATION, s);
        editor.commit();
    }



    public String getPickLocation() {
        return pref.getString(PICK_LOCATION, "");
    }

    public void setPickLocation(String s) {
        editor.putString(PICK_LOCATION, s);
        editor.commit();
    }


}
