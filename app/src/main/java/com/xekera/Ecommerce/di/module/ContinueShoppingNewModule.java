package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.continue_shopping.*;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;

@Module
public class ContinueShoppingNewModule {
    @Provides
    public ShopFragmentMVP.Presenter provideContinueShoppingPresenter(ShopFragmentMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new ShopFragmentPresenter(model, sessionManager, utils);
    }

    @Provides
    public ShopFragmentMVP.Model provideContinueShoppingModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        return new ShopFragmentModel(xekeraAPI, appDatabase, utils);
    }
}
