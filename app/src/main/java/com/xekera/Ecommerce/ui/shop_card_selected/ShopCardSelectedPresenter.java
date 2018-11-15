package com.xekera.Ecommerce.ui.shop_card_selected;

import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

public class ShopCardSelectedPresenter implements ShopCardSelectedMVP.Presenter {
    private ShopCardSelectedMVP.View view;
    private ShopCardSelectedMVP.Model model;
    private DashboardAdapter homeAdapter;
    private SliderAdapter sliderAdapter;
    private SessionManager sessionManager;
    private Utils utils;

    public ShopCardSelectedPresenter(ShopCardSelectedMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(ShopCardSelectedMVP.View view) {
        this.view = view;
    }

}
