package com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.StripePaymentActivity;
import com.xekera.Ecommerce.util.CreditCardEditText;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CCSecureCodeFragment extends Fragment {


    @BindView(R.id.et_cvv)
    CreditCardEditText et_cvv;
    @BindView(R.id.cardViewCCSecureCode)
    CardView cardViewCCSecureCode;
    TextView tv_cvv;

    StripePaymentActivity activity;

    public CCSecureCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccsecure_code, container, false);
        ButterKnife.bind(this, view);

        activity = (StripePaymentActivity) getActivity();

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (tv_cvv != null) {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tv_cvv.setText("XXX");
                    else
                        tv_cvv.setText(editable.toString());

                } else
                    Log.d(TAG, "afterTextChanged: cvv null");

            }
        });

        et_cvv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        et_cvv.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if (activity != null) {
                    hideSoftKeyboard(et_cvv);
                    activity.onBackPressed();
                }
            }
        });

        cardViewCCSecureCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(et_cvv);
            }
        });

        return view;

    }

    public void setCvv(TextView tv) {
        tv_cvv = tv;
    }

    public String getValue() {

        String getValue = "";

        if (et_cvv != null) {
            getValue = et_cvv.getText().toString().trim();
        }
        return getValue;
    }

    private void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }
}
