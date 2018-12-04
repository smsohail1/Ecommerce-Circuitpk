package com.xekera.Ecommerce.ui.signup

import android.view.View
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils

public class SignupPresenter : SignupMVP.Presenter {

    private var view: SignupMVP.View? = null
    private var model: SignupMVP.Model? = null
    private var utils: Utils? = null
    private var sessionManager: SessionManager? = null

    constructor(
        utils: Utils,
        sessionManager: SessionManager,
        model: SignupMVP.Model
    ) {
        this.utils = utils
        this.sessionManager = sessionManager
        this.model = model
    }

//     SignupPresenter(utils: Utils, sessionManager: SessionManager,model :SignupMVP.Model) {
//        this.utils = utils
//        this.sessionManager = sessionManager
//        this.model= model
//    }

    override fun setView(view: SignupMVP.View) {
        this.view = view;
    }


    override fun onClickBtnSignUp(
        userName: String,
        password: String,
        phoneNo: String,
        emailID: String,
        viewActivity: View
    ) {

        if (validateInputFields(userName, password, phoneNo, emailID, viewActivity)) {
            sessionManager?.createLoginSession(userName, phoneNo, password, emailID)
            view?.signUpSuccessfully();
//            if (utils?.isInternetAvailable()!!) {
//
//            }
        }
    }


    private fun validateInputFields(
        username: String,
        password: String,
        phoneNo: String,
        emailAddress: String,
        viewActivity: View
    ): Boolean {
        if (utils?.isTextNullOrEmpty(username)!!) {
            view?.showSnackBarShortTime(utils?.getStringFromResourceId(R.string.username_error_login)!!, viewActivity)
            return false
        }
        if (utils?.isTextNullOrEmpty(password)!!) {
            view?.showSnackBarShortTime(utils?.getStringFromResourceId(R.string.password_error_login)!!, viewActivity)
            return false
        }

        if (utils?.isTextNullOrEmpty(phoneNo)!!) {
            view?.showSnackBarShortTime(utils?.getStringFromResourceId(R.string.phone_no_error)!!, viewActivity)
            return false
        }
        if (utils?.isTextNullOrEmpty(emailAddress)!!) {
            view?.showSnackBarShortTime(utils?.getStringFromResourceId(R.string.email_address_error)!!, viewActivity)
            return false
        }


        return true
    }
}