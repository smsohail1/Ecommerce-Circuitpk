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
                            String townCity, String paymode,
                            String notes, String flatCharges) {

        if (validateInputFields(firstName, lastName, company, phone, email, streetAddress1,
                streetAddress2, townCity, paymode, notes, flatCharges)) {

            view.showBillingAmountDetailView(flatCharges, firstName, lastName, company, phone, email, streetAddress1, streetAddress2,
                    townCity, paymode, notes);
        }

    }


    private boolean validateInputFields(String firstName, String lastName, String company, String phone,
                                        String email, String streetAddress1, String streetAddress2,
                                        String townCity,
                                        String paymode, String notes, String flatCharges) {
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
        }


        if (utils.isTextNullOrEmpty(paymode)) {
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.paymode_error));
            return false;
        }

        return true;
    }
}
