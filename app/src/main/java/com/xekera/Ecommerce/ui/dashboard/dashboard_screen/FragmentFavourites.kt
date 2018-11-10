package com.xekera.Ecommerce.ui.dashboard.dashboard_screen

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.xekera.Ecommerce.App
import com.xekera.Ecommerce.R
import com.xekera.Ecommerce.ui.BaseActivity

class FragmentFavourites : Fragment() {

    fun Fragment_favourites() {
        // Required empty public constructor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (getActivity()!!.getApplication() as App).appComponent.inject(this)
        //   requestPermissionsFromUser();
    }

    override fun onResume() {
        super.onResume()
        try {
setTitle();
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    fun setTitle() {
        (activity as BaseActivity).setTitle(getString(R.string.favourite_dashboard))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_favourite, container, false)

        initializeViews(v)

        return v
    }

    private fun initializeViews(v: View) {
        ButterKnife.bind(this, v)


    }
}