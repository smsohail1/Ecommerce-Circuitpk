package com.xekera.Ecommerce.ui.shop_card_selected;


public interface ShopCardSelectedMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

    }

    interface Presenter {
        void setView(ShopCardSelectedMVP.View view);

    }

    interface Model {


    }
}
