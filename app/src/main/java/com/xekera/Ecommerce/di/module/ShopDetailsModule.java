package com.xekera.Ecommerce.di.module;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsMVP;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class ShopDetailsModule {

    @Provides
    public ShopDetailsMVP.Presenter provideShopDetailsPresenter(ShopDetailsMVP.Model model, SessionManager sessionManager, Utils utils,AppDatabase appDatabase) {
        return new ShopDetailsPresenter(model, sessionManager, utils,appDatabase);
    }

    @Provides
    public ShopDetailsMVP.Model provideShopDetailsModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return  new ShopDetailsModel(xakeraAPI, appDatabase, utils);
    }
}
