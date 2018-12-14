package com.xekera.Ecommerce.ui.account;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.util.ProgressCustomDialogController;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.ToastUtil;
import com.xekera.Ecommerce.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.imgProfile)
    protected CircleImageView imgProfile;
    @BindView(R.id.edtUsername)
    protected EditText edtUsername;
    @BindView(R.id.edtCompanyName)
    protected EditText edtCompanyName;
    @BindView(R.id.edtPhoneNo)
    protected EditText edtPhoneNo;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtAddress)
    protected EditText edtAddress;
    @BindView(R.id.btnSaveChanges)
    protected Button btnSaveChanges;
    @BindView(R.id.linearLayoutParent)
    protected LinearLayout linearLayoutParent;

    @Inject
    Utils utils;
    @Inject
    ToastUtil toastUtil;
    @Inject
    SessionManager sessionManager;

    public AccountFragment() {
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
        // ((BaseActivity) getActivity()).hideBottomNavigation();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        initializeViews(v);


        return v;
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        btnSaveChanges.setOnClickListener(this);
        linearLayoutParent.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                    if (sessionManager.getKeyIsFacebookLogin()) {

                        if (!utils.isTextNullOrEmpty(sessionManager.getKeyPicture())) {

                            Picasso.with(getActivity()).load(sessionManager.getKeyPicture()).into(imgProfile);
                        }
                    } else {
                        if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
                            Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
                            imgProfile.setImageBitmap(img);
                        }
                    }
                    edtUsername.setText(sessionManager.getusername());
                    edtPhoneNo.setText(sessionManager.getphoneno());
                    edtEmail.setText(sessionManager.getEmail());
                    edtCompanyName.setText(sessionManager.getCompanyName());
                    edtAddress.setText(sessionManager.getDeliveryAddress());


                } catch (Exception e) {

                }
            }
        }, 200);


    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveChanges:
                String username = edtUsername.getText().toString();
                String companyName = edtCompanyName.getText().toString();
                String address = edtAddress.getText().toString();
                String phoneNo = edtPhoneNo.getText().toString();
                String email = edtEmail.getText().toString();
                if (validateInputFields(username, companyName, address, phoneNo, email)) {
                    sessionManager.createAccountDetails(username, companyName, address, phoneNo, email);
                    toastUtil.showToastShortTime("Saved changes successfully.");
                    ((BaseActivity) getActivity()).setUserDetails();

                }
                break;

            case R.id.linearLayoutParent:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtAddress);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtPhoneNo);
                break;
        }

    }

    private boolean validateInputFields(String userName, String companyName, String address, String phoneNo, String emailID) {
        if (utils.isTextNullOrEmpty(userName)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.username_error_login));
            return false;
        }
        if (utils.isTextNullOrEmpty(address)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.house_no_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(phoneNo)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.phone_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(emailID)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.email_error));
            return false;
        }

        return true;
    }
}
