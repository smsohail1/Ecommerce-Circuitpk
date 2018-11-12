package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailsFragment extends Fragment implements ShopDetailsMVP.View {


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
           // setTitle();
           // showBackImageIcon();
            // hideHumbergIcon();
            hideActionBar();
           // hideLoginIcon();
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

        // getToolbar().setVisibility(View.GONE);
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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

        setTitle();
        hideLoginIcon();
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


    public ShopDetailsFragment newInstance(String ProductName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SHOP_NAME_DETAILS, ProductName);
        ShopDetailsFragment fragment = new ShopDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
//
//    public class newInstance extends Fragment {
//        public newInstance(String s) {
//        }
//    }
}
