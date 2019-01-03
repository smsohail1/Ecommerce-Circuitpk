package com.xekera.Ecommerce.ui.delivery_billing_details.stripe.CCFragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.xekera.Ecommerce.util.CreditCardExpiryTextWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class CCValidityFragment extends Fragment {


    @BindView(R.id.et_validity)
    CreditCardEditText et_validity;
    @BindView(R.id.cardViewCCValidity)
    CardView cardViewCCValidity;
    TextView tv_validity;

    StripePaymentActivity activity;
    CardFrontFragment cardFrontFragment;

    public CCValidityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccvalidity, container, false);
        ButterKnife.bind(this, view);

        activity = (StripePaymentActivity) getActivity();
        cardFrontFragment = activity.cardFrontFragment;


        tv_validity = cardFrontFragment.getValidity();
        et_validity.addTextChangedListener(new CreditCardExpiryTextWatcher(et_validity, tv_validity));

        et_validity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        et_validity.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if (activity != null) {
                    hideSoftKeyboard(et_validity);
                    activity.onBackPressed();
                }
            }
        });

        cardViewCCValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(et_validity);
            }
        });

        return view;
    }

    public String getValidity() {
        if (et_validity != null)
            return et_validity.getText().toString().trim();

        return null;
    }

    private void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }
}
