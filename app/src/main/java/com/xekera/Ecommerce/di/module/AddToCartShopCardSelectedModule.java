package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details.AddToCartShopCardSelectedMVP;
import com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details.AddToCartShopCardSelectedModel;
import com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details.AddToCartShopCardSelectedPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class AddToCartShopCardSelectedModule {
    @Provides
    public AddToCartShopCardSelectedMVP.Presenter provideShopDetailsPresenter(Context context, AddToCartShopCardSelectedMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new AddToCartShopCardSelectedPresenter(context, model, sessionManager, utils);
    }

    @Provides
    public AddToCartShopCardSelectedMVP.Model provideShopDetailsModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return new AddToCartShopCardSelectedModel(xakeraAPI, appDatabase, utils);
    }
}
