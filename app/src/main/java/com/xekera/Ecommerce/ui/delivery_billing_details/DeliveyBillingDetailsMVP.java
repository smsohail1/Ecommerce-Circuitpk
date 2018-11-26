package com.xekera.Ecommerce.ui.delivery_billing_details;

public interface DeliveyBillingDetailsMVP {

    interface View {


        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);


    }

    interface Presenter {
        void setView(DeliveyBillingDetailsMVP.View view);

    }

    interface Model {


    }

}
