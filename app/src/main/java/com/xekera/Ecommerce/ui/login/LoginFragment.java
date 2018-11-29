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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
        ((BaseActivity) getActivity()).hideBottomNavigation();

        btnSignIn.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        utils.showSoftKeyboard(edtUsername);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn: {

                String userName = edtUsername.getText().toString();
                String password = customEdtPasswordHideShow.getText().toString();

                presenter.onClickBtnSignIn(userName, password, view);
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
    public void loggedInSuccessfully() {
        showSnackBarShortTime("Logged In successfully.", getView());
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














