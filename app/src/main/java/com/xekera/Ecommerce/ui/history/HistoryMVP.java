package com.xekera.Ecommerce.ui.history;

import android.content.Context;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;

import java.util.List;

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

        void setAdapter(List<Booking> addToCarts);



    }

    interface Presenter {
        void setView(HistoryMVP.View view);

        void fetchOrderDetails();

        void fetchCartsCount();


    }

    interface Model {

        void getOrderDetailsList(HistoryModel.IFetchOrderDetailsList iFetchOrderDetailsList);
        void getCartDetailsList(HistoryModel.IFetchCartDetailsList iFetchCartDetailsList);

    }
}
