package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingMVP;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingModel;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingPresenter;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.favourites.FavouritesPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class ContinueShoppingModule {
    @Provides
    public ContinueShoppingMVP.Presenter provideContinueShoppingPresenter(Context context, ContinueShoppingMVP.Model model, SessionManager sessionManager, Utils utils, AppDatabase appDatabase) {
        return new ContinueShoppingPresenter(context, model, sessionManager, utils, appDatabase);
    }

    @Provides
    public ContinueShoppingMVP.Model provideContinueShoppingModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        return new ContinueShoppingModel(xekeraAPI, appDatabase, utils);
    }
}
