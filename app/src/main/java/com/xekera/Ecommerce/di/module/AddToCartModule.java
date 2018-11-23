package com.xekera.Ecommerce.di.module;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartMVP;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class AddToCartModule {
    @Provides
    public AddToCartMVP.Presenter provideAddToCartPresenter(AddToCartMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new AddToCartPresenter(model, sessionManager, utils);
    }

    @Provides
    public AddToCartMVP.Model provideAddToCartModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return new AddToCartModel(xakeraAPI, appDatabase, utils);
    }
}
