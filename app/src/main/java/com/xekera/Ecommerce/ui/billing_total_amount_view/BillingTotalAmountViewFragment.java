package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import cdflynn.android.library.checkview.CheckView;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.StripePaymentActivity;
import com.xekera.Ecommerce.util.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.xekera.Ecommerce.util.AppConstants.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillingTotalAmountViewFragment extends Fragment implements View.OnClickListener, BillingTotalAmountViewMVP.View {


    @BindView(R.id.recyclerViewAddToCartDetails)
    protected RecyclerView recyclerViewAddToCartDetails;
    @BindView(R.id.linearParent)
    protected LinearLayout linearParent;
    @BindView(R.id.subTotalValueTextView)
    protected TextView subTotalValueTextView;
    @BindView(R.id.shippingValueTextView)
    protected TextView shippingValueTextView;
    @BindView(R.id.totalValueTextView)
    protected TextView totalValueTextView;
    @BindView(R.id.btnConfirmCheckout)
    protected Button btnConfirmCheckout;

    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    @Inject
    protected BillingTotalAmountViewMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;


    BillingTotalAmountViewAdapter adapter;

    public static final String KEY_FLAT_CHARGES = "flat_charges";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_COMPANY_NAME = "company_name";
    public static final String KEY_PHONE_NO = "phone_no";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_STREET_ADDRESS1 = "street_address1";
    public static final String KEY_STREET_ADDRESS2 = "street_address2";
    public static final String KEY_TOWN_CITY = "town_city";
    public static final String KEY_PAYMENT_MODE = "payment_mode";
    public static final String KEY_ORDER_NOTES = "order_notes";
    public static final String KEY_SELF_PICKUP = "self_pickup";


    String flatCharges = "", firstName = "", lastName = "", companyName = "", phoneNo = "", email = "", streetAddress1 = "",
            streetAddress2 = "", townCity = "", paymentMode = "", orderNotes = "", selfPikup = "",
            cardNumber = "", expiryDate = "", CVCNumber = "";

    List<String> cartItems;
    List<Booking> cartList;

    View toastView;

    public BillingTotalAmountViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);

        flatCharges = getArguments().getString(KEY_FLAT_CHARGES, "");
        firstName = getArguments().getString(KEY_FIRST_NAME, "");
        lastName = getArguments().getString(KEY_LAST_NAME, "");
        companyName = getArguments().getString(KEY_COMPANY_NAME, "");
        phoneNo = getArguments().getString(KEY_PHONE_NO, "");
        email = getArguments().getString(KEY_EMAIL, "");
        streetAddress1 = getArguments().getString(KEY_STREET_ADDRESS1, "");
        streetAddress2 = getArguments().getString(KEY_STREET_ADDRESS2, "");
        townCity = getArguments().getString(KEY_TOWN_CITY, "");
        paymentMode = getArguments().getString(KEY_PAYMENT_MODE, "");
        orderNotes = getArguments().getString(KEY_ORDER_NOTES, "");
        selfPikup = getArguments().getString(KEY_SELF_PICKUP, "");


        // Connect to Your Back4app Account
        Parse.initialize(new Parse.Configuration.Builder(getActivity())
                .applicationId(APPLICATION_ID)
                .clientKey(CLIENT_KEY)
                .server(BACK4PAPP_API).build());
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);


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
        View v = inflater.inflate(R.layout.billing_total_amount_view, container, false);

        initializeViews(v);


        return v;
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        btnConfirmCheckout.setOnClickListener(this);
        // ((BaseActivity) getActivity()).hideBottomNavigation();

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        shippingValueTextView.setText(flatCharges);
        presenter.fetchCartDetails();

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
        toastUtil.showToastShortTime(message, toastView);
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
    public void showRecylerViewProductsDetail(BillingTotalAmountViewAdapter billingTotalAmountViewAdapter) {
        recyclerViewAddToCartDetails.setAdapter(billingTotalAmountViewAdapter);

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
    public void setSubTotal(String setSubToal) {
        if (!utils.isTextNullOrEmpty(setSubToal)) {
            subTotalValueTextView.setText(setSubToal);
            String flatShippingRateStr = shippingValueTextView.getText().toString();
            long flatShippingRateLong = 0;
            flatShippingRateLong = Long.valueOf(setSubToal) + Long.valueOf(flatShippingRateStr);
            totalValueTextView.setText(String.valueOf(flatShippingRateLong));

        } else {
            subTotalValueTextView.setText("0");
        }


    }


    public BillingTotalAmountViewFragment newInstance(String flatCharges, String firstName, String company, String phone,
                                                      String email, String streetAddress1,
                                                      String townCity, String paymode,
                                                      String notes, String selfPickup, String cardNumber, String expiryDate, String CVCNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FLAT_CHARGES, flatCharges);
        bundle.putString(KEY_FIRST_NAME, firstName);
        bundle.putString(KEY_COMPANY_NAME, company);
        bundle.putString(KEY_PHONE_NO, phone);
        bundle.putString(KEY_EMAIL, email);
        bundle.putString(KEY_STREET_ADDRESS1, streetAddress1);
        bundle.putString(KEY_TOWN_CITY, townCity);
        bundle.putString(KEY_PAYMENT_MODE, paymode);
        bundle.putString(KEY_ORDER_NOTES, notes);
        bundle.putString(KEY_SELF_PICKUP, selfPickup);


        BillingTotalAmountViewFragment fragment = new BillingTotalAmountViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showMessageRemoveItemFromCart(String message) {

    }

    @Override
    public void setParentFields() {
        subTotalValueTextView.setText("0");
        shippingValueTextView.setText("0");
        totalValueTextView.setText("0");
    }

    @Override
    public void showMessageZeroItemOnCart() {
        toastUtil.showToastShortTime("No item exist in cart.", toastView);
    }

    @Override
    public void setCartCounts(long counts) {
        showToastShortTime("No cart item found.");

    }

    @Override
    public void cartLists(List<AddToCart> addToCarts) {
        cartItems = new ArrayList<String>();
        for (AddToCart addToCart : addToCarts) {
            cartItems.add(addToCart.getItemName());
        }

        presenter.addItemsToBooking(addToCarts, firstName, companyName, phoneNo, email, streetAddress1,
                townCity, paymentMode, orderNotes, flatCharges, selfPikup);
    }


    @Override
    public void itemRemovedFromCart() {
        //  showToastLongTime("Order Submitted Successfully.");
        showOrderCompleteSuccessDialog(getActivity());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).popBackstack();
                ((BaseActivity) getActivity()).popBackstack();
                ((BaseActivity) getActivity()).navigateToScreen(R.id.navigation_History);

            }
        }, 300);


    }

    private void showOrderCompleteSuccessDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_order_placed_successfully);

        final CheckView check = (CheckView) dialog.findViewById(R.id.check);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check.check();

            }
        }, 150);

        dialog.show();
    }


    @Override
    public void bookingObject(List<Booking> bookings) {
        cartList = bookings;
    }

    @Override
    public void deleteItemsFromCart() {
        presenter.deleteCartItems(cartItems);

    }

    @Override
    public void setAdapter(List<AddToCart> addToCarts) {
        adapter = new BillingTotalAmountViewAdapter(addToCarts);
        showRecylerViewProductsDetail(adapter);

        getSubTotal(addToCarts);

    }

    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());

        }
        setSubTotal(String.valueOf(price));
        //  view.setCartCounts(addToCarts.size());
    }

    Card card;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirmCheckout:

                if (!utils.isTextNullOrEmptyOrZero(totalValueTextView.getText().toString())) {
                    if (!utils.isTextNullOrEmpty(paymentMode)) {
                        if (paymentMode.equalsIgnoreCase("Credit Card (Stripe)")) {
                            if (utils.isInternetAvailable()) {
                                if (!utils.isTextNullOrEmpty(sessionManager.getExpiryDate()) ||
                                        !utils.isTextNullOrEmpty(sessionManager.getCardNumber()) ||
                                        !utils.isTextNullOrEmpty(sessionManager.getCVCNumber())) {
                                    String[] cardExpiryDate = sessionManager.getExpiryDate().split("/");
                                    card = new Card(sessionManager.getCardNumber(), Integer.valueOf(cardExpiryDate[0]), Integer.valueOf(cardExpiryDate[1]), sessionManager.getCVCNumber());
                                    showProgressDialogPleaseWait();
                                    sendStripeRequest(card);
                                } else {
                                    showToastShortTime("Please Enter Credit Card Details.");
                                    gotoStripeActivity();

                                }

                            } else {
                                showToastShortTime("Please connect to internet.");

                            }
                        } else {
                            String formattedDate = "";
                            formattedDate = getCurrentDate();
                            presenter.insertBooking(cartList, formattedDate);

                        }
                    } else {
                        showToastShortTime("Please select payment mode");
                    }
                } else {
                    showToastShortTime("Can't order items due to total amount is zero");

                }
