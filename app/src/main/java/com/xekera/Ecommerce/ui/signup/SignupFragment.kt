package com.xekera.Ecommerce.ui.signup

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xekera.Ecommerce.App
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.ui.BaseActivity
import com.xekera.Ecommerce.util.ProgressCustomDialogController
import com.xekera.Ecommerce.util.SnackUtil
import com.xekera.Ecommerce.util.ToastUtil
import com.xekera.Ecommerce.util.Utils
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject


/**
 * A simple {@link Fragment} subclass.
 */
class SignupFragment : Fragment(), View.OnClickListener,SignupMVP.View {


    @Inject
    lateinit  var utils: Utils
    @Inject
    lateinit  var toastUtil: ToastUtil
    @Inject
    lateinit var snackUtil: SnackUtil
    @Inject
    lateinit var presenter: SignupMVP.Presenter

    lateinit   var progressDialogControllerPleaseWait: ProgressCustomDialogController


    fun SignupFragment() {
        // Required empty public constructor
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (getActivity()!!.getApplication() as App).getAppComponent().inject(this)
    }


    fun setTitle() {
        (activity as BaseActivity).setTitle(getString(R.string.signup_title))
    }

    fun hideShoppingCartIcon() {
        (activity as BaseActivity).hideShoppingCartIcon()
    }


    fun hideLoginIcon() {
        (activity as BaseActivity).hideLoginIcon()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_signup, container, false)

       // initializeViews()

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews();

    }


    fun initializeViews() {

        progressDialogControllerPleaseWait = ProgressCustomDialogController(getActivity(), R.string.please_wait)

        utils?.hideSoftKeyboard(edtUsername)

        btnSignUp.setOnClickListener { view ->
            onClick(view)

        }

    }


    public override fun onResume() {
        super.onResume()
        this.presenter?.setView(this)
        try {
       //     setTitle()
         //   hideLoginIcon()
           // hideShoppingCartIcon()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.btnSignUp -> {
                val userName = edtUsername?.getText().toString()
                val password = edtPassword?.getText().toString()
                val phoneNo = edtUserPhoneNo?.getText().toString()
                val emailAddress = edtEmailAddress?.getText().toString()
                presenter?.onClickBtnSignUp(userName,password,phoneNo,emailAddress,view)
//                if (validateInputFields(userName, password, phoneNo, emailAddress,view)) {
//
//                    Snackbar.make(view,getStringFromResourceId(R.string.signup_successfully, this) , Snackbar.LENGTH_SHORT)
//                        .show()
//                    resetForm()
//                }
            }

        }
    }


    override fun showSnackBarShortTime(message: String, view: View) {
        snackUtil.showSnackBarShortTime(view, message)
    }

    override fun showSnackBarLongTime(message: String, view: View) {
        snackUtil.showSnackBarLongTime(view, message)
    }

//    private fun validateInputFields(
//        username: String,
//        password: String,
//        phoneNo: String,
//        emailAddress: String,view: View
//    ): Boolean {
//        if (username.equals("", ignoreCase = true) || username.isEmpty()) {
//
//
//            Snackbar.make(view,getStringFromResourceId(R.string.username_error, this) , Snackbar.LENGTH_SHORT)
//                .show()
//            //Toast.makeText(this, getStringFromResourceId(R.string.username_error, this), Toast.LENGTH_SHORT)
//            //  .show()
//            return false
//        }
//        if (password.equals("", ignoreCase = true) || password.isEmpty()) {
//            // Toast.makeText(this, getStringFromResourceId(R.string.password_error, this), Toast.LENGTH_SHORT)
//            //   .show()
//
//            Snackbar.make(view,getStringFromResourceId(R.string.password_error, this) , Snackbar.LENGTH_SHORT)
//                .show()
//            return false
//        }
//
//        if (phoneNo.equals("", ignoreCase = true) || phoneNo.isEmpty()) {
//            // Toast.makeText(this, getStringFromResourceId(R.string.phone_no_error, this), Toast.LENGTH_SHORT)
//            //   .show()
//
//            Snackbar.make(view,getStringFromResourceId(R.string.phone_no_error, this) , Snackbar.LENGTH_SHORT)
//                .show()
//
//            return false
//        }
//
//
//        if (emailAddress.equals("", ignoreCase = true) || emailAddress.isEmpty()) {
//            //   Toast.makeText(this, getStringFromResourceId(R.string.email_address_error, this), Toast.LENGTH_SHORT)
//            //     .show()
//
//            Snackbar.make(view,getStringFromResourceId(R.string.email_address_error, this) , Snackbar.LENGTH_SHORT)
//                .show()
//
//            return false
//        }
//
//
//        return true
//    }
//

    fun getStringFromResourceId(stringResourceId: Int, context: Context?): String {
        return if (context != null) {
            context.resources.getString(stringResourceId)
        } else {
            ""
        }
    }

    fun resetForm() {

        edtUsername.setText("")

        edtPassword.setText("")

        edtUserPhoneNo.setText("")

        edtEmailAddress.setText("")
    }

    override fun showToastShortTime(message: String) {
        toastUtil?.showToastShortTime(message)
    }

    override fun showToastLongTime(message: String) {
        toastUtil?.showToastLongTime(message)
    }

    override fun hideSoftKeyboard() {
        utils?.hideSoftKeyboard(edtUsername)
        utils?.hideSoftKeyboard(edtPassword)
        utils?.hideSoftKeyboard(edtUserPhoneNo)
        utils?.hideSoftKeyboard(edtEmailAddress)

    }

    override fun showProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait?.showDialog()
    }

    override fun hideProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait?.hideDialog()
    }


}
