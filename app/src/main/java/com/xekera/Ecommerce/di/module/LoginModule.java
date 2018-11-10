package com.xekera.Ecommerce.di.module;


import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.ui.login.LoginMVP;
import com.xekera.Ecommerce.ui.login.LoginModel;
import com.xekera.Ecommerce.ui.login.LoginPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginMVP.Presenter provideLoginPresenter(LoginMVP.Model model, Utils utils, SessionManager sessionManager){
        return new LoginPresenter(model,utils,sessionManager);
    }

    @Provides
    public LoginMVP.Model provideLoginModel(XekeraAPI xekeraAPI, Utils utils){
        return new LoginModel(xekeraAPI,utils);
    }
}










