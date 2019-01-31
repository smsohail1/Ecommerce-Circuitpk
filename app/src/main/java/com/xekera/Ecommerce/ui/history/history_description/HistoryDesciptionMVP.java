package com.xekera.Ecommerce.ui.history.history_description;

import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.*;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.HistoryDesciptionAdapter;

import java.util.List;

public interface HistoryDesciptionMVP {

    interface View {

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void showRecylerViewProductsDetail(HistoryDesciptionAdapter addToCartAdapter);

        void showRecyclerView();

        void hideRecyclerView();

        void setCartCounts(long counts);

        void setCartCounterTextview(int counts);

        void setParentFields();

        void txtNoCartItemFound();

        void hideNoCartItemFound();

        void setSubTotal(String setSubToal);

        void setAdapter(List<Booking> addToCarts);

        void showOrderCompleteSuccessDialog();

        void hideLoadingProgressDialog();

        void showSearchData();

        void hideSearchDate();

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void setHistoryAdapter(HistoryOrderIdDiscriptionResponse response);
    }

    interface Presenter {
        void setView(HistoryDesciptionMVP.View view);

        void fetchOrderDetails();

        void fetchCartsCount();

        void fetchOrderIdDescription(String orderId);


    }

    interface Model {

        void getOrderDetailsList(HistoryDesciptionModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void getCartDetailsList(HistoryDesciptionModel.IFetchCartDetailsList iFetchCartDetailsList);

        void fetchOrderHistoryIdDescription(String orderId, INetworkListGeneral<HistoryOrderIdDiscriptionResponse> iNetworkListGeneral);

    }
}
