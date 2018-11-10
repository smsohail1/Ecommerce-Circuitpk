package com.xekera.Ecommerce.ui.login;


import android.view.View;

public interface LoginMVP {

    interface View{
        void showToastShortTime(String message);
        void showToastLongTime(String message);
        void hideSoftKeyboard();
        void showProgressDialogPleaseWait();
        void hideProgressDialogPleaseWait();
        void showSnackBarShortTime(String message, android.view.View view);
        void showSnackBarLongTime(String message, android.view.View view);
        void showHomeScreen();
    }

    interface Presenter{
        void setView(LoginMVP.View view);
        void onClickBtnSignIn(String username, String password, android.view.View view);
        void oncClickBtnSignUp();
    }

    interface Model{
        void signIn(String username, String password);
    }
}












