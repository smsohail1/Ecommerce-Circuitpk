package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import android.content.Context;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class ShopDetailsPresenter implements ShopDetailsMVP.Presenter {
    private ShopDetailsMVP.View view;
    private ShopDetailsMVP.Model model;
    private SessionManager sessionManager;
    private ShopDetailsAdapter shopDetailsAdapter;
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

    @Override
    public void setRecylerViewItems(Context context, List<ShoppingDetailModel> items) {

       // shopDetailsAdapter = new ShopDetailsAdapter(context, items, this);
       // view.showRecylerViewProductsDetail(shopDetailsAdapter);
    }

}
