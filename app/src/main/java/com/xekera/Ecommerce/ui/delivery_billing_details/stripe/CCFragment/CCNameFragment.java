package com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CardFrontFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.StripePaymentActivity;
import com.xekera.Ecommerce.util.CreditCardEditText;
import com.xekera.Ecommerce.util.Utils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CCNameFragment extends Fragment {


    @BindView(R.id.et_name)
    CreditCardEditText et_name;
    @BindView(R.id.cardViewCCName)
    CardView cardViewCCName;
    TextView tv_Name;

    StripePaymentActivity activity;
    CardFrontFragment cardFrontFragment;

    @Inject
    Utils utils;


    public CCNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccname, container, false);
        ButterKnife.bind(this, view);

        activity = (StripePaymentActivity) getActivity();
        cardFrontFragment = activity.cardFrontFragment;

        tv_Name = cardFrontFragment.getName();

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (tv_Name != null) {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tv_Name.setText("CARD HOLDER");
                    else
                        tv_Name.setText(editable.toString());

                }

            }
        });

        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (activity != null) {
                        activity.nextClick();
                        return true;
                    }

                }
                return false;
            }
        });


        et_name.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if (activity != null) {
                    hideSoftKeyboard(et_name);
                    activity.onBackPressed();
                }
            }
        });

        cardViewCCName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(et_name);
            }
        });

        return view;
    }

    public String getName() {
        if (et_name != null)
            return et_name.getText().toString().trim();

        return null;
    }


    private void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }
}

