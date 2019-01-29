package com.xekera.Ecommerce.di.module;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.history.HistoryMVP;
import com.xekera.Ecommerce.ui.history.HistoryModel;
import com.xekera.Ecommerce.ui.history.HistoryPresenter;
import com.xekera.Ecommerce.ui.history.history_description.HistoryDesciptionMVP;
import com.xekera.Ecommerce.ui.history.history_description.HistoryDesciptionModel;
import com.xekera.Ecommerce.ui.history.history_description.HistoryDesciptionPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;


@Module
public class HistoryDesciptionModule {

    @Provides
    public HistoryDesciptionMVP.Presenter provideHistoryPresenter(Context context, HistoryDesciptionMVP.Model model,
                                                                  SessionManager sessionManager, Utils utils) {
        return new HistoryDesciptionPresenter(context, model, sessionManager, utils);
    }

    @Provides
    public HistoryDesciptionMVP.Model provideHistoryModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
        return new HistoryDesciptionModel(appDatabase, utils,xekeraAPI);
    }
}
