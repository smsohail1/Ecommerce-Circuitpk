package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.util.Utils;

public class ShopDetailsModel implements ShopDetailsMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public ShopDetailsModel(XekeraAPI  xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }}