package com.xekera.Ecommerce.ui.dashboard_shopping;


import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;

public interface ShopFragmentMVP {


    interface View {
        void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter);

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

    }

    interface Presenter {
        void setView(ShopFragmentMVP.View view);

        void setDashboardItems();

    }

    interface Model {


    }
}
