package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        productName = getArguments().getString(KEY_SHOP_NAME_DETAILS, "");


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        try {
            setTitle();
         //   showBackImageIcon();
           // hideHumbergIcon();
            showBackImageIcon();
            //hideActionBar();
             hideLoginIcon();
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

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewProductDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        setTitle();
        hideLoginIcon();


        shopDetails = new ArrayList<ShoppingDetailModel>();
        shopDetails.add(new ShoppingDetailModel("Arduino", "5000"));

        shopDetails.add(new ShoppingDetailModel("Resberi Pi", "10000"));

        shopDetails.add(new ShoppingDetailModel("LED", "300"));

        shopDetails.add(new ShoppingDetailModel("Jumper Wire", "800"));

        shopDetails.add(new ShoppingDetailModel("Bread Board", "200"));
//        shopDetails.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");

        shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this);
        showRecylerViewProductsDetail(shopDetailsAdapter);


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
}
