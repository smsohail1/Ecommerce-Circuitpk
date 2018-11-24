package com.xekera.Ecommerce.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.LoginBaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getToolbar().setVisibility(View.GONE);
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public int getLayout() {
        return

                //  R.layout.activity_login_base;
                R.layout.activity_base;
    }

    @Override
    public Fragment getFragment() {
        return new LoginFragment();
    }
}

















