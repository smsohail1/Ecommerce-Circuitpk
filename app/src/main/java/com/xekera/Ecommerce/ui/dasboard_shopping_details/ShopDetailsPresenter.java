package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

public class ShopDetailsPresenter implements ShopDetailsMVP.Presenter {
    private ShopDetailsMVP.View view;
    private ShopDetailsMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;

    public ShopDetailsPresenter(ShopDetailsMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(ShopDetailsMVP.View view) {
        this.view = view;
    }

}
