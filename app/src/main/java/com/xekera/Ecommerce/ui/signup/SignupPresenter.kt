package com.xekera.Ecommerce.ui.signup

import android.util.Patterns
import android.view.View
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup
import com.xekera.Ecommerce.data.rest.response.LoginSignUPErrorResponse
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse
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
        if (utils?.isInternetAvailable()!!) {

            if (validateInputFields(userName, password, phoneNo, emailID, viewActivity)) {

                view?.showProgressDialogPleaseWait()

                model?.signUP(userName, password, phoneNo, emailID, object :
                    INetworkLoginSignup<SignUpSuccessResponse> {

//                    override fun onError(loginSignUPErrorResponse: LoginSignUPErrorResponse?) {
//                        view?.hideProgressDialogPleaseWait()
//                        view?.showToastShortTime(loginSignUPErrorResponse?.message.toString())
//
//
//                    }

                    override fun onSuccess(response: SignUpSuccessResponse) {
                        view?.hideProgressDialogPleaseWait()
                        if (response.status) {

                            sessionManager?.createLoginSession(userName, phoneNo, password, emailID, true, false, "")
                            view?.showToastShortTime(response.message)
                            view?.signUpSuccessfully();
                        } else {

                            view?.showToastShortTime(response.message)

                        }


                    }


                    override fun onFailure(t: Throwable) {
                        t.printStackTrace()
                        view?.hideProgressDialogPleaseWait()
                        if (t.message != null) {
                            view?.showToastShortTime(t.message.toString())
                        } else {
                            view?.showToastShortTime("Error while signup.")
                        }
                    }
                })

            }
        }else {
            view?.showToastShortTime("Please connect to internet.")
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
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.username_error_login)!!)
            return false
        }

        if ( username.length < 8) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.username_length_error_login)!!)
            return false
        }

        if (utils?.isTextNullOrEmpty(password)!!) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.password_error_login)!!)
            return false
        }

        if ( password.length < 8) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.password_length_error_login)!!)
            return false
        }
        if (utils?.isTextNullOrEmpty(phoneNo)!!) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.phone_no_error)!!)
            return false
        }

        if (  phoneNo.length < 11) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.phone_no_length_error)!!)
            return false
        }

        if (utils?.isTextNullOrEmpty(emailAddress)!!) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.email_address_error)!!)
            return false
        }

        if (!validEmail(emailAddress)) {
            view?.showToastShortTime(utils?.getStringFromResourceId(R.string.email_address_invalid_error)!!)
            return false
        }




        return true
    }

    private fun validEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

}