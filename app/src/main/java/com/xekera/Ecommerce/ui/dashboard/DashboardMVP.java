package com.xekera.Ecommerce.ui.dashboard;




import java.util.List;


public interface DashboardMVP {

    interface View {
        //void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter);


        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

    }

    interface Presenter {
        void setView(View view);

        void setDashboardItems();

    }

    interface Model {


    }

}
