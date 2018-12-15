package com.xekera.Ecommerce.di.module

import com.xekera.Ecommerce.data.rest.XekeraAPI
import com.xekera.Ecommerce.ui.signup.SignupMVP
import com.xekera.Ecommerce.ui.signup.SignupModel
import com.xekera.Ecommerce.ui.signup.SignupPresenter
import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils
import dagger.Module
import dagger.Provides


@Module
class SignupModule {

    @Provides
    fun provideSignupPresenter(utils: Utils, sessionManager: SessionManager,model :SignupMVP.Model): SignupMVP.Presenter {
        return SignupPresenter(utils, sessionManager,model)
    }

    @Provides
    fun provideSignupModel(utils: Utils, sessionManager: SessionManager,xekeraAPI: XekeraAPI): SignupMVP.Model {
        return SignupModel(utils, sessionManager,xekeraAPI)
    }
}