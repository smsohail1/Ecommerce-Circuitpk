package com.xekera.Ecommerce.ui.signup

import com.xekera.Ecommerce.util.SessionManager
import com.xekera.Ecommerce.util.Utils

class SignupModel(utils: Utils, sessionManager: SessionManager) : SignupMVP.Model {

    private var utils: Utils? = null
    private var sessionManager: SessionManager? = null


    fun SignupModel(utils: Utils, sessionManager: SessionManager) {
        //  this.model = model
        this.utils = utils
        this.sessionManager = sessionManager
    }

    override fun signUP(userName: String, password: String, phoneNo: String, emailID: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}