package com.xekera.Ecommerce.ui.add_to_cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.home_delivery_Address.DeliveryAddressActivity;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;

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

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(getString(R.string.cart_dashboard));
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        //  deliveryAddressImageView.setOnClickListener(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    public void removeItemFromCart(AddToCart productItems) {

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
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.deliveryAddressImageView:
//                Intent i = new Intent(getActivity(), DeliveryAddressActivity.class);
//                getActivity().startActivity(i);
//                break;
//        }
    }
}
