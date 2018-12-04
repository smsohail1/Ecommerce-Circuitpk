package com.xekera.Ecommerce.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;


public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String IS_LOGIN = "isLoggedIn";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_USER_PHONE = "user_phon";
    private static final String KEY_USER_EMAIL = "user_email";

    private static final String KEY_LATITUDE = "delivery_latitude";
    private static final String KEY_LONGITUDE = "delivery_longitude";
    private static final String KEY_PLACE_NAME = "delivery_place_name";


    @Inject
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppConstants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String username, String phoneno, String userPassword, String email) {
        //editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_USER_PHONE, phoneno);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.putString(KEY_USER_EMAIL, email);
        editor.commit();
    }

    public void createDeliverySession(String latitude, String longitude, String placeName) {
        editor.putString(KEY_LATITUDE, latitude);
        editor.putString(KEY_LONGITUDE, longitude);
        editor.putString(KEY_PLACE_NAME, placeName);
        editor.commit();
    }

    public void removeAddressSession() {
        editor.remove(KEY_LATITUDE);
        editor.remove(KEY_LONGITUDE);
        editor.remove(KEY_PLACE_NAME);
        editor.apply();
        editor.commit();
    }

    public void logoutUser() {
        editor.remove(IS_LOGIN);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_PHONE);
        editor.remove(KEY_USER_PASSWORD);
        editor.remove(KEY_USER_EMAIL);
        editor.apply();
        editor.commit();
    }

    public String getKeyLatitude() {
        return pref.getString(KEY_LATITUDE, "");

    }

    public String getKeyLongitude() {
        return pref.getString(KEY_LONGITUDE, "");

    }

    public String getKeyPlaceName() {
        return pref.getString(KEY_PLACE_NAME, "");
    }

    public void setLatitude(String latitude) {
        editor.putString(KEY_LATITUDE, latitude);
        editor.commit();
    }


    public void setLongitude(String longitude) {
        editor.putString(KEY_LONGITUDE, longitude);
        editor.commit();
    }


    public void setAddress(String address) {
        editor.putString(KEY_PLACE_NAME, address);
        editor.commit();
    }

    public void clearAllValuesAndShowLoginScreen() {
        editor.remove(IS_LOGIN);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_PASSWORD);
        editor.remove(KEY_USER_PHONE);
        editor.apply();
        editor.commit();
    }

    public void clearData() {
        editor.remove(IS_LOGIN);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_PHONE);
        editor.remove(KEY_USER_PASSWORD);
        editor.remove(KEY_USER_EMAIL);
        editor.apply();
        editor.commit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public String getusername() {
        return pref.getString(KEY_USER_NAME, "");
    }

    public String getphoneno() {
        return pref.getString(KEY_USER_PHONE, "");
    }

    public String getuserPassword() {
        return pref.getString(KEY_USER_PASSWORD, "");
    }


    public void setEmail(String courieremail) {
        editor.putString(KEY_USER_EMAIL, courieremail);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }


}

















