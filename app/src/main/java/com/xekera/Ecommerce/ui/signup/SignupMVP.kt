package com.xekera.Ecommerce.ui.signup

import com.xekera.Ecommerce.data.rest.INetworkLoginSignup
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse


public interface SignupMVP {

    interface View {
        fun showToastShortTime(message: String)
        fun showToastLongTime(message: String)
        fun hideSoftKeyboard()
        fun showProgressDialogPleaseWait()
        fun hideProgressDialogPleaseWait()
        fun showSnackBarShortTime(message: String, view: android.view.View)
        fun showSnackBarLongTime(message: String, view: android.view.View)
        fun signUpSuccessfully();
    }

    interface Presenter {
        fun setView(view: SignupMVP.View)
        fun onClickBtnSignUp(
            userName: String,
            password: String,
            phoneNo: String,
            emailID: String,
            viewActivity: android.view.View
        )
    }

    interface Model {
        fun signUP(
            userName: String, password: String, phoneNo: String, emailID: String, iNetworkLoginSignup: INetworkLoginSignup<SignUpSuccessResponse>
        )
    }
}