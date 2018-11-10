package com.xekera.Ecommerce.ui.dashboard;


import java.util.List;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardModel implements DashboardMVP.Model {
    private XekeraAPI   xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public DashboardModel(XekeraAPI  xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }

}












