package com.xekera.Ecommerce.ui.account;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


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
    @BindView(R.id.relativeLayoutParent)
    protected RelativeLayout relativeLayoutParent;
    @BindView(R.id.btnChangePhoto)
    protected Button btnChangePhoto;


    @Inject
    Utils utils;
    @Inject
    ToastUtil toastUtil;
    @Inject
    SessionManager sessionManager;


    View toastView;

    private int GALLERY = 1, CAMERA = 2;

    String strImg = "";


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
        relativeLayoutParent.setOnClickListener(this);
        btnChangePhoto.setOnClickListener(this);
        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                    if (sessionManager.getKeyIsFacebookLogin()) {


                        if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
                            Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
                            imgProfile.setImageBitmap(img);
                        } else if (!utils.isTextNullOrEmpty(sessionManager.getKeyPicture())) {

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
                    if (utils.isTextNullOrEmpty(strImg)) {

                    } else {
                        sessionManager.setTakePhoto(strImg);
                    }
                    sessionManager.createAccountDetails(username, companyName, address, phoneNo, email);
                    toastUtil.showToastShortTime("Saved changes successfully.", toastView);
                    ((BaseActivity) getActivity()).setUserDetails();

                }
                break;

            case R.id.relativeLayoutParent:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtAddress);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtPhoneNo);
                break;

            case R.id.btnChangePhoto:
                requestPermissions();

                if (!mPermissionDenied) {
                    showPictureDialog();

                }
                break;

        }

    }


    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if ((hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED) ||
                (camera != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            mPermissionDenied = false;
            //permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission Allowed
                    int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if ((hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED) &&
                            (camera == PackageManager.PERMISSION_GRANTED)
                    ) {
                        //permissionsGranted();
                        mPermissionDenied = false;

                    } else {
                        mPermissionDenied = true;
                    }
                } else {
                    // showMissingPermissionError();
                    // Permission Denied
                    mPermissionDenied = true;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showPictureDialog() {
        try {

            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Select photo from gallery",
                    "Capture photo from camera"};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    choosePhotoFromGallary();
                                    break;
                                case 1:
                                    takePhotoFromCamera();
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        } catch (Exception e) {

        }
    }

    public void choosePhotoFromGallary() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        } catch (Exception e) {

        }
    }

    private void takePhotoFromCamera() {
        try {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;

                    Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(contentURI),
                            null, options);

                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //  String path = saveImage(bitmap);

                    imgProfile.setImageBitmap(bitmap);
                    //toastUtil.showToastShortTime("Profile picture updated", toastView);
                    strImg = bitMapToString(bitmap);
//                    sessionManager.setTakePhoto(strImg);

                    // ((BaseActivity) getActivity()).setUserDetails();

                } catch (IOException e) {
                    e.printStackTrace();
                    toastUtil.showToastShortTime("Error while updating profile picture", toastView);

                }
            }

        } else if (requestCode == CAMERA) {
            try {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


                ByteArrayOutputStream out = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, out);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

                imgProfile.setImageBitmap(decoded);

                strImg = bitMapToString(decoded);
                //sessionManager.setTakePhoto(strImg);

                // ((BaseActivity) getActivity()).setUserDetails();
                //   saveImage(thumbnail);
                //toastUtil.showToastShortTime("Profile picture updated", toastView);
            } catch (Exception e) {
                toastUtil.showToastShortTime("Error while updating profile picture", toastView);
            }
        }


    }

    private String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private boolean validateInputFields(String userName, String companyName, String address, String phoneNo, String emailID) {
        if (utils.isTextNullOrEmpty(userName)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.username_error_login), toastView);
            return false;
        }
        if (utils.isTextNullOrEmpty(address)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.house_no_error), toastView);
            return false;
        }
        if (utils.isTextNullOrEmpty(phoneNo)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.phone_error), toastView);
            return false;
        }
        if (utils.isTextNullOrEmpty(emailID)) {
            toastUtil.showToastShortTime(utils.getStringFromResourceId(R.string.email_error), toastView);
            return false;
        }

        return true;
    }
}
