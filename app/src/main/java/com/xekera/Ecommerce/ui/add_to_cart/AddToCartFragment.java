package com.xekera.Ecommerce.ui.add_to_cart;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShopFragment;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingFragment;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToCartFragment extends Fragment implements AddToCartMVP.View, AddToCartAdapter.IShopDetailAdapter,
        View.OnClickListener {


    @BindView(R.id.recyclerViewAddToCartDetails)
    protected RecyclerView recyclerViewAddToCartDetails;
    @BindView(R.id.linearParent)
    protected LinearLayout linearParent;
    //  @BindView(R.id.deliveryAddressImageView)
    //protected ImageView deliveryAddressImageView;
    //@BindView(R.id.deliveryAddressValueTextView)
    //protected TextView deliveryAddressValueTextView;
    @BindView(R.id.subTotalValueTextView)
    protected TextView subTotalValueTextView;
    @BindView(R.id.shippingValueTextView)
    protected TextView shippingValueTextView;
    @BindView(R.id.totalValueTextView)
    protected TextView totalValueTextView;
    @BindView(R.id.txtNoCartItemFound)
    protected TextView txtNoCartItemFound;
    @BindView(R.id.btnCheckout)
    protected Button btnCheckout;
    @BindView(R.id.btnContinueShopping)
    protected Button btnContinueShopping;
    @BindView(R.id.btnCoupon)
    protected Button btnCoupon;
    @BindView(R.id.gstValueTextView)
    protected TextView gstValueTextView;

    @BindView(R.id.progressBarRelativeLayout)
    protected RelativeLayout progressBarRelativeLayout;


    @Inject
    protected AddToCartMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    AddToCartAdapter adapter;

    String latitude = "";
    String longitude = "";
    String placeName = "";

    private ProgressCustomDialogController progressDialogControllerPleaseWait;
    View toastView;
    boolean isProgressBarShowing = false;


    public AddToCartFragment() {
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
        try {
            // ((BaseActivity) getActivity()).showBottomNavigation();
            // btnCheckout.setClickable(true);

//            placeName = sessionManager.getKeyPlaceName();
//            latitude = sessionManager.getKeyLatitude();
//            longitude = sessionManager.getKeyLongitude();
//            if (!utils.isTextNullOrEmpty(placeName)) {
//                deliveryAddressValueTextView.setText(placeName);
//            } else {
//                deliveryAddressValueTextView.setText("");
//
//            }
            //   setTitle();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        initializeViews(v);

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        // ((BaseActivity) getActivity()).addDashboardFragment(new ContinueShopFragment());
//
//        // attaching bottom sheet behaviour - hide / show on scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

//        Fragment fragment;
//        fragment = new ContinueShopFragment();
//        addFragment(fragment);

    }

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigation_shop:
//
//                    fragment = new ContinueShopFragment();
//                    addFragment(fragment);
//                    return true;
//                case R.id.navigation_favourite:
//                    fragment = new FragmentFavourites();
//                    addFragment(fragment);
//                    return true;
//                case R.id.navigation_cart:
//                    fragment = new AddToCartFragment();
//                    addFragment(fragment);
//                    return true;
//
//                case R.id.navigation_History:
//                    //  Toast.makeText(getActivity(), "History is selected", Toast.LENGTH_SHORT).show();
//                    //((BaseActivity) getActivity()).popBackstack();
//                    //((BaseActivity) getActivity()).addDashboardFragment(new HistoryDesciptionFragment());
//                    fragment = new HistoryDesciptionFragment();
//                    addFragment(fragment);
//                    return true;
//            }
//            return false;
//        }
//    };


    private void addFragment(Fragment fragment) {
        // load fragment
        if (fragment != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer2, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(getString(R.string.cart_dashboard));
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);
        // ((BaseActivity) getActivity()).showBottomNavigation();
        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);


        btnCheckout.setOnClickListener(this);
        btnContinueShopping.setOnClickListener(this);
        btnCoupon.setOnClickListener(this);
        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        isProgressBarShowing = true;
        progressBarRelativeLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchCartDetails();

            }
        }, 500);

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
    public void showRecylerViewProductsDetail(AddToCartAdapter addToCartAdapter) {
        progressBarRelativeLayout.setVisibility(View.GONE);
        isProgressBarShowing = false;
        recyclerViewAddToCartDetails.setAdapter(addToCartAdapter);

    }

    @Override
    public void hideLoadingProgressDialog() {
        progressBarRelativeLayout.setVisibility(View.GONE);
        isProgressBarShowing = false;
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
        if (!utils.isTextNullOrEmptyOrZero(setSubToal)) {
            subTotalValueTextView.setText(setSubToal);
            String flatShippingRateStr = shippingValueTextView.getText().toString();
            long gstAmount = Long.valueOf(gstValueTextView.getText().toString());
            long flatShippingRateLong = 0;
            flatShippingRateLong = Long.valueOf(setSubToal) + Long.valueOf(flatShippingRateStr) + gstAmount;
            totalValueTextView.setText(String.valueOf(flatShippingRateLong));

        } else {
            subTotalValueTextView.setText("0");
            //   totalValueTextView.setText("0");
        }


    }

    @Override
    public void onIncrementButtonClick(AddToCart productItems) {

    }

    @Override
    public void onDecrementButtonClick(AddToCart productItems) {

    }

    @Override
    public void incrementDecrement(String quantity, long individualPrice, String itemPrice, String productName,
                                   String cutPrice, byte[] bytes, String imgUrl,String prodcutID,String isEmailSent) {
        presenter.saveProductDetails(quantity, individualPrice, itemPrice, productName,
                cutPrice, bytes, imgUrl,prodcutID,isEmailSent);
    }

    @Override
    public void removeItemFromCart(final AddToCart productItems, final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.removeItemFromCart(productItems, position);
            }
        }, 150);
    }


    @Override
    public void showMessageRemoveItemFromCart(String message) {
        showSnackBarShortTime("Item Removed from Cart", getView());
    }

    @Override
    public void setParentFields() {
        subTotalValueTextView.setText("0");
        shippingValueTextView.setText("0");
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
    public void showMessageZeroItemOnCart() {
        toastUtil.showToastShortTime("No item available in cart.", toastView);
    }

    @Override
    public void navigateToBillingDetailScreen() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                DeliveyBillingDetailsFragment deliveyBillingDetailsFragment = new DeliveyBillingDetailsFragment();
//                ((BaseActivity) getActivity()).addFragment(deliveyBillingDetailsFragment);
//            }
//        }, 100);

        if (sessionManager.isSignUp() || sessionManager.isLoggedIn() || sessionManager.getKeyIsFacebookLogin()) {

            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (!(fragment instanceof DeliveyBillingDetailsFragment)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        DeliveyBillingDetailsFragment deliveyBillingDetailsFragment = new DeliveyBillingDetailsFragment();
                        ((BaseActivity) getActivity()).addFragment(deliveyBillingDetailsFragment);
                    }
                }, 100);
            }
        } else {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (!(fragment instanceof LoginFragment)) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToastShortTime("Required SignUp for further proceed.");
                        ((BaseActivity) getActivity()).addFragment(new LoginFragment());

                    }
                }, 100);
            }
        }

    }

    @Override
    public void setCartCounts(long counts) {

        ((BaseActivity) getActivity()).setCartsCounts(counts);

    }

    @Override
    public void setCartCounterTextview(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }

    @Override
    public void setAdapter(List<AddToCart> addToCarts) {

        adapter = new AddToCartAdapter(addToCarts, this);
        showRecylerViewProductsDetail(adapter);


        getSubTotal(addToCarts);
    }

    @Override
    public void removeItemFromAdapter(int position) {
        adapter.removeItem(position);
    }

    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());
            // price = price + (Long.valueOf(i.getItemPrice()) * Long.valueOf(i.getItemQuantity()));

        }
        setSubTotal(String.valueOf(price));
        //  setCartCounts(addToCarts.size());
        setCartCounterTextview(addToCarts.size());

    }

    boolean isContinueShoppingEnable = true;
    boolean isEnable = true;
    boolean isCheckOutButtonEnable = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:
                if (!isProgressBarShowing) {
                    if (isCheckOutButtonEnable) {
                        if (!utils.isTextNullOrEmptyOrZero(subTotalValueTextView.getText().toString())) {
                            presenter.getCartCountList();
                        } else {
                            showToastShortTime("Can't order items due to sub total is zero");
                        }
                    }
                    isCheckOutButtonEnable = false;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isCheckOutButtonEnable = true;
                        }
                    }, 2000);
                } else {
                    showToastShortTime("Loading data...");

                }
                break;

            case R.id.btnContinueShopping:
                if (!isProgressBarShowing) {

                    if (isContinueShoppingEnable) {
                        isContinueShoppingEnable = false;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isContinueShoppingEnable = true;

                                ((BaseActivity) getActivity()).replaceFragment(new ContinueShopFragment());
                            }
                        }, 150);
                    }

                } else {
                    showToastShortTime("Loading data...");

                }
                break;

            case R.id.btnCoupon:
                if (!isProgressBarShowing) {

                    if (isEnable) {
                        isEnable = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isEnable = true;
                                showCouponDialog(getActivity(), "Coupon");

                            }
                        }, 200);
                    }
                } else {
                    showToastShortTime("Loading data...");
                }
                break;
        }
    }

    private void showCouponDialog(Context context, String title) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilaog_coupon);

        Button btnApplyCoupon = dialog.findViewById(R.id.btnApplyCoupon);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);

        txtTitle.setText("" + title);

        btnApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastUtil.showToastShortTime("Coupon Applied successfully.", toastView);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
