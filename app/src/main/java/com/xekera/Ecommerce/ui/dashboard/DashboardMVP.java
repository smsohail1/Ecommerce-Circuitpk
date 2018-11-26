package com.xekera.Ecommerce.ui.dashboard;


import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;

import java.util.List;


public interface DashboardMVP {

    interface View {
        //void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter);


        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void setCartCounts(long counts);

        void setCartLabel();

    }

    interface Presenter {
        void setView(View view);

        void setDashboardItems();

        void getCartCountList();

    }

    interface Model {

        void getCartCountList(DashboardModel.IFetchCartDetailsList iFetchCartDetailsList);
    }

}
