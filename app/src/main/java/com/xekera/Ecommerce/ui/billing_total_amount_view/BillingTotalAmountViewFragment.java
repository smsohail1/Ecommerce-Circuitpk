package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


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

    public static final String KEY_FLAT_CHARGES = "flat_charges";
    String flatCharges = "";
    List<String> cartItems;


    public BillingTotalAmountViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        flatCharges = getArguments().getString(KEY_FLAT_CHARGES, "");


    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity) getActivity()).hideBottomNavigation();

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


        btnConfirmCheckout.setOnClickListener(this);
        ((BaseActivity) getActivity()).hideBottomNavigation();

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


    public BillingTotalAmountViewFragment newInstance(String flatCharges) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FLAT_CHARGES, flatCharges);
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
        toastUtil.showToastShortTime("No item exist in cart.");
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

    }

    @Override
    public void itemRemovedFromCart() {
        showToastLongTime("Order Submitted Successfully.");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).popBackstack();
                ((BaseActivity) getActivity()).popBackstack();
                ((BaseActivity) getActivity()).navigateToScreen(R.id.navigation_History);

            }
        }, 300);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirmCheckout:
                presenter.deleteCartItems(cartItems);
                break;
        }

    }
}