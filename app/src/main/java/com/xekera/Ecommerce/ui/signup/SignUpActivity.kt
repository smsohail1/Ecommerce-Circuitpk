package com.xekera.Ecommerce.ui.signup

import android.os.Bundle
import android.support.v4.app.Fragment
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.ui.BaseActivity

public class SignUpActivity : BaseActivity() {

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayout(): Int {
        return R.layout.activity_login_base_main
    }

    override fun getFragment(): Fragment {
        return SignupFragment()
    }
}
