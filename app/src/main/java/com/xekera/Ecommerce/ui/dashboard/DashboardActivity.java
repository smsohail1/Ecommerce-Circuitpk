package com.xekera.Ecommerce.ui.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;


public class DashboardActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_base;
    }

    @Override
    public Fragment getFragment() {
        return new DashboardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
