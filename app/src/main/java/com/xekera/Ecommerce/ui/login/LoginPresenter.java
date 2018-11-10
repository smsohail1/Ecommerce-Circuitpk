package com.xekera.Ecommerce.ui.login;


import android.view.View;
import android.widget.Toast;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.response.MessageResponse;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class LoginPresenter implements LoginMVP.Presenter {
    private LoginMVP.View view;
    private LoginMVP.Model model;
    private Utils utils;
    private SessionManager sessionManager;

    public LoginPresenter(LoginMVP.Model model,Utils utils,SessionManager sessionManager){
        this.model = model;
        this.utils = utils;
        this.sessionManager = sessionManager;
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void onClickBtnSignIn(final String username, String password,View view) {
        if(validateInputFields(username,password,view)) {
            if (utils.isInternetAvailable()) {
            }
        }
    }

    @Override
    public void oncClickBtnSignUp() {

    }


    private boolean validateInputFields(String username, String password,View viewActivity){
        if(utils.isTextNullOrEmpty(username)){
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.username_error_login),viewActivity);
            return false;
        }
        if(utils.isTextNullOrEmpty(password)){
            view.showSnackBarShortTime(utils.getStringFromResourceId(R.string.password_error_login),viewActivity);
            return false;
        }
        return true;
    }
}












