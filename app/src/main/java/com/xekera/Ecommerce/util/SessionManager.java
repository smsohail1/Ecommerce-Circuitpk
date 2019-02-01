package com.xekera.Ecommerce.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;


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

    private static final String KEY_USER_TAKE_PHOTO = "img_take_photo";
    private static final String KEY_IS_SIGNUP = "is_signup";
    private static final String KEY_IS_FACEBOOK_LOGIN = "is_facebook_login";

    private static final String KEY_PICTURE = "picture";
    private static final String KEY_IS_FAVOURITE_LIST = "favourite_list";


    public static String KEY_CARD_NUMBER = "card_no";
    public static String KEY_CARD_EXPIRY_DATE = "card_expiry_date_detail";
    public static String KEY_CARD_CVC = "card_cvc_no";


    private static final String KEY_LATITUDE = "delivery_latitude";
    private static final String KEY_LONGITUDE = "delivery_longitude";
    private static final String KEY_PLACE_NAME = "delivery_place_name";


    private static final String KEY_COMPANY_NAME = "company_name";

    private static final String KEY_ADDRESS = "address";

    @Inject
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppConstants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String username, String phoneno, String userPassword, String email, boolean isSignUp,
                                   boolean isLoginViaFacebook, String fbImageUrl) {
        //editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_USER_PHONE, phoneno);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putBoolean(KEY_IS_SIGNUP, isSignUp);
        editor.putBoolean(KEY_IS_FACEBOOK_LOGIN, isLoginViaFacebook);
        editor.putString(KEY_PICTURE, fbImageUrl);

        editor.commit();
    }

    public void createCreditCardSession(String cardNumber, String expiryDate, String CVCNumber) {
        editor.putString(KEY_CARD_NUMBER, cardNumber);
        editor.putString(KEY_CARD_EXPIRY_DATE, expiryDate);
        editor.putString(KEY_CARD_CVC, CVCNumber);
        editor.commit();

    }


    public void createSignUpSession(String username, String phoneno, String userPassword, String email, boolean isSignUp
    ) {
        //editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_USER_PHONE, phoneno);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putBoolean(KEY_IS_SIGNUP, isSignUp);

        editor.commit();
    }

    public void createLogin(String username, String phoneno, String userPassword, String email, boolean isSignUp,
                            boolean isLoginViaFacebook, String fbImageUrl, boolean isLoggedIn) {
        //editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_USER_PHONE, phoneno);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putBoolean(KEY_IS_SIGNUP, isSignUp);
        editor.putBoolean(KEY_IS_FACEBOOK_LOGIN, isLoginViaFacebook);
        editor.putString(KEY_PICTURE, fbImageUrl);
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
    }


    public void createDeliverySession(String latitude, String longitude, String placeName) {
        editor.putString(KEY_LATITUDE, latitude);
        editor.putString(KEY_LONGITUDE, longitude);
        editor.putString(KEY_PLACE_NAME, placeName);
        editor.commit();
    }


    public void createAccountDetails(String username, String companyName, String address, String phoneNo, String email) {
        editor.putString(KEY_USER_NAME, username);
        editor.putString(KEY_COMPANY_NAME, companyName);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_USER_PHONE, phoneNo);
        editor.putString(KEY_USER_EMAIL, email);
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

    public String getKeyPicture() {
        return pref.getString(KEY_PICTURE, "");

    }

    public void setKeyPicture(String picture) {
        editor.putString(KEY_PICTURE, picture);
        editor.commit();
    }


    public String getKeyPlaceName() {
        return pref.getString(KEY_PLACE_NAME, "");
    }

    public void setLatitude(String latitude) {
        editor.putString(KEY_LATITUDE, latitude);
        editor.commit();
    }

    public boolean getKeyIsSignUp() {
        return pref.getBoolean(KEY_IS_SIGNUP, false);
    }

    public void setIsSignUp(boolean isSignUp) {
        editor.putBoolean(KEY_IS_SIGNUP, isSignUp);
        editor.commit();
    }


    public boolean getKeyIsFacebookLogin() {
        return pref.getBoolean(KEY_IS_FACEBOOK_LOGIN, false);
    }

    public void setIsFacebookLogin(boolean isFacebookLogin) {
        editor.putBoolean(KEY_IS_FACEBOOK_LOGIN, isFacebookLogin);
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


    public void clearAll() {
        editor.remove(IS_LOGIN);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_PHONE);
        editor.remove(KEY_USER_PASSWORD);
        editor.remove(KEY_USER_EMAIL);
        editor.remove(KEY_USER_TAKE_PHOTO);
        editor.remove(KEY_IS_SIGNUP);
        editor.remove(KEY_IS_FACEBOOK_LOGIN);
        editor.remove(KEY_PICTURE);
        editor.remove(KEY_CARD_NUMBER);
        editor.remove(KEY_CARD_EXPIRY_DATE);
        editor.remove(KEY_CARD_CVC);
        editor.remove(KEY_COMPANY_NAME);
        editor.remove(KEY_ADDRESS);

        editor.apply();
        editor.commit();
    }


    public void removeCreditCardSession() {

        editor.remove(KEY_CARD_NUMBER);
        editor.remove(KEY_CARD_EXPIRY_DATE);
        editor.remove(KEY_CARD_CVC);

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

    public boolean isSignUp() {
        return pref.getBoolean(KEY_IS_SIGNUP, false);
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

    public void setTakePhoto(String img) {
        editor.putString(KEY_USER_TAKE_PHOTO, img);
        editor.commit();
    }

    public String getTakePhoto() {
        return pref.getString(KEY_USER_TAKE_PHOTO, "");
    }

    public String getEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    public String getCompanyName() {
        return pref.getString(KEY_COMPANY_NAME, "");
    }


    public void setCompanyName(String companyName) {
        editor.putString(KEY_COMPANY_NAME, companyName);
        editor.commit();
    }

    public String getDeliveryAddress() {
        return pref.getString(KEY_ADDRESS, "");
    }


    public void setDeliveryAddress(String address) {
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }

    public void setIsFavouriteList(Set<String> list) {
        editor.putStringSet(KEY_IS_FAVOURITE_LIST, list)
                .commit();
    }

    public Set<String> getIsFavouriteList() {
        return pref.getStringSet(KEY_IS_FAVOURITE_LIST, new HashSet<String>());
    }


    public String getCVCNumber() {
        return pref.getString(KEY_CARD_CVC, "");
    }

    public String getCardNumber() {
        return pref.getString(KEY_CARD_NUMBER, "");
    }

    public String getExpiryDate() {
        return pref.getString(KEY_CARD_EXPIRY_DATE, "");
    }


}
