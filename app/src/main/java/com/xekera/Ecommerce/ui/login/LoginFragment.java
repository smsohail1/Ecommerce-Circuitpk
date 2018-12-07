package com.xekera.Ecommerce.ui.login;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.picasso.Picasso;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.dashboard.DashboardActivity;
import com.xekera.Ecommerce.ui.signup.SignUpActivity;
import com.xekera.Ecommerce.ui.signup.SignupFragment;
import com.xekera.Ecommerce.util.ProgressCustomDialogController;
import com.xekera.Ecommerce.util.SnackUtil;
import com.xekera.Ecommerce.util.ToastUtil;
import com.xekera.Ecommerce.util.Utils;

import javax.inject.Inject;

import com.facebook.appevents.AppEventsLogger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginMVP.View {
    @BindView(R.id.edtUsername)
    protected EditText edtUsername;
    //    @BindView(R.id.edtPassword)
//    protected EditText edtPassword;
    @BindView(R.id.customEdtPasswordHideShow)
    protected EditText customEdtPasswordHideShow;
    @BindView(R.id.btnSignIn)
    protected Button btnSignIn;
    @BindView(R.id.btnCreateAccount)
    protected Button btnCreateAccount;
    @BindView(R.id.fb_button)
    protected LoginButton fb_button;

    CallbackManager callbackManager;

    @Inject
    Utils utils;
    @Inject
    ToastUtil toastUtil;
    @Inject
    SnackUtil snackUtil;
    @Inject
    protected LoginMVP.Presenter presenter;

    private ProgressCustomDialogController progressDialogControllerPleaseWait;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        //   requestPermissionsFromUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.presenter.setView(this);
        try {
            // setTitle();
            //   hideLoginIcon();
            // hideShoppingCartIcon();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //        if(checkPlayServices()){
//            turnGPSOn();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViews(v);

        return v;
    }

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(getString(R.string.login_title));
    }

    public void hideShoppingCartIcon() {
        ((BaseActivity) getActivity()).hideShoppingCartIcon();
    }


    public void hideLoginIcon() {
        ((BaseActivity) getActivity()).hideLoginIcon();
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        this.presenter.setView(this);
        // ((BaseActivity) getActivity()).hideBottomNavigation();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnSignIn.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        utils.showSoftKeyboard(edtUsername);

        callFacebook();


    }


    private void callFacebook() {
        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {
            //  Picasso.with(getActivity()).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            // Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }

        fb_button.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        fb_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            // txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            //txtEmail.setText(email);
                            //Picasso.with(MainActivity.this).load(image_url).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn: {

                String userName = edtUsername.getText().toString();
                String password = customEdtPasswordHideShow.getText().toString();

                presenter.onClickBtnSignIn(userName, password);
                break;
            }
            case R.id.btnCreateAccount: {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((BaseActivity) getActivity()).addFragment(new SignupFragment());

                    }
                }, 400);


                break;
            }
        }
    }

    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }

    @Override
    public void hideSoftKeyboard() {
        utils.hideSoftKeyboard(edtUsername);
        //utils.hideSoftKeyboard(edtPassword);

    }

    @Override
    public void showProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.showDialog();
    }

    @Override
    public void hideProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.hideDialog();
    }

    @Override
    public void showSnackBarShortTime(String message, View view) {
        snackUtil.showSnackBarShortTime(view, message);
    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }

    @Override
    public void showHomeScreen() {
        // open dashboard screen here
        startActivity(new Intent(getActivity(), DashboardActivity.class));
        getActivity().finish();
    }

    @Override
    public void showSnackBarShortTime(String message) {
        showSnackBarShortTime(message, getView());
    }

    @Override
    public void loggedInSuccessfully() {
        showToastShortTime("Logged In successfully.");
        (((BaseActivity) getActivity())).setUserDetails();

        final LoginFragment loginFragment = new LoginFragment();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                (((BaseActivity) getActivity())).popBackFromStack(loginFragment);

            }
        }, 100);


    }

    private void turnGPSOn() {
        try {
            final LocationManager manager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            } else {
                //utils.startGPSTrackerService(getActivity().getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // PERMISSIONS CODE
    /*final private int REQUEST_CODE_ASK_PERMISSIONS = 125;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionsFromUser() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
        int hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        int hasReceiveSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
        int hasReadSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
        int hasSendSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);


        if ((hasCameraPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED) ||
                (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) ||
                (hasAccessFineLocationPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) ||
                (hasReceiveSMSPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasReadSMSPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasSendSMSPermission != PackageManager.PERMISSION_GRANTED)
                ) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.SEND_SMS
                    },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            //permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[2] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[3] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[4] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[5] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[6] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[7] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[8] == PackageManager.PERMISSION_GRANTED)
                        ) {
                    // Permission Allowed
                    int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
                    int hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
                    int hasCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
                    int hasReceiveSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
                    int hasReadSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
                    int hasSendSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

                    if ((hasCameraPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasReadPhoneStatePermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasAccessFineLocationPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasCallPhonePermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasReceiveSMSPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasReadSMSPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasSendSMSPermission == PackageManager.PERMISSION_GRANTED)
                            ) {
                        //permissionsGranted();
                    }
                } else {
                    // Permission Denied
                    mPermissionDenied = true;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
*/

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        toastUtil.showToastLongTime("These permissions are required");
        getActivity().finish();
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                toastUtil.showToastLongTime(utils.getStringFromResourceId(R.string.update_play_services));
                getActivity().finish();
            }
            return false;
        }
        return true;
    }
}














