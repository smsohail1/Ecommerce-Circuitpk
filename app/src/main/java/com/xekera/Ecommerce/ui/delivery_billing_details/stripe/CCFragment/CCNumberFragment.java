package com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
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
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CardFrontFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.StripePaymentActivity;
import com.xekera.Ecommerce.util.CreditCardEditText;
import com.xekera.Ecommerce.util.CreditCardFormattingTextWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class CCNumberFragment extends Fragment {


    @BindView(R.id.et_number)
    CreditCardEditText et_number;
    @BindView(R.id.cardViewCCNumber)
    CardView cardViewCCNumber;
    TextView tv_number;

    StripePaymentActivity activity;
    CardFrontFragment cardFrontFragment;

    public CCNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccnumber, container, false);
        ButterKnife.bind(this, view);

        activity = (StripePaymentActivity) getActivity();
        cardFrontFragment = activity.cardFrontFragment;

        tv_number = cardFrontFragment.getNumber();

        //Do your stuff
        et_number.addTextChangedListener(new CreditCardFormattingTextWatcher(et_number, tv_number, cardFrontFragment.getCardType(),
                new CreditCardFormattingTextWatcher.CreditCardType() {
                    @Override
                    public void setCardType(int type) {
                        Log.d("Card", "setCardType: " + type);

                        cardFrontFragment.setCardType(type);
                    }
                }));

        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cardFrontFragment.setCardType(s.toString());


            }
        });
        et_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        et_number.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if (activity != null) {
                    hideSoftKeyboard(et_number);
                    activity.onBackPressed();
                }
            }
        });


        cardViewCCNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(et_number);
            }
        });
        return view;
    }

    public String getCardNumber() {
        if (et_number != null)
            return et_number.getText().toString().trim();

        return null;
    }


    private void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }
}
