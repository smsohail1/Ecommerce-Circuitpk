package com.xekera.Ecommerce.ui.login;


import android.view.View;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.response.ForgotPasswordResponse;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;

public interface LoginMVP {

    interface View {
        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void hideSoftKeyboard();

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showHomeScreen();

        void showSnackBarShortTime(String message);


        void loggedInSuccessfully();

        void  hideForgotPasswordDialog();

        void  showlogoutOption();
    }

    interface Presenter {
        void setView(LoginMVP.View view);

        void onClickBtnSignIn(String username, String password);

        void oncClickBtnSignUp();

        void registerFacebookUser(String username, String password, String phoneNo, String emailID);

        void resetPassword(String password, String emailID);
    }

    interface Model {
        void signIn(String username, String password, INetworkLoginSignup<LoginSuccessResponse> iNetworkLoginSignup);

        void registerFacebookUser(String username, String password, String phoneNo, String emailID,
                                  INetworkLoginSignup<SignUpSuccessResponse> iNetworkLoginSignup);

        void resetPassword(String password, String emailID, INetworkLoginSignup<ForgotPasswordResponse> iNetworkLoginSignup);

    }
}












