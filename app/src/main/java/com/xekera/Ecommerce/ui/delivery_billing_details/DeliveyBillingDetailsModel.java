package com.xekera.Ecommerce.ui.delivery_billing_details;

import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.util.Utils;

public class DeliveyBillingDetailsModel implements DeliveyBillingDetailsMVP.Model {

    private AppDatabase appDatabase;
    private Utils utils;

    public DeliveyBillingDetailsModel(AppDatabase appDatabase, Utils utils) {
        this.appDatabase = appDatabase;
        this.utils = utils;

    }

}
