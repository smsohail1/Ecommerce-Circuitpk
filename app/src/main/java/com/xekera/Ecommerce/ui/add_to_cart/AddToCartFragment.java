package com.xekera.Ecommerce.ui.add_to_cart;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.List;

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
//        // ((BaseActivity) getActivity()).addDashboardFragment(new ShopFragment());
//
//        // attaching bottom sheet behaviour - hide / show on scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

//        Fragment fragment;
//        fragment = new ShopFragment();
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
//                    fragment = new ShopFragment();
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
//                    //((BaseActivity) getActivity()).addDashboardFragment(new HistoryFragment());
//                    fragment = new HistoryFragment();
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


        btnCheckout.setOnClickListener(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchCartDetails();

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
    public void showRecylerViewProductsDetail(AddToCartAdapter addToCartAdapter) {
        recyclerViewAddToCartDetails.setAdapter(addToCartAdapter);

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

    @Override
    public void onIncrementButtonClick(AddToCart productItems) {

    }

    @Override
    public void onDecrementButtonClick(AddToCart productItems) {

    }

    @Override
    public void incrementDecrement(String quantity, long individualPrice, String itemPrice, String productName, String cutPrice, byte[] bytes) {
        presenter.saveProductDetails(quantity, individualPrice, itemPrice, productName,
                cutPrice, bytes);
    }

    @Override
    public void removeItemFromCart(AddToCart productItems) {
        presenter.removeItemFromCart(productItems);
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
        toastUtil.showToastShortTime("No item available in cart.");
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

        if (sessionManager.isSignUp()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    DeliveyBillingDetailsFragment deliveyBillingDetailsFragment = new DeliveyBillingDetailsFragment();
                    ((BaseActivity) getActivity()).addFragment(deliveyBillingDetailsFragment);
                }
            }, 100);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToastLongTime("Required SignUp/Login for further proceed.");
                    ((BaseActivity) getActivity()).addFragment(new LoginFragment());

                }
            }, 100);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:
                presenter.getCartCountList();
                break;
        }
    }
}
