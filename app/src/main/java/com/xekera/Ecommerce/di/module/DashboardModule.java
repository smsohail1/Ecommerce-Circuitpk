package com.xekera.Ecommerce.di.module;


import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.dashboard.DashboardMVP;
import com.xekera.Ecommerce.ui.dashboard.DashboardModel;
import com.xekera.Ecommerce.ui.dashboard.DashboardPresenter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {


    @Provides
    public DashboardMVP.Presenter provideHomePresenter(DashboardMVP.Model model, SessionManager sessionManager, Utils utils) {
        return new DashboardPresenter(model, sessionManager, utils);
    }

    @Provides
    public DashboardMVP.Model provideHomeModel(XekeraAPI xakeraAPI, AppDatabase appDatabase, Utils utils) {
        return new DashboardModel(xakeraAPI, appDatabase, utils);
    }
}










