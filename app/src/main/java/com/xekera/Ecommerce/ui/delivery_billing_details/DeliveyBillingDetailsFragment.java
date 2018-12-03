package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.RelationAdapter;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
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
    @BindView(R.id.spinnerCountry)
    protected Spinner spinnerCountry;
    @BindView(R.id.spinnerTownCity)
    protected Spinner spinnerTownCity;
    @BindView(R.id.spinnerStateCountry)
    protected Spinner spinnerStateCountry;

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
    @BindView(R.id.spinnerCountryDiffAddress)
    protected Spinner spinnerCountryDiffAddress;
    @BindView(R.id.spinnerTownCityDiffAddress)
    protected Spinner spinnerTownCityDiffAddress;
    @BindView(R.id.spinnerStateCountryDiffAddress)
    protected Spinner spinnerStateCountryDiffAddress;

    @BindView(R.id.radioGroup)
    protected RadioGroup radioGroup;
    @BindView(R.id.radioGroupDiffAddress)
    protected RadioGroup radioGroupDiffAddress;

    @BindView(R.id.btnCheckout)
    protected Button btnCheckout;

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


    String selectedSpinnerCountry = "", selectedSpinnerStateCountry = "", selectedSpinnerTownCity = "";
    String selectedSpinnerCountryDiffAddress = "", selectedSpinnerStateCountryDiffAddress = "", selectedSpinnerTownCityDiffAddress = "";
    String selectedPaymentMode = "", selectedPaymentModeDiffAddress = "";

    List<String> country;
    List<String> cityTown;
    List<String> stateCountry;


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
        ((BaseActivity) getActivity()).hideBottomNavigation();

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

        btnCheckout.setOnClickListener(this);

        ((BaseActivity) getActivity()).hideBottomNavigation();


        edtFirstname.setText(sessionManager.getusername());
        edtPhoneNo.setText(sessionManager.getphoneno());
        edtEmail.setText(sessionManager.getEmail());

        country = Arrays.asList(getResources().getStringArray(R.array.country));
        cityTown = Arrays.asList(getResources().getStringArray(R.array.town_city));
        stateCountry = Arrays.asList(getResources().getStringArray(R.array.state_country));


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, country);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCountry.setAdapter(new RelationAdapter(country));
        spinnerStateCountry.setAdapter(new RelationAdapter(stateCountry));
        spinnerTownCity.setAdapter(new RelationAdapter(cityTown));


        spinnerCountry.setSelection(0);
        spinnerStateCountry.setSelection(0);
        spinnerTownCity.setSelection(0);

        spinnerCountryDiffAddress.setAdapter(new RelationAdapter(country));
        spinnerStateCountryDiffAddress.setAdapter(new RelationAdapter(stateCountry));
        spinnerTownCityDiffAddress.setAdapter(new RelationAdapter(cityTown));

        spinnerCountryDiffAddress.setSelection(0);
        spinnerStateCountryDiffAddress.setSelection(0);
        spinnerTownCityDiffAddress.setSelection(0);

        radioGroup.clearCheck();
        radioGroupDiffAddress.clearCheck();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    selectedPaymentMode = rb.getText().toString();
                    // Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    selectedPaymentMode = "";
                    Toast.makeText(getActivity(), "No PayMode Selected", Toast.LENGTH_SHORT).show();


                }

            }
        });
        radioGroupDiffAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    selectedPaymentModeDiffAddress = rb.getText().toString();
                    //Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    selectedPaymentModeDiffAddress = "";
                    Toast.makeText(getActivity(), "No PayMode Selected", Toast.LENGTH_SHORT).show();


                }

            }
        });


//        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerCountry = spinnerCountry.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        spinnerStateCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerStateCountry = spinnerStateCountry.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        spinnerTownCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerTownCity = spinnerTownCity.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        spinnerCountryDiffAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerCountryDiffAddress = spinnerCountryDiffAddress.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        spinnerStateCountryDiffAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerStateCountryDiffAddress = spinnerStateCountryDiffAddress.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        spinnerTownCityDiffAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedSpinnerTownCityDiffAddress = spinnerTownCityDiffAddress.getItemAtPosition(i).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


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

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
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
    public void showBillingAmountDetailView(final String flatCharges, final String firstName, final String lastName,
                                            final String company, final String phone,
                                            final String email, final String streetAddress1, final String streetAddress2,
                                            final String country, final String stateCountry, final String townCity, final String paymode,
                                            final String notes, final String postalCode) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BillingTotalAmountViewFragment billingTotalAmountViewFragment = new BillingTotalAmountViewFragment();
                ((BaseActivity) getActivity()).addFragment
                        (billingTotalAmountViewFragment.newInstance(flatCharges, firstName, lastName, company, phone,
                                email, streetAddress1, streetAddress2, country, stateCountry, townCity, paymode, notes, postalCode));

            }
        }, 200);
    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:

                utils.hideSoftKeyboard(edtFirstname);
                utils.hideSoftKeyboard(edtLastname);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);
                utils.hideSoftKeyboard(edtStreetAddress2);
                utils.hideSoftKeyboard(edtPostalCode);

                utils.hideSoftKeyboard(edtFirstnameDiffAddress);
                utils.hideSoftKeyboard(edtLastnameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress2DiffAddress);
                utils.hideSoftKeyboard(edtPostalCodeDiffAddress);
                utils.hideSoftKeyboard(edtNotes);


                if (shipToDiffAddressCheckBox.isChecked()) {

                    selectedSpinnerCountryDiffAddress = country.get(spinnerCountryDiffAddress.getSelectedItemPosition());

                    selectedSpinnerStateCountryDiffAddress = stateCountry.get(spinnerStateCountryDiffAddress.getSelectedItemPosition());

                    String flatChargesAmount = "250";
                    selectedSpinnerTownCityDiffAddress = cityTown.get(spinnerTownCityDiffAddress.getSelectedItemPosition());

                    if (selectedSpinnerTownCityDiffAddress.equalsIgnoreCase("Islamabad") ||
                            selectedSpinnerTownCityDiffAddress.equalsIgnoreCase("Rawalpindi")) {
                        flatChargesAmount = "100";
                    } else {
                        flatChargesAmount = "250";
                    }

                    presenter.saveDetails(edtFirstnameDiffAddress.getText().toString(), edtLastnameDiffAddress.getText().toString(),
                            edtCompanyNameDiffAddress.getText().toString(), edtPhoneNoDiffAddress.getText().toString(),
                            edtEmailDiffAddress.getText().toString(), edtStreetAddress1DiffAddress.getText().toString(),
                            edtStreetAddress2DiffAddress.getText().toString(), selectedSpinnerCountryDiffAddress, selectedSpinnerStateCountryDiffAddress,
                            selectedSpinnerTownCityDiffAddress, selectedPaymentModeDiffAddress, edtNotes.getText().toString(),
                            flatChargesAmount, edtPostalCodeDiffAddress.getText().toString());
                } else {
                    selectedSpinnerCountry = country.get(spinnerCountry.getSelectedItemPosition());

                    selectedSpinnerStateCountry = stateCountry.get(spinnerStateCountry.getSelectedItemPosition());

                    selectedSpinnerTownCity = cityTown.get(spinnerTownCity.getSelectedItemPosition());

                    String flatChargesAmount = "250";
                    if (selectedSpinnerTownCity.equalsIgnoreCase("Islamabad") ||
                            selectedSpinnerTownCity.equalsIgnoreCase("Rawalpindi")) {
                        flatChargesAmount = "100";
                    } else {
                        flatChargesAmount = "250";
                    }
                    presenter.saveDetails(edtFirstname.getText().toString(), edtLastname.getText().toString(),
                            edtCompanyName.getText().toString(), edtPhoneNo.getText().toString(),
                            edtEmail.getText().toString(), edtStreetAddress1.getText().toString(),
                            edtStreetAddress2.getText().toString(), selectedSpinnerCountry, selectedSpinnerStateCountry,
                            selectedSpinnerTownCity, selectedPaymentMode, edtNotes.getText().toString(),
                            flatChargesAmount, edtPostalCode.getText().toString());
                }


                break;
        }

    }


//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        int ii = 0;
//        switch (adapterView.getId()) {
//            case R.id.spinnerCountry:
//                selectedSpinnerCountry = adapterView.getItemAtPosition(i).toString();
//
//
//                break;
//            case R.id.spinnerStateCountry:
//                selectedSpinnerStateCountry = adapterView.getItemAtPosition(i).toString();
//
//                break;
//            case R.id.spinnerTownCity:
//                selectedSpinnerTownCity = adapterView.getItemAtPosition(i).toString();
//
//                break;
//            case R.id.spinnerCountryDiffAddress:
//                selectedSpinnerCountryDiffAddress = adapterView.getItemAtPosition(i).toString();
//
//                break;
//            case R.id.spinnerStateCountryDiffAddress:
//                selectedSpinnerStateCountryDiffAddress = adapterView.getItemAtPosition(i).toString();
//
//                break;
//            case R.id.spinnerTownCityDiffAddress:
//                selectedSpinnerTownCityDiffAddress = adapterView.getItemAtPosition(i).toString();
//
//                break;
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}
