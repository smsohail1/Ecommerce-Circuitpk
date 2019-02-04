package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.view.View;
import com.stripe.android.model.Card;

public interface DeliveyBillingDetailsMVP {

    interface View {


        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void showBillingAmountDetailView(String flatCharges, String firstName, String company, String phone,
                                         String email, String streetAddress1,
                                         String townCity, String paymode,
                                         String notes, String selfPickup, String cardNumber, String expiryDate, String CVCNumber);

    }

    interface Presenter {
        void setView(DeliveyBillingDetailsMVP.View view);

        void saveDetails(String firstName, String company, String phone, String email,
                         String streetAddress1,
                         String townCity, String paymode, String notes,
                         String flatCharges, String selfPickup, String cardNumber, String expiryDate, String CVCNumber);

    }

    interface Model {


    }

}
