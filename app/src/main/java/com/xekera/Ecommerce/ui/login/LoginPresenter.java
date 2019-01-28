package com.xekera.Ecommerce.ui.login;


import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.response.ForgotPasswordResponse;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;
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
    public void onClickBtnSignIn(final String username, final String password) {
        if (utils.isInternetAvailable()) {
            if (validateInputFields(username, password)) {

                if (sessionManager.getKeyIsFacebookLogin()) {
                    view.showToastShortTime("User is already logged In from facebook.");
                    return;
                } else if (sessionManager.isLoggedIn()) {
                    view.showToastShortTime("User is already logged In.");
                    return;
                }
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
                                // sessionManager.setIsLoggedIn(true);
                                sessionManager.createLogin(username, "", password, response.getEmail(), true, false, "", true);
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
//                        view.showToastShortTime(messageResponse.getMessageCode() + ": " + messageResponse.getMessageText());
//                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        view.hideProgressDialogPleaseWait();
                        if (t.getMessage() != null) {
                            view.showToastShortTime(t.getMessage());
                        } else {
                            view.showToastShortTime("Error while login.");
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

    @Override
    public void registerFacebookUser(String username, String password, String phoneNo, String emailID) {

        model.registerFacebookUser(username, password, phoneNo, emailID, new INetworkLoginSignup<SignUpSuccessResponse>() {
            @Override
            public void onSuccess(SignUpSuccessResponse response) {
              /*  if (response == null) {
                    view.showToastShortTime("Server not responding.");

                    return;
                } else {
                    if (response.getStatus()) {
                        view.showToastShortTime(response.getMessage());

                    } else {
                        view.showToastShortTime(response.getMessage());
                    }
                }*/
            }

            @Override
            public void onFailure(Throwable t) {
              /*  t.printStackTrace();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while login.");
                }*/
            }
        });
    }

    @Override
    public void resetPassword(String password, String emailID) {
        view.showProgressDialogPleaseWait();
        model.resetPassword(password, emailID, new INetworkLoginSignup<ForgotPasswordResponse>() {
            @Override
            public void onSuccess(ForgotPasswordResponse response) {
                view.hideProgressDialogPleaseWait();

                if (response == null) {
                    view.hideForgotPasswordDialog();

                    view.showToastShortTime("Server not responding");

                    return;
                } else {
                    if (response.getStatus()) {
                        view.hideForgotPasswordDialog();

                        view.showToastShortTime(response.getMessage());
                    } else {

                        view.showToastShortTime(response.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while forgot password.");
                }
            }
        });
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












