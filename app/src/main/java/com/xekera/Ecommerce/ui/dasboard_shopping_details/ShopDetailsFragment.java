package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.varunest.sparkbutton.SparkButton;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailsFragment extends Fragment implements ShopDetailsMVP.View, ShopDetailsAdapter.IShopDetailAdapter {

    @BindView(R.id.edtSearchProduct)
    protected EditText edtSearchProduct;
    @BindView(R.id.recyclerViewProductDetails)
    protected RecyclerView recyclerViewProductDetails;

    @Inject
    protected ShopDetailsMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    public static final String KEY_SHOP_NAME_DETAILS = "shop_details_name";


    ShopDetailsAdapter shopDetailsAdapter;

    List<ShoppingDetailModel> shopDetails;
    String productName = "";

    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    public ShopDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  super.onActivityResult(requestCode, resultCode, data);

        // if (resultCode == RESULT_OK) {
//        if (requestCode == 55) {
//            int addID = data.getIntExtra("addressID", 0);
//            String addressLine = data.getStringExtra("addressLine");
//            // }
//        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        productName = getArguments().getString(KEY_SHOP_NAME_DETAILS, "");


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        ((BaseActivity) getActivity()).hideBottomNavigation();
        List<String> img;
        img = new ArrayList<>();
        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
        img.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
        img.add("https://dzvfs5sz5rprz.cloudfront.net/media/catalog/product/cache/1/image/1200x/9df78eab33525d08d6e5fb8d27136e95/m/e/mega_shop_hidden_gsm_supported_voice_recorder_black.jpg");
        img.add("https://dzvfs5sz5rprz.cloudfront.net/media/catalog/product/cache/1/image/1200x/9df78eab33525d08d6e5fb8d27136e95/m/e/mega_shop_usb_range_extender_black.jpg");
        img.add("https://www.bhphotovideo.com/images/images2000x2000/kingston_dt100g3_16gb_16gb_data_traveler_100_964342.jpg");
        img.add("https://a.pololu-files.com/picture/0J1479.1200.jpg?6d28c13f103617525228f0936ec16321");

        edtSearchProduct.setText("");
        shopDetails.clear();
        shopDetails.add(new ShoppingDetailModel("Arduino", "5000", false, 1, img));

        shopDetails.add(new ShoppingDetailModel("Resberi Pi", "10000", false, 1, img));

        shopDetails.add(new ShoppingDetailModel("LED", "300", false, 1, img));

        shopDetails.add(new ShoppingDetailModel("Jumper Wire", "800", false, 1, img));

        shopDetails.add(new ShoppingDetailModel("Bread Board", "200", false, 1, img));

        shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this);
        showRecylerViewProductsDetail(shopDetailsAdapter);


        try {
//            setTitle();
//            //   showBackImageIcon();
//            // hideHumbergIcon();
//            showBackImageIcon();
//            //hideActionBar();
//            hideLoginIcon();

//            setTitle();
//            hideHumbergIcon();
//            showBackImageIcon();
//            hideLoginIcon();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(productName);
    }


    public void hideLoginIcon() {
        ((BaseActivity) getActivity()).hideLoginIcon();
    }


    public void hideHumbergIcon() {
        ((BaseActivity) getActivity()).hideHumberIcon();

    }

    public void showBackImageIcon() {
        ((BaseActivity) getActivity()).showBackImageIcon();

    }


    public void hideActionBar() {
        ((BaseActivity) getActivity()).hideActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_details, container, false);

        initializeViews(v);


        return v;
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        ((BaseActivity) getActivity()).hideBottomNavigation();

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewProductDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        //  setTitle();
        //hideLoginIcon();
        // showBackImageIcon();

        edtSearchProduct.setText("");


        shopDetails = new ArrayList<ShoppingDetailModel>();
//        shopDetails.add(new ShoppingDetailModel("Arduino", "5000", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Resberi Pi", "10000", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("LED", "300", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Jumper Wire", "800", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Bread Board", "200", false, 1));
//
//        shopDetails.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");

        // shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this);
        //showRecylerViewProductsDetail(shopDetailsAdapter);


        //  presenter.setRecylerViewItems(getActivity(), shopDetails);
        //shopDetailsAdapter = new ShopDetailsAdapter();


        edtSearchProduct.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                try {


                    String text = edtSearchProduct.getText().toString().toLowerCase(Locale.getDefault());

                    shopDetailsAdapter.filter(text);

                } catch (Exception ex) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

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
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
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
    public void showRecylerViewProductsDetail(ShopDetailsAdapter shopDetailsAdapter) {
        recyclerViewProductDetails.setAdapter(shopDetailsAdapter);

    }


    public ShopDetailsFragment newInstance(String ProductName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SHOP_NAME_DETAILS, ProductName);
        ShopDetailsFragment fragment = new ShopDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAddButtonClick(ShoppingDetailModel productItems) {

    }

    @Override
    public void onViewDetailsButtonClick(ShoppingDetailModel productItems) {

    }

//    @Override
//    public void onFavouriteButtonClick(ShoppingDetailModel productItems) {
//        String dd;
//        dd = "";
//        if (productItems.isFavourite()) {
//            showSnackBarShortTime("Add item to favourites.", getView());
//        } else {
//            showSnackBarShortTime("Remove item from favourites.", getView());
//
//        }
//
//    }

    @Override
    public void onIncrementButtonClick(ShoppingDetailModel productItems) {

    }

    @Override
    public void onDecrementButtonClick(ShoppingDetailModel productItems) {

    }

    @Override
    public void onCardClick(final ShoppingDetailModel productItems, final Bitmap bitmapImg) {
        utils.hideSoftKeyboard(edtSearchProduct);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ShopCardSelectedFragment shopCardSelectedFragment = new ShopCardSelectedFragment();
                ((BaseActivity) getActivity()).replaceFragment(shopCardSelectedFragment.newInstance(productItems, bitmapImg));
            }
        }, 300);

    }

    @Override
    public void shareItemsDetails(ShoppingDetailModel productItems, Bitmap bitmapImg) {
        requestPermissions();
        if (!mPermissionDenied) {
            try {

                Intent share = new Intent(Intent.ACTION_SEND);
                // share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                share.setType("image/jpeg");

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "title");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);


                OutputStream outstream;
                try {
                    outstream = getActivity().getContentResolver().openOutputStream(uri);
                    bitmapImg.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                    outstream.close();
                } catch (Exception e) {
                    System.err.println(e.toString());
                }

                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, "Product Name: " + productItems.getProductName() + "\n" +
                        "Price: " + productItems.getProductPrice());
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(share, "Share with friends"));

            } catch (Exception e) {
                Log.d("error", e.getMessage());
                showMissingPermissionError();
            }
        } else {
            showMissingPermissionError();
        }
    }


    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if ((hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if ((hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED)
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


    /**
     * Displays a dialog with error message explaining that the phone permission is missing.
     */
    private void showMissingPermissionError() {
        toastUtil.showToastLongTime("Please enable Read/Write Storage permission.");
    }


//    public void notifyAdapter(ShoppingDetailModel shoppingDetailModel) {

//    }


}
