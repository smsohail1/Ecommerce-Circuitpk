package com.xekera.Ecommerce.ui.history;

import android.content.Context;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;

public interface HistoryMVP {

    interface View {

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void showRecylerViewProductsDetail(HistoryAdapter addToCartAdapter);

        void showRecyclerView();

        void hideRecyclerView();

        void setCartCounts(long counts);

        void setCartCounterTextview(int counts);

        void setParentFields();

        void txtNoCartItemFound();

        void hideNoCartItemFound();

        void setSubTotal(String setSubToal);


    }

    interface Presenter {
        void setView(HistoryMVP.View view);

        void fetchOrderDetails();

        void cancelOrder(Context context);

    }

    interface Model {

        void getOrderDetailsList(HistoryModel.IFetchOrderDetailsList iFetchOrderDetailsList);
    }
}
