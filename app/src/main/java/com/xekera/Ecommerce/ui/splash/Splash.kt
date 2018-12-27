package com.xekera.Ecommerce.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.ui.dashboard.DashboardActivity
import com.xekera.Ecommerce.R.id.imageView
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView

class Splash : AppCompatActivity() {

    var anim: Animation? = null
    var imgLogo: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val dashboardActivity = Intent(applicationContext, DashboardActivity::class.java)
            startActivity(dashboardActivity)
            finish()

        }, 2000)

    }



}