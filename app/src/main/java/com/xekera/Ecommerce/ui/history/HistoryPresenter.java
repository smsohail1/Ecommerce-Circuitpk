package com.xekera.Ecommerce.ui.history;

import android.app.Dialog;
import android.content.Context;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class HistoryPresenter implements HistoryMVP.Presenter {
    private HistoryMVP.View view;
    private HistoryMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;
    private HistoryAdapter adapter;


    public HistoryPresenter(Context context, HistoryMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(HistoryMVP.View view) {
        this.view = view;

    }

    @Override
    public void fetchOrderDetails() {

        model.getOrderDetailsList(new HistoryModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Booking> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    // view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();

                    view.showRecyclerView();
                    view.setAdapter(addToCarts);
                    // setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.hideRecyclerView();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

//    private void setAdapter(List<Booking> bookings) {
//        if (adapter == null) {
//            adapter = new HistoryAdapter(context, bookings, this);
//            view.showRecylerViewProductsDetail(adapter);
//        } else {
//            adapter.removeAll();
//            adapter.addAll(bookings);
//        }
//        getSubTotal(bookings);
//    }


//    private void getSubTotal(List<Booking> addToCarts) {
//        long price = 0;
//
//        for (Booking i : addToCarts) {
//            price = price + Long.valueOf(i.getItemPrice()) + Long.valueOf(i.getFlatCharges());
//        }
//        view.setSubTotal(String.valueOf(price));
//        // view.setCartCounts(addToCarts.size());
//        // view.setCartCounterTextview(addToCarts.size());
//    }


}