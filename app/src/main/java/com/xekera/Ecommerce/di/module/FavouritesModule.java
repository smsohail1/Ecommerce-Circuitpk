package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.favourites.FavouritesMVP;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.favourites.FavouritesPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;

@Module
public class FavouritesModule {
    @Provides
    public FavouritesMVP.Presenter provideFavouritesPresenter(Context context, FavouritesMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new FavouritesPresenter(context, model, sessionManager, utils);
    }

    @Provides
    public FavouritesMVP.Model provideFavouritesModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
        return new FavouritesModel(appDatabase, utils, xekeraAPI);
    }
}
