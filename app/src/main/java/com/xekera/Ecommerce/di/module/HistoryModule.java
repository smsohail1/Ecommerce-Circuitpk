package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.history.HistoryMVP;
import com.xekera.Ecommerce.ui.history.HistoryModel;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class HistoryModule {


    @Provides
    public HistoryMVP.Presenter provideHistoryPresenter(Context context, HistoryMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new HistoryPresenter(context, model, sessionManager, utils);
    }

    @Provides
    public HistoryMVP.Model provideHistoryModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
        return new HistoryModel(appDatabase, utils,xekeraAPI);
    }
}
