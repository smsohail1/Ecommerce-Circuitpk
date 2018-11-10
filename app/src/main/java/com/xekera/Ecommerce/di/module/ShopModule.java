package com.xekera.Ecommerce.di.module;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.dashboard.DashboardModel;
import com.xekera.Ecommerce.ui.dashboard.DashboardPresenter;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragmentMVP;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragmentModel;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragmentPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class ShopModule {

    @Provides
    public ShopFragmentMVP.Presenter provideShopPresenter(ShopFragmentMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new ShopFragmentPresenter(model, sessionManager, utils);
    }

    @Provides
    public ShopFragmentMVP.Model provideShopModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return  new ShopFragmentModel(xakeraAPI, appDatabase, utils);
    }
}
