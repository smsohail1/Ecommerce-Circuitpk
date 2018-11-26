package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;

public class DeliveyBillingDetailsFragment extends Fragment implements DeliveyBillingDetailsMVP.View, View.OnClickListener {

    @BindView(R.id.shipToDiffAddressCheckBox)
    protected CheckBox shipToDiffAddressCheckBox;
    @BindView(R.id.DiffAddressLayout)
    protected LinearLayout DiffAddressLayout;
    @BindView(R.id.edtFirstname)
    protected EditText edtFirstname;
    @BindView(R.id.edtLastname)
    protected EditText edtLastname;
    @BindView(R.id.edtCompanyName)
    protected EditText edtCompanyName;
    @BindView(R.id.edtPhoneNo)
    protected EditText edtPhoneNo;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtStreetAddress1)
    protected EditText edtStreetAddress1;
    @BindView(R.id.edtStreetAddress2)
    protected EditText edtStreetAddress2;
    @BindView(R.id.edtPostalCode)
    protected EditText edtPostalCode;
    @BindView(R.id.edtNotes)
    protected EditText edtNotes;


    @BindView(R.id.edtFirstnameDiffAddress)
    protected EditText edtFirstnameDiffAddress;
    @BindView(R.id.edtLastnameDiffAddress)
    protected EditText edtLastnameDiffAddress;
    @BindView(R.id.edtCompanyNameDiffAddress)
    protected EditText edtCompanyNameDiffAddress;
    @BindView(R.id.edtPhoneNoDiffAddress)
    protected EditText edtPhoneNoDiffAddress;
    @BindView(R.id.edtEmailDiffAddress)
    protected EditText edtEmailDiffAddress;
    @BindView(R.id.edtStreetAddress1DiffAddress)
    protected EditText edtStreetAddress1DiffAddress;
    @BindView(R.id.edtStreetAddress2DiffAddress)
    protected EditText edtStreetAddress2DiffAddress;
    @BindView(R.id.edtPostalCodeDiffAddress)
    protected EditText edtPostalCodeDiffAddress;


    @Inject
    protected DeliveyBillingDetailsMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;


    public DeliveyBillingDetailsFragment() {
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
        presenter.setView(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_delivey_billing_details, container, false);

        initializeViews(v);


        return v;
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);


        shipToDiffAddressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                     if (isChecked) {
                                                                         DiffAddressLayout.setVisibility(View.VISIBLE);
                                                                     } else {
                                                                         DiffAddressLayout.setVisibility(View.GONE);

                                                                     }
                                                                 }
                                                             }
        );

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }


    @Override
    public void onClick(View view) {

    }
}
