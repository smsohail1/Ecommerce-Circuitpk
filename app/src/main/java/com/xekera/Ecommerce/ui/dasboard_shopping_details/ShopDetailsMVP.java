package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.content.Context;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;

import java.util.List;

public interface ShopDetailsMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showRecylerViewProductsDetail(ShopDetailsAdapter shopDetailsAdapter);


    }

    interface Presenter {
        void setView(ShopDetailsMVP.View view);

        void setRecylerViewItems(Context context, List<ShoppingDetailModel> items);

    }

    interface Model {


    }
}
