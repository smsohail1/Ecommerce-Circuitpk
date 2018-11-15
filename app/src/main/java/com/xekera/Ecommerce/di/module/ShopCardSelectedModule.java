package com.xekera.Ecommerce.di.module;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedMVP;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class ShopCardSelectedModule {

    @Provides
    public ShopCardSelectedMVP.Presenter provideShopDetailsPresenter(ShopCardSelectedMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new ShopCardSelectedPresenter(model, sessionManager, utils);
    }

    @Provides
    public ShopCardSelectedMVP.Model provideShopDetailsModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return new ShopCardSelectedModel(xakeraAPI, appDatabase, utils);
    }
}
