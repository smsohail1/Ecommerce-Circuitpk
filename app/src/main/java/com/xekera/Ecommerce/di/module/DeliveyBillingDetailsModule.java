package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsMVP;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsModel;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class DeliveyBillingDetailsModule {

    @Provides
    public DeliveyBillingDetailsMVP.Presenter provideDeliveyBillingDetailsPresenter(Context context, DeliveyBillingDetailsMVP.Model model, Utils utils, SessionManager sessionManager) {
        return new DeliveyBillingDetailsPresenter(context, model, sessionManager, utils);
    }

    @Provides
    public DeliveyBillingDetailsMVP.Model provideDeliveyBillingDetailsModel(AppDatabase appDatabase, Utils utils) {
        return new DeliveyBillingDetailsModel(appDatabase, utils);
    }
}
