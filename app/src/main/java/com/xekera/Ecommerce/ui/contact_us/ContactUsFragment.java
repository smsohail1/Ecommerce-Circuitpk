package com.xekera.Ecommerce.ui.contact_us;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.adapter.RelationAdapter;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.xekera.Ecommerce.util.AppConstants.supportEmail;

public class ContactUsFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.edtUsername)
    protected EditText edtUsername;
    @BindView(R.id.edtNotes)
    protected EditText edtNotes;
    @BindView(R.id.edtCompanyName)
    protected EditText edtCompanyName;
    @BindView(R.id.edtUserPhoneNo)
    protected EditText edtUserPhoneNo;
    @BindView(R.id.edtEmailAddress)
    protected EditText edtEmailAddress;
    @BindView(R.id.imgCallMobile)
    protected ImageView imgCallMobile;

    @BindView(R.id.spinnerTownCity)
    protected Spinner spinnerTownCity;

    @BindView(R.id.btnSend)
    protected Button btnSend;

    @BindView(R.id.relativeLayoutParent)
    protected RelativeLayout relativeLayoutParent;


    View toastView;

    @Inject
    Utils utils;
    @Inject
    ToastUtil toastUtil;
    @Inject
    SnackUtil snackUtil;
    @Inject
    SessionManager sessionManager;

    String contactSuggestionSelectedStr = "";
    List<String> contactSuggestion;


    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        resetForm();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);

        initializeViews(v);


        return v;
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        relativeLayoutParent.setOnClickListener(this);
        imgCallMobile.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        contactSuggestion = Arrays.asList(getResources().getStringArray(R.array.contact_report));

        spinnerTownCity.setAdapter(new RelationAdapter(contactSuggestion));
        spinnerTownCity.setSelection(0);

        spinnerTownCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    contactSuggestionSelectedStr = contactSuggestion.get(position);


                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayoutParent:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtUserPhoneNo);
                utils.hideSoftKeyboard(edtEmailAddress);
                utils.hideSoftKeyboard(edtNotes);
                break;

            case R.id.btnSend:

                String userName = edtUsername.getText().toString();
                String phoneNo = edtUserPhoneNo.getText().toString();
                String email = edtEmailAddress.getText().toString();
                String message = edtNotes.getText().toString();
                String companyName = edtCompanyName.getText().toString();

                if (isFormValid(userName,
                        phoneNo,
                        email,
                        message, contactSuggestionSelectedStr)) {
                    sendEmail("Username: " + userName, "Mobile No: " + phoneNo, "Email: " + email,
                            "Report Message: " + message, companyName, "Topic: " + contactSuggestionSelectedStr);

                }
                break;

            case R.id.imgCallMobile:
                requestPermissions();
                if (mPermissionDenied) {
                    callSupportNumber(AppConstants.support_contact_no);

                }
                break;
        }
    }

    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        int call = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);


        if ((call != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.CALL_PHONE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            mPermissionDenied = true;
            //permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission Allowed
                    int call = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

                    if ((call == PackageManager.PERMISSION_GRANTED)) {
                        //permissionsGranted();
                        mPermissionDenied = true;
                        callSupportNumber(AppConstants.support_contact_no);

                    } else {
                        mPermissionDenied = false;
                    }
                } else {
                    // Permission Denied
                    mPermissionDenied = false;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callSupportNumber(String mobileNo) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            //  toastUtil.showToastLongTime("Security Exception - Please Enable Permissions");
        }
    }

    private boolean isFormValid(String username, String mobileNo, String email, String message, String contactSuggestionSelectedStr) {

        if (utils.isTextNullOrEmpty(contactSuggestionSelectedStr)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.contact_us_suggestion_error), toastView);
            return false;
        }

        if (contactSuggestionSelectedStr.equalsIgnoreCase("Choose")) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.contact_us_suggestion_error), toastView);
            return false;
        }

        if (utils.isTextNullOrEmpty(username)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.username_error_login), toastView);
            return false;
        }
        if (utils.isTextNullOrEmpty(mobileNo)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.contact_us_phone_error), toastView);

            return false;
        }
        if (mobileNo.length() < 11) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.phone_no_length_error), toastView);
            return false;
        }

        if (utils.isTextNullOrEmpty(email)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.email_address_error), toastView);
            return false;
        }

        if (!isValidEmail(email)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.email_address_invalid_error), toastView);
            return false;
        }

        if (utils.isTextNullOrEmpty(message)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.contact_us_message), toastView);
            return false;
        }
        return true;
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void sendEmail(String username, String mobileNo, String email, String message, String companyName, String contactSuggestionSelectedStr) {
        try {


            Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);
            emailIntent.setType("plain/text");
            emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{supportEmail});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Cicuit.pk Report");
            if (utils.isTextNullOrEmpty(companyName)) {
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, username + "\n\n" + mobileNo + "\n\n" + email + "\n\n" +
                        message);
            } else {
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        contactSuggestionSelectedStr + "\n\n" +
                                username + "\n\n" +
                                "Company Name: " + companyName + "\n\n" +
                                mobileNo + "\n\n" + email + "\n\n" +
                                message);
            }
            //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Myimage.jpeg"));
            //  emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Exception e) {

        }
    }

    private void resetForm() {
        edtUsername.setText("");
        edtCompanyName.setText("");
        edtUserPhoneNo.setText("");
        edtEmailAddress.setText("");
        edtNotes.setText("");

    }
}
