package com.xekera.Ecommerce.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.ui.dashboard.DashboardActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val dashboardActivity = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(dashboardActivity)
            finish()

        }, 1000)


    }
}