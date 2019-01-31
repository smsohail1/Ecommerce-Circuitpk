package com.xekera.Ecommerce.ui.history.history_description;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import cdflynn.android.library.checkview.CheckView;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.response.Fulldetail;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderIdDiscriptionResponse;
import com.xekera.Ecommerce.data.rest.response.OrderList;
import com.xekera.Ecommerce.data.rest.response.ProList;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.HistoryDesciptionAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryDesciptionAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HistoryDesciptionFragment extends Fragment implements HistoryDesciptionMVP.View, HistoryDesciptionAdapter.IHistoryCancelOrderAdapter,
        HistoryDesciptionAdapter.ISearchOrderAmount, View.OnClickListener {


    @BindView(R.id.edtSearchProduct)
    protected EditText edtSearchProduct;
    @BindView(R.id.recyclerViewAddToCartDetails)
    protected RecyclerView recyclerViewAddToCartDetails;
    @BindView(R.id.linearParent)
    protected LinearLayout linearParent;
    // @BindView(R.id.subTotalValueTextView)
    //protected TextView subTotalValueTextView;
    //  @BindView(R.id.shippingValueTextView)
    //protected TextView shippingValueTextView;
    @BindView(R.id.totalValueTextView)
    protected TextView totalValueTextView;
    @BindView(R.id.txtNoCartItemFound)
    protected TextView txtNoCartItemFound;

    @BindView(R.id.progressBarRelativeLayout)
    protected RelativeLayout progressBarRelativeLayout;

    @BindView(R.id.datePickerEdittext)
    protected EditText datePickerEdittext;
    @BindView(R.id.linearLayoutItemDetails)
    protected LinearLayout linearLayoutItemDetails;
    @BindView(R.id.searchParent)
    protected LinearLayout searchParent;

    @Inject
    protected HistoryDesciptionMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    private ProgressCustomDialogController progressDialogControllerPleaseWait;

    public static final String KEY_SHOP_CARD_SELECTED_PRODUCT_ORDER = "shop_card_selected_product_id_order";

    HistoryDesciptionAdapter adapter;
    View toastView;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    boolean isProgressBarShowing = false;

    String orderID = "";

    public HistoryDesciptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        try {

            orderID = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRODUCT_ORDER);

        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            // ((BaseActivity) getActivity()).showBottomNavigation();
            //edtSearchProduct.setText("");
            // datePickerEdittext.setText("");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history_discription, container, false);

        initializeViews(v);

        return v;
    }

    public void setTitle() {
        // ((BaseActivity) getActivity()).setTitle(getString(R.string.history_dashboard));
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);
        //  ((BaseActivity) getActivity()).showBottomNavigation();

        // datePickerEdittext.setOnClickListener(this);
        linearLayoutItemDetails.setOnClickListener(this);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);
        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        // edtSearchProduct.setText("");
        //  datePickerEdittext.setText("");

        progressBarRelativeLayout.setVisibility(View.VISIBLE);
        //hideSearchDate();

//        edtSearchProduct.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // TODO Auto-generated method stub
//                try {
//
//                    if (!isProgressBarShowing) {
//                        String text = edtSearchProduct.getText().toString().toLowerCase(Locale.getDefault()).trim();
//                        //  adapter.filter(text);
//                    }
//
//                } catch (Exception ex) {
//
//                }
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1,
//                                          int arg2, int arg3) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//                                      int arg3) {
//                // TODO Auto-generated method stub
//            }
//        });

        isProgressBarShowing = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //  presenter.fetchOrderDetails();
                if (utils.isInternetAvailable()) {
                    // progressbar.setVisibility(View.VISIBLE);

                    presenter.fetchOrderIdDescription(orderID);

                } else {
                    showToastShortTime("Please connect to internet to view past orders.");

                }

            }
        }, 600);
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
    public void setHistoryAdapter(HistoryOrderIdDiscriptionResponse response) {
        adapter = new HistoryDesciptionAdapter(getActivity(), response.getProducts().getProList(),
                response.getAddress(),this, this);
        showRecylerViewProductsDetail(adapter);
    }

    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message, toastView);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }


    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void showRecylerViewProductsDetail(HistoryDesciptionAdapter HistoryDesciptionAdapter) {
        progressBarRelativeLayout.setVisibility(View.GONE);
        isProgressBarShowing = false;
        recyclerViewAddToCartDetails.setAdapter(HistoryDesciptionAdapter);


    }

    @Override
    public void showRecyclerView() {
        linearParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        linearParent.setVisibility(View.GONE);
    }

    @Override
    public void setCartCounts(long counts) {

        ((BaseActivity) getActivity()).setCartsCounts(counts, 3, "History");

    }

    @Override
    public void setCartCounterTextview(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }

    @Override
    public void setParentFields() {
        //  subTotalValueTextView.setText("0");
        //   shippingValueTextView.setText("0");
        totalValueTextView.setText("0");
    }

    @Override
    public void txtNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.GONE);
    }

    @Override
    public void setSubTotal(String setSubToal) {
        if (!utils.isTextNullOrEmpty(setSubToal)) {
//            subTotalValueTextView.setText(setSubToal);
            //  String flatShippingRateStr = shippingValueTextView.getText().toString();
            // long flatShippingRateLong = 0;
            //   flatShippingRateLong = Long.valueOf(setSubToal) + Long.valueOf(flatShippingRateStr);
            totalValueTextView.setText(String.valueOf(setSubToal));

        } else {
            totalValueTextView.setText("0");

            //          subTotalValueTextView.setText("0");
        }


    }


    @Override
    public void setAdapter(List<Booking> addToCarts) {
        //   if (adapter == null) {
        //  adapter = new HistoryDesciptionAdapter(getActivity(), addToCarts, this, this);
        // showRecylerViewProductsDetail(adapter);

        // } else {
        //    adapter.removeAll();
        //   adapter.addAll(addToCarts);
        // }
        //getSubTotal(addToCarts);

    }

    @Override
    public void showOrderCompleteSuccessDialog() {

        showOrderCompleteSuccessDialog(getActivity());

    }

    @Override
    public void hideLoadingProgressDialog() {
        progressBarRelativeLayout.setVisibility(View.GONE);
        isProgressBarShowing = false;
    }

    @Override
    public void showSearchData() {
        // searchParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchDate() {
        // searchParent.setVisibility(View.GONE);

    }

    private void showOrderCompleteSuccessDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_order_placed_successfully);

        //ImageView imgRemove = dialog.findViewById(R.id.imgRemove);
        final CheckView check = (CheckView) dialog.findViewById(R.id.check);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check.check();

            }
        }, 200);

