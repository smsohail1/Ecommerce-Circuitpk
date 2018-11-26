package com.xekera.Ecommerce.ui.delivery_billing_details;

import android.content.Context;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

public class DeliveyBillingDetailsPresenter implements DeliveyBillingDetailsMVP.Presenter {

    private DeliveyBillingDetailsMVP.View view;
    private DeliveyBillingDetailsMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;

    public DeliveyBillingDetailsPresenter(Context context, DeliveyBillingDetailsMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }


    @Override
    public void setView(DeliveyBillingDetailsMVP.View view) {

    }
}
