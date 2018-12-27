package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
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

    @BindView(R.id.checkboxSelfPickUpDiffAddress)
    protected CheckBox checkboxSelfPickUpDiffAddress;
    @BindView(R.id.flatRateTextview)
    protected TextView flatRateTextview;
    @BindView(R.id.flatRateTextviewDiffAddress)
    protected TextView flatRateTextviewDiffAddress;

    @BindView(R.id.checkboxSelfPickUp)
    protected CheckBox checkboxSelfPickUp;
    @BindView(R.id.DiffAddressLayout)
    protected LinearLayout DiffAddressLayout;
    @BindView(R.id.edtUsername)
    protected EditText edtUsername;

    @BindView(R.id.edtCompanyName)
    protected EditText edtCompanyName;
    @BindView(R.id.edtPhoneNo)
    protected EditText edtPhoneNo;
    @BindView(R.id.edtEmail)
    protected EditText edtEmail;
    @BindView(R.id.edtStreetAddress1)
    protected EditText edtStreetAddress1;
    @BindView(R.id.edtNotes)
    protected EditText edtNotes;
    @BindView(R.id.spinnerTownCity)
    protected Spinner spinnerTownCity;

    @BindView(R.id.edtUsernameDiffAddress)
    protected EditText edtUsernameDiffAddress;
    @BindView(R.id.edtCompanyNameDiffAddress)
    protected EditText edtCompanyNameDiffAddress;
    @BindView(R.id.edtPhoneNoDiffAddress)
    protected EditText edtPhoneNoDiffAddress;
    @BindView(R.id.edtEmailDiffAddress)
    protected EditText edtEmailDiffAddress;
    @BindView(R.id.edtStreetAddress1DiffAddress)
    protected EditText edtStreetAddress1DiffAddress;
    @BindView(R.id.spinnerTownCityDiffAddress)
    protected Spinner spinnerTownCityDiffAddress;

    @BindView(R.id.deliveryAddressSelfPickupLayout)
    protected LinearLayout deliveryAddressSelfPickupLayout;
    @BindView(R.id.deliveryAddressSelfPickupLayoutDiffAddress)
    protected LinearLayout deliveryAddressSelfPickupLayoutDiffAddress;
    @BindView(R.id.userDetailsParentLayout)
    protected LinearLayout userDetailsParentLayout;
    @BindView(R.id.linearDeliveryAddressLayout)
    protected LinearLayout linearDeliveryAddressLayout;
    @BindView(R.id.paymentModeLayout)
    protected LinearLayout paymentModeLayout;

    @BindView(R.id.userDetailsParentLayoutDiffAddress)
    protected LinearLayout userDetailsParentLayoutDiffAddress;
    @BindView(R.id.linearDeliveryAddressLayoutDiffAddress)
    protected LinearLayout linearDeliveryAddressLayoutDiffAddress;
    @BindView(R.id.paymentModeLayoutDiffAddress)
    protected LinearLayout paymentModeLayoutDiffAddress;
    @BindView(R.id.linearLayoutParent)
    protected LinearLayout linearLayoutParent;

    @BindView(R.id.radioGroup)
    protected RadioGroup radioGroup;
    @BindView(R.id.radioGroupDiffAddress)
    protected RadioGroup radioGroupDiffAddress;

    @BindView(R.id.btnCheckout)
    protected Button btnCheckout;
    @BindView(R.id.flatRateOtherCityTextview)
    protected TextView flatRateOtherCityTextview;
    @BindView(R.id.flatRateOtherCityTextviewDiffAddress)
    protected TextView flatRateOtherCityTextviewDiffAddress;


    final boolean[] isShipToDifferent = {false};
    int check = 0;

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


    String selectedSpinnerTownCity = "";
    String selectedSpinnerTownCityDiffAddress = "";
    String selectedPaymentMode = "", selectedPaymentModeDiffAddress = "";
    String selfPickup = "", selfPickupDiffAddress = "";

    List<String> cityTown;
    View toastView;


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
        // ((BaseActivity) getActivity()).hideBottomNavigation();

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

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        btnCheckout.setOnClickListener(this);
        userDetailsParentLayout.setOnClickListener(this);
        linearDeliveryAddressLayout.setOnClickListener(this);
        paymentModeLayout.setOnClickListener(this);
        linearLayoutParent.setOnClickListener(this);


        userDetailsParentLayoutDiffAddress.setOnClickListener(this);
        linearDeliveryAddressLayoutDiffAddress.setOnClickListener(this);
        paymentModeLayoutDiffAddress.setOnClickListener(this);
        //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // ((BaseActivity) getActivity()).hideBottomNavigation();


        edtUsername.setText(sessionManager.getusername());
        edtPhoneNo.setText(sessionManager.getphoneno());
        edtEmail.setText(sessionManager.getEmail());
        edtCompanyName.setText(sessionManager.getCompanyName());
        edtStreetAddress1.setText(sessionManager.getDeliveryAddress());

