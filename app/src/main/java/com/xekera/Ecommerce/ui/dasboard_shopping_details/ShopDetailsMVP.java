package com.xekera.Ecommerce.ui.dasboard_shopping_details;


public interface ShopDetailsMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);
    }

    interface Presenter {
        void setView(ShopDetailsMVP.View view);


    }

    interface Model {


    }
}
