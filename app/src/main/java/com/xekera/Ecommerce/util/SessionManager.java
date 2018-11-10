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

    @Inject
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppConstants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String username, String phoneno, String userPassword, String email) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_USER_PHONE, phoneno);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.putString(KEY_USER_EMAIL, email);
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

    public void clearAllValuesAndShowLoginScreen(){
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



    public void setCourieremail(String courieremail) {
        editor.putString(KEY_USER_EMAIL, courieremail);
        editor.commit();
    }

    public String getCourieremail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

}

