//        country = Arrays.asList(getResources().getStringArray(R.array.country));
        cityTown = Arrays.asList(getResources().getStringArray(R.array.town_city));
        //      stateCountry = Arrays.asList(getResources().getStringArray(R.array.state_country));


        // attaching data adapter to spinner
        spinnerTownCity.setAdapter(new RelationAdapter(cityTown));


        spinnerTownCity.setSelection(0);

        spinnerTownCityDiffAddress.setAdapter(new RelationAdapter(cityTown));

        spinnerTownCityDiffAddress.setSelection(0);

        radioGroup.clearCheck();
        radioGroupDiffAddress.clearCheck();

//        spinnerTownCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String city = spinnerTownCity.getItemAtPosition(i).toString();
//                if (isShipToDifferent[0]) {
//
//                    if (city.equalsIgnoreCase("Islamabad") || city.equalsIgnoreCase("Rawalpindi")) {
//                        flatRateTextviewDiffAddress.setText("RS100");
//
//                    } else {
//                        flatRateTextviewDiffAddress.setText("RS250");
//
//                    }
//                } else {
//                    if (city.equalsIgnoreCase("Islamabad") || city.equalsIgnoreCase("Rawalpindi")) {
//                        flatRateTextview.setText("RS100");
//
//                    } else {
//                        flatRateTextview.setText("RS250");
//
//                    }
//                }
//            }
//        });


        spinnerTownCityDiffAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    if (++check > 1) {

                        if (cityTown.get(position).equalsIgnoreCase("Islamabad") || cityTown.get(position).equalsIgnoreCase("Rawalpindi")) {
                            flatRateOtherCityTextviewDiffAddress.setText("Shipping Rate: RS 100");
                        } else if (cityTown.get(position).equalsIgnoreCase("Other")) {
                            flatRateOtherCityTextviewDiffAddress.setText("Shipping Rate: RS 250");
                        } else {
                            showToastShortTime("Please select valid city.");
                            flatRateOtherCityTextviewDiffAddress.setText("");

                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


        spinnerTownCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    if (++check > 1) {

                        if (cityTown.get(position).equalsIgnoreCase("Islamabad") || cityTown.get(position).equalsIgnoreCase("Rawalpindi")) {
                            flatRateOtherCityTextview.setText("Shipping Rate: RS 100");
                        } else if (cityTown.get(position).equalsIgnoreCase("Other")) {
                            flatRateOtherCityTextview.setText("Shipping Rate: RS 250");
                        } else {
                            showToastShortTime("Please select valid city.");
                            flatRateOtherCityTextview.setText("");

                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    selectedPaymentMode = rb.getText().toString();
                    if (selectedPaymentMode.equalsIgnoreCase("Easypay PK") ||
                            selectedPaymentMode.equalsIgnoreCase("JazzCash")) {
                        showDialog(getActivity(), "Cash Transfer Mobile No");
                    }
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
                    if (selectedPaymentModeDiffAddress.equalsIgnoreCase("Easypay PK") ||
                            selectedPaymentModeDiffAddress.equalsIgnoreCase("JazzCash")) {
                        showDialog(getActivity(), "Cash Transfer Mobile No");
                    }
                    // Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    selectedPaymentModeDiffAddress = "";
                    Toast.makeText(getActivity(), "No PayMode Selected", Toast.LENGTH_SHORT).show();


                }

            }
        });

        shipToDiffAddressCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                 @Override
                                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                     if (isChecked) {
                                                                         //  isShipToDifferent[0] = isChecked;
                                                                         check = 0;
                                                                         DiffAddressLayout.setVisibility(View.VISIBLE);

                                                                     } else {
                                                                         DiffAddressLayout.setVisibility(View.GONE);

                                                                         // isShipToDifferent[0] = isChecked;


                                                                     }
                                                                 }
                                                             }
        );
        checkboxSelfPickUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  selfPickup = checkboxSelfPickUp.getText().toString();
                                                                  edtStreetAddress1.setText("");
                                                                  deliveryAddressSelfPickupLayout.setVisibility(View.GONE);
                                                                  flatRateTextview.setText("RS 0");
                                                              } else {
                                                                  selfPickup = "";
                                                                  deliveryAddressSelfPickupLayout.setVisibility(View.VISIBLE);
                                                                  flatRateTextview.setText("");


                                                              }
                                                          }
                                                      }
        );
        checkboxSelfPickUpDiffAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                                     @Override
                                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                         if (isChecked) {
                                                                             selfPickupDiffAddress = checkboxSelfPickUpDiffAddress.getText().toString();
                                                                             edtStreetAddress1DiffAddress.setText("");
                                                                             deliveryAddressSelfPickupLayoutDiffAddress.setVisibility(View.GONE);
                                                                             flatRateTextviewDiffAddress.setText("RS 0");


                                                                         } else {
                                                                             selfPickupDiffAddress = "";
                                                                             deliveryAddressSelfPickupLayoutDiffAddress.setVisibility(View.VISIBLE);
                                                                             flatRateTextviewDiffAddress.setText("");


                                                                         }
                                                                     }
                                                                 }
        );


    }


    private void showDialog(Context context, String title) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_mobile_no);

        // Button submit = dialog.findViewById(R.id.submit);
        final TextView txtMobileNo = dialog.findViewById(R.id.txtMobileNo);
        Button txtCopied = dialog.findViewById(R.id.txtCopied);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);

        // txtMessage.setText("" + message);
        txtTitle.setText("" + title);

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //sessionManager.logoutUser();
//                //  startActivity(new Intent(BaseActivity.this, LoginActivity.class));
//                dialog.dismiss();
//            }
//        });
        txtCopied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // showToastShortTime("Copied");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 400);
            }
        });


        dialog.show();
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

    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void showBillingAmountDetailView(final String flatCharges, final String firstName,
                                            final String company, final String phone,
                                            final String email, final String streetAddress1,
                                            final String townCity, final String paymode,
                                            final String notes, final String selfPickup) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BillingTotalAmountViewFragment billingTotalAmountViewFragment = new BillingTotalAmountViewFragment();
                ((BaseActivity) getActivity()).addFragment
                        (billingTotalAmountViewFragment.newInstance(flatCharges, firstName, company, phone,
                                email, streetAddress1, townCity, paymode, notes, selfPickup));

            }
        }, 300);
    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:

                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);

                utils.hideSoftKeyboard(edtUsernameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtNotes);


                if (shipToDiffAddressCheckBox.isChecked()) {


                    String flatChargesAmount = "250";
                    selectedSpinnerTownCityDiffAddress = cityTown.get(spinnerTownCityDiffAddress.getSelectedItemPosition());

                    if ((selectedSpinnerTownCityDiffAddress.equalsIgnoreCase("Islamabad") ||
                            selectedSpinnerTownCityDiffAddress.equalsIgnoreCase("Rawalpindi"))
                            && utils.isTextNullOrEmpty(selfPickupDiffAddress)
                            ) {
                        flatChargesAmount = "100";
                    } else if (!utils.isTextNullOrEmpty(selfPickupDiffAddress) &&
                            selfPickupDiffAddress.equalsIgnoreCase("Self Pickup")) {
                        flatChargesAmount = "0";
                    } else {
                        flatChargesAmount = "250";
                    }

                    presenter.saveDetails(edtUsernameDiffAddress.getText().toString(),
                            edtCompanyNameDiffAddress.getText().toString(), edtPhoneNoDiffAddress.getText().toString(),
                            edtEmailDiffAddress.getText().toString(), edtStreetAddress1DiffAddress.getText().toString(),
                            selectedSpinnerTownCityDiffAddress, selectedPaymentModeDiffAddress, edtNotes.getText().toString(),
                            flatChargesAmount, selfPickupDiffAddress);
                } else {

                    selectedSpinnerTownCity = cityTown.get(spinnerTownCity.getSelectedItemPosition());

                    String flatChargesAmount = "250";
                    if ((selectedSpinnerTownCity.equalsIgnoreCase("Islamabad") ||
                            selectedSpinnerTownCity.equalsIgnoreCase("Rawalpindi"))
                            && utils.isTextNullOrEmpty(selfPickup)) {
                        flatChargesAmount = "100";
                    } else if (!utils.isTextNullOrEmpty(selfPickup) && selfPickup.equalsIgnoreCase("Self Pickup")) {
                        flatChargesAmount = "0";
                    } else {
                        flatChargesAmount = "250";
                    }
                    presenter.saveDetails(edtUsername.getText().toString(),
                            edtCompanyName.getText().toString(), edtPhoneNo.getText().toString(),
                            edtEmail.getText().toString(), edtStreetAddress1.getText().toString(),
                            selectedSpinnerTownCity, selectedPaymentMode, edtNotes.getText().toString(),
                            flatChargesAmount, selfPickup);
                }


                break;

            case R.id.userDetailsParentLayout:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);

                break;

            case R.id.linearDeliveryAddressLayout:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);

                break;


            case R.id.paymentModeLayout:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);
                break;
            case R.id.userDetailsParentLayoutDiffAddress:
                utils.hideSoftKeyboard(edtUsernameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtNotes);


                break;

            case R.id.linearDeliveryAddressLayoutDiffAddress:
                utils.hideSoftKeyboard(edtUsernameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtNotes);


                break;


            case R.id.paymentModeLayoutDiffAddress:
                utils.hideSoftKeyboard(edtUsernameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtNotes);

                break;

            case R.id.linearLayoutParent:
                utils.hideSoftKeyboard(edtUsername);
                utils.hideSoftKeyboard(edtCompanyName);
                utils.hideSoftKeyboard(edtPhoneNo);
                utils.hideSoftKeyboard(edtEmail);
                utils.hideSoftKeyboard(edtStreetAddress1);

                utils.hideSoftKeyboard(edtUsernameDiffAddress);
                utils.hideSoftKeyboard(edtCompanyNameDiffAddress);
                utils.hideSoftKeyboard(edtPhoneNoDiffAddress);
                utils.hideSoftKeyboard(edtEmailDiffAddress);
                utils.hideSoftKeyboard(edtStreetAddress1DiffAddress);
                utils.hideSoftKeyboard(edtNotes);

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
