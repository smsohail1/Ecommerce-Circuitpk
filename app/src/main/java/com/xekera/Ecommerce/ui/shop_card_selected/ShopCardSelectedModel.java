package com.xekera.Ecommerce.ui.shop_card_selected;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.util.Utils;

public class ShopCardSelectedModel implements ShopCardSelectedMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public ShopCardSelectedModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }
}