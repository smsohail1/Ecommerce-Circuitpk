package com.xekera.Ecommerce.ui.login;


import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;
import java.util.logging.Handler;

public class LoginPresenter implements LoginMVP.Presenter {
    private LoginMVP.View view;
    private LoginMVP.Model model;
    private Utils utils;
    private SessionManager sessionManager;

    public LoginPresenter(LoginMVP.Model model, Utils utils, SessionManager sessionManager) {
        this.model = model;
        this.utils = utils;
        this.sessionManager = sessionManager;
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void onClickBtnSignIn(final String username, String password) {
        if (utils.isInternetAvailable()) {
            if (validateInputFields(username, password)) {

                view.showProgressDialogPleaseWait();

                model.signIn(username, password, new INetworkLoginSignup<LoginSuccessResponse>() {
                    @Override
                    public void onSuccess(LoginSuccessResponse response) {
                        view.hideProgressDialogPleaseWait();
                        if (response == null) {
                            view.showToastShortTime("Server not responding.");

                            return;
                        } else {
                            if (response.getStatus()) {
                                sessionManager.setIsLoggedIn(true);
                                view.showToastShortTime(response.getMessage());
                                view.loggedInSuccessfully();

                            } else {
                                view.showToastShortTime(response.getMessage());

                            }
                        }
                    }

//                    @Override
//                    public void onErrorList(MessageResponse messageResponse) {
//                        view.hideProgressDialogPleaseWait();
//                        view.showToastLongTime(messageResponse.getMessageCode() + ": " + messageResponse.getMessageText());
//                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        view.hideProgressDialogPleaseWait();
                        if (t.getMessage() != null) {
                            view.showToastLongTime(t.getMessage());
                        } else {
                            view.showToastLongTime("Error while login.");
                        }
                    }
                });


            }
        } else {
            view.showToastShortTime("Please connect to internet.");
        }
    }

    @Override
    public void oncClickBtnSignUp() {

    }


    private boolean validateInputFields(String username, String password) {
        if (utils.isTextNullOrEmpty(username)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.username_error_login));
            return false;
        }
        if (utils.isTextNullOrEmpty(password)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.password_error_login));
            return false;
        }
        return true;
    }
}












