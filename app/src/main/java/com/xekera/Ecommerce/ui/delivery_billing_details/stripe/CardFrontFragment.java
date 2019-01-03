package com.xekera.Ecommerce.ui.delivery_billing_details.stripe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.util.FontTypeChange;

import static com.xekera.Ecommerce.util.CreditCardUtils.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFrontFragment extends Fragment {


    @BindView(R.id.tv_card_number)
    TextView tvNumber;
    @BindView(R.id.tv_member_name)
    TextView tvName;
    @BindView(R.id.tv_validity)
    TextView tvValidity;
    @BindView(R.id.ivType)
    ImageView ivType;

    FontTypeChange fontTypeChange;

    public CardFrontFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_front, container, false);
        ButterKnife.bind(this, view);
        fontTypeChange = new FontTypeChange(getActivity());
        tvNumber.setTypeface(fontTypeChange.get_fontface(3));
        tvName.setTypeface(fontTypeChange.get_fontface(3));

        return view;
    }

    public TextView getNumber() {
        return tvNumber;
    }

    public TextView getName() {
        return tvName;
    }

    public TextView getValidity() {
        return tvValidity;
    }

    public ImageView getCardType() {
        return ivType;
    }


    public void setCardType(int type) {
        ivType.setVisibility(View.VISIBLE);
        switch (type) {
            case VISA:
                ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_visa));
                break;
            case MASTERCARD:
                ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_mastercard));
                break;
            case AMEX:
                ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_american_express));
                break;
//            case DISCOVER:
//                ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_discover));
//                break;
            case NONE:
                ivType.setVisibility(View.INVISIBLE);

                ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_credit_card_placeholder));

                // ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_chip));
                break;

        }


    }


    public void setCardType(String cardNumber) {
        if (cardNumber == null) {
            ivType.setVisibility(View.INVISIBLE);
            ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_credit_card_placeholder));


        } else if (cardNumber.equalsIgnoreCase("") || cardNumber.isEmpty()) {
            ivType.setVisibility(View.INVISIBLE);
            ivType.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_credit_card_placeholder));


        }
    }


}