//        imgRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//
//                    }
//                }, 200);
//            }
//        });

        dialog.show();
    }

    public HistoryDesciptionFragment newInstance(String orderID) {
        HistoryDesciptionFragment fragment = null;
        try {


            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRODUCT_ORDER, orderID);

            fragment = new HistoryDesciptionFragment();
            fragment.setArguments(bundle);
            return fragment;
        } catch (Exception e) {
            return fragment;
        }
    }

    private void getSubTotal(List<Booking> addToCarts) {
        long price = 0;

        for (Booking i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice()) + Long.valueOf(i.getFlatCharges());
        }
        setSubTotal(String.valueOf(price));
        // setCartCounterTextview(addToCarts.size());
        //   setCartCounts(addToCarts.size());

        presenter.fetchCartsCount();
    }


    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }

    @Override
    public void showSnackBarShortTime(String message, View view) {
        snackUtil.showSnackBarShortTime(view, message);
    }

    boolean isShowing = true;

    @Override
    public void cancelOrder(String orderID) {
        if (isShowing) {
            isShowing = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //isShowing = true;
                    // if (isShowing)
                    showCancelDialog(getActivity(), "Alert", "Do you want to cancel this order?");
                    isShowing = true;
                }
            }, 200);
        }
    }

    boolean isEnable = true;

    @Override
    public void trackOrder(String orderID) {
        if (isEnable) {
            isEnable = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showDialog(getActivity(), "Track Order Details");
                    isEnable = true;
                }
            }, 400);
        }
    }

    @Override
    public void hideSoftkeyboard() {
        utils.hideSoftKeyboard(edtSearchProduct);
    }

    boolean isPhoneBtnEnable = true;

    private void showDialog(Context context, String title) {

        //Sir farrukh contact number
        // final String companyNo = "923345290597";

        // final String companyNo = "923312831510";

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_track_order);

        TextView txtTrackOrderDeatils = dialog.findViewById(R.id.txtTrackOrderDeatils);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        ImageView imgCallMobile = dialog.findViewById(R.id.imgCallMobile);
        // final TextView textviewMobileNo = dialog.findViewById(R.id.textviewMobileNo);

        txtTitle.setText("" + title);
        imgCallMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
                if (mPermissionDenied) {
                    if (isPhoneBtnEnable) {
                        isPhoneBtnEnable = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isPhoneBtnEnable = true;
                                callSupportNumber(AppConstants.support_contact_no);

                            }
                        }, 400);
                    }
                }
            }
        });

        dialog.show();
    }

    private void callSupportNumber(String mobileNo) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            //  toastUtil.showToastLongTime("Security Exception - Please Enable Permissions");
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

    private void showCancelDialog(Context context, String title, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_cancel_dialog);
        // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        View v = dialog.getWindow().getDecorView();
//        v.setBackgroundResource(android.R.color.transparent);

        Button cancel = dialog.findViewById(R.id.cancel);
        Button submit = dialog.findViewById(R.id.submit);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);

        txtMessage.setText("" + message);
        txtTitle.setText("" + title);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sessionManager.logoutUser();
                //  startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.datePickerEdittext:
//                if (!isProgressBarShowing) {
//                    utils.hideSoftKeyboard(datePickerEdittext);
//                    showDatePickerDialog();
//                } else {
//                    showToastShortTime("Loading Data...");
//                }
//                break;

            case R.id.linearLayoutItemDetails:
                utils.hideSoftKeyboard(edtSearchProduct);
                break;

        }
    }

//    private void showDatePickerDialog() {
//        try {
//
//            calendar = Calendar.getInstance();
//            year = calendar.get(Calendar.YEAR);
//            month = calendar.get(Calendar.MONTH);
//            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//            datePickerDialog = new DatePickerDialog(getActivity(),
//                    new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                            datePickerEdittext.setText(day + "-" + (month + 1) + "-" + year);
//                        }
//                    }, year, month, dayOfMonth);
//            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//            datePickerDialog.show();
//        } catch (Exception e) {
//
//        }
//    }

    @Override
    public void showTotalAmount(String itemTotalPrice) {
        totalValueTextView.setText(itemTotalPrice);

    }
}