//                presenter.deleteCartItems(cartItems);
                break;
        }

    }

    private void gotoStripeActivity() {
        Intent intent = new Intent(getActivity(), StripePaymentActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

    }


    private void sendStripeRequest(Card card) {

        Stripe stripe = new Stripe(getActivity());
        stripe.createToken(
                card,
                PUBLISHABLE_KEY,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        if (token != null) {
                            charge(token);
                        } else {
                            hideProgressDialogPleaseWait();
                            showToastShortTime("Error while payment using stripe.");
                            sessionManager.removeCreditCardSession();

                        }

                    }

                    public void onError(Exception error) {
                        hideProgressDialogPleaseWait();
                        showToastShortTime(error.getMessage());
                        sessionManager.removeCreditCardSession();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                gotoStripeActivity();
                            }
                        }, 300);

                    }
                }
        );

    }

    private void charge(Token cardToken) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //  params.put("ItemName", "test");
        params.put("cardToken", cardToken.getId());
        // params.put("name", "Dominic Wong");
        params.put("email", email);
        params.put("orderID", "12345");
        params.put("price", Integer.valueOf(totalValueTextView.getText().toString()));
        //params.put("address", "HIHI");
//        params.put("zip", "99999");
//        params.put("city_state", "CA");
        //startProgress("Purchasing Item");
        ParseCloud.callFunctionInBackground("purchaseItem", params, new FunctionCallback<Object>() {
            public void done(Object response, ParseException e) {
                // finishProgress();
                hideProgressDialogPleaseWait();

                if (e == null) {

                    if (response != null && response.toString().equalsIgnoreCase("Success")) {
                        // showToastShortTime("Error while payment using stripe.");
                        sessionManager.removeCreditCardSession();

                        String formattedDate = "";
                        formattedDate = getCurrentDate();
                        presenter.insertBooking(cartList, formattedDate);
                        //  Log.d("Cloud Response", "There were no exceptions! " + response.toString());

                    } else {
                        showToastShortTime("Error while payment using stripe.");
                        sessionManager.removeCreditCardSession();

                    }
//                    Toast.makeText(getApplicationContext(),
//                            "Item Purchased Successfully ",
                    //          Toast.LENGTH_LONG).show();
                } else {
                    //  Log.d("Cloud Response", "Exception: " + e);
                    showToastShortTime("Error while payment using stripe.");
                    sessionManager.removeCreditCardSession();

//                    Toast.makeText(getApplicationContext(),
//                            e.getMessage().toString(),
//                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private String getCurrentDate() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_TWO);
            return df.format(c.getTime());
        } catch (Exception e) {
            return "";
        }
    }


}