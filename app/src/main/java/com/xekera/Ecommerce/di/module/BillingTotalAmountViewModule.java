package com.xekera.Ecommerce.di.module;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewMVP;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewModel;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewPresenter;
import com.xekera.Ecommerce.ui.login.LoginModel;
import com.xekera.Ecommerce.ui.login.LoginPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class BillingTotalAmountViewModule {

    @Provides
    public BillingTotalAmountViewMVP.Presenter provideBillingTotalAmountViewPresenter(BillingTotalAmountViewMVP.Model model, Utils utils, SessionManager sessionManager) {
        return new BillingTotalAmountViewPresenter(model, sessionManager, utils);
    }

    @Provides
    public BillingTotalAmountViewMVP.Model provideBillingTotalAmountViewModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
        return new BillingTotalAmountViewModel(appDatabase, utils, xekeraAPI);
    }
}
