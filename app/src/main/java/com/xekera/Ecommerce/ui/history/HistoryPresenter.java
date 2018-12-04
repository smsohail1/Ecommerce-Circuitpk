package com.xekera.Ecommerce.ui.history;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
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
                    setAdapter(addToCarts);
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

    boolean isShowing = false;

    @Override
    public void cancelOrder(final Context context) {
        isShowing = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //isShowing = true;
               // if (isShowing)
                    showCancelDialog(context, "Alert!", "Do you want to cancel this order?");

            }
        }, 200);
    }


    private void showCancelDialog(Context context, String title, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_cancel_dialog);

        Button cancel = dialog.findViewById(R.id.cancel);
        Button submit = dialog.findViewById(R.id.submit);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);

        txtMessage.setText("" + message);
        txtTitle.setText("" + title);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sessionManager.logoutUser();
                //  startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
            dialog.show();



    }

    private void setAdapter(List<Booking> bookings) {
        if (adapter == null) {
            adapter = new HistoryAdapter(context, bookings, this);
            view.showRecylerViewProductsDetail(adapter);
        } else {
            adapter.removeAll();
            adapter.addAll(bookings);
        }
        getSubTotal(bookings);
    }


    private void getSubTotal(List<Booking> addToCarts) {
        long price = 0;

        for (Booking i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice()) + Long.valueOf(i.getFlatCharges());
        }
        view.setSubTotal(String.valueOf(price));
        // view.setCartCounts(addToCarts.size());
        // view.setCartCounterTextview(addToCarts.size());


    }


}