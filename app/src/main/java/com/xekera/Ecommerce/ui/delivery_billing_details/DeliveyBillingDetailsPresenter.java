package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.regex.Pattern;

public class DeliveyBillingDetailsPresenter implements DeliveyBillingDetailsMVP.Presenter {

    private DeliveyBillingDetailsMVP.View view;
    private DeliveyBillingDetailsMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;

    public DeliveyBillingDetailsPresenter(Context context, DeliveyBillingDetailsMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }


    @Override
    public void setView(DeliveyBillingDetailsMVP.View view) {
        this.view = view;

    }

    @Override
    public void saveDetails(String firstName, String company, String phone,
                            String email, String streetAddress1,
                            String townCity, String paymode,
                            String notes, String flatCharges, String selfPickup) {

        if (validateInputFields(firstName, company, phone, email, streetAddress1,
                townCity, paymode, notes, flatCharges, selfPickup)) {

            view.showBillingAmountDetailView(flatCharges, firstName, company, phone, email, streetAddress1,
                    townCity, paymode, notes, selfPickup);
        }

    }


    private boolean validateInputFields(String firstName, String company, String phone,
                                        String email, String streetAddress1,
                                        String townCity,
                                        String paymode, String notes, String flatCharges, String selfPickup) {
        if (utils.isTextNullOrEmpty(firstName)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.firstname_error));
            return false;
        }

        if (utils.isTextNullOrEmpty(phone)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.phone_error));
            return false;
        }
        if (phone.length() < 11) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.phone_no_length_error));
            return false;
        }

        if (utils.isTextNullOrEmpty(email)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.email_error));
            return false;
        }


        if (!validEmail(email)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.email_address_invalid_error));
            return false;
        }


        if (flatCharges != null && !flatCharges.equalsIgnoreCase("") && !flatCharges.equalsIgnoreCase("0")) {
            if (utils.isTextNullOrEmpty(streetAddress1)) {
                view.showToastShortTime(utils.getStringFromResourceId(R.string.house_no_error));
                return false;
            }
            if (utils.isTextNullOrEmpty(townCity) || townCity.equalsIgnoreCase("Select")) {
                view.showToastShortTime(utils.getStringFromResourceId(R.string.town_city_error));
                return false;
            }
        }

//        if (utils.isTextNullOrEmpty(streetAddress1)) {
//            view.showToastShortTime(utils.getStringFromResourceId(R.string.house_no_error));
//            return false;
//        }


        if (utils.isTextNullOrEmpty(paymode)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.paymode_error));
            return false;
        }

        return true;
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
