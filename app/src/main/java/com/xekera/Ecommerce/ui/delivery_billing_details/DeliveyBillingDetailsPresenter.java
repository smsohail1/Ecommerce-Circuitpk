package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.content.Context;
import android.view.View;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

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
    public void saveDetails(String firstName, String lastName, String company, String phone,
                            String email, String streetAddress1, String streetAddress2,
                            String country, String stateCountry, String townCity, String paymode,
                            String notes, String flatCharges, String postalCode) {

        if (validateInputFields(firstName, lastName, company, phone, email, streetAddress1,
                streetAddress2, country, stateCountry, townCity, paymode, notes, flatCharges, postalCode)) {

            view.showBillingAmountDetailView(flatCharges);
        }

    }


    private boolean validateInputFields(String firstName, String lastName, String company, String phone,
                                        String email, String streetAddress1, String streetAddress2,
                                        String country, String stateCountry, String townCity,
                                        String paymode, String notes, String flatCharges, String postalCode) {
        if (utils.isTextNullOrEmpty(firstName)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.firstname_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(lastName)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.lastname_error));
            return false;
        }

        if (utils.isTextNullOrEmpty(phone)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.phone_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(email)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.email_error));
            return false;
        }

        if (utils.isTextNullOrEmpty(country)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.country_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(streetAddress1)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.house_no_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(streetAddress2)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.house_no_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(townCity) || townCity.equalsIgnoreCase("Select")) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.town_city_error));
            return false;
        } else if ((townCity.equalsIgnoreCase("Islamabad") || townCity.equalsIgnoreCase("Rawalpindi"))
                && !(stateCountry.equalsIgnoreCase("Punjab")
                || stateCountry.equalsIgnoreCase("Islamabad Capital Territory"))) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.error_valid_state_country));
            return false;
        }

        if (utils.isTextNullOrEmpty(stateCountry) || stateCountry.equalsIgnoreCase("Select")) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.state_country_error));
            return false;
        } else if ((stateCountry.equalsIgnoreCase("Punjab") ||
                stateCountry.equalsIgnoreCase("Islamabad Capital Territory"))
                && !(townCity.equalsIgnoreCase("Islamabad")
                || townCity.equalsIgnoreCase("Rawalpindi"))) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.error_valid_town_city));
            return false;
        }

        if (utils.isTextNullOrEmpty(postalCode)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.postal_code_error));
            return false;
        }
        if (utils.isTextNullOrEmpty(paymode)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.paymode_error));
            return false;
        }

        return true;
    }
}
