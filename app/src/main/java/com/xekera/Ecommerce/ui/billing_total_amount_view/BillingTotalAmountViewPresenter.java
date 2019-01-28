package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.util.Log;
import com.google.gson.Gson;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class BillingTotalAmountViewPresenter implements BillingTotalAmountViewMVP.Presenter {
    private BillingTotalAmountViewMVP.View view;
    private BillingTotalAmountViewMVP.Model model;
    private BillingTotalAmountViewAdapter adapter;

    private SessionManager sessionManager;
    private Utils utils;

    public BillingTotalAmountViewPresenter(BillingTotalAmountViewMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(BillingTotalAmountViewMVP.View view) {
        this.view = view;
    }

    @Override
    public void fetchCartDetails() {
        model.getCartDetailsList(new BillingTotalAmountViewModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.setCartCounts(0);
                    return;
                } else {
                    view.showRecyclerView();
                    view.cartLists(addToCarts);
                    view.setAdapter(addToCarts);
                    //setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.setCartCounts(0);

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void deleteCartItems(List<String> items) {

        model.removeSelectedCartDetails(items, new BillingTotalAmountViewModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.itemRemovedFromCart();
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });

    }

    @Override
    public void insertBooking(final List<Booking> addToCart, String dateTime, final String name,
                              final String companyName, final String phoneNo, final String email, String address, String paymentMode, String orderNotes,
                              String selfPikup, String flatCharges) {
        model.insertBooking(addToCart, dateTime, new BillingTotalAmountViewModel.IBookingInsert() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {
//                    List<String> items = new ArrayList<>();
//                    items.add(name);
//                    items.add(companyName);
//                    items.add(phoneNo);
//                    items.add(email);

                    view.deleteItemsFromCart();
                    String jsonObjectStr = new Gson().toJson(addToCart);
                    String addressData = "Address:" + "{" +
                            "phone:" + phoneNo + "," +
                            "email:" + email

                            + "}";
                    String fullData = "{prolist:" + jsonObjectStr;
                    Log.d("test_Data", "data=" + fullData + "," + addressData);
                    int i = 0;
                    return;
                } else {
                    view.showToastShortTime("Error while saving data");
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });

    }


    @Override
    public void addItemsToBooking(List<AddToCart> addToCarts, String firstName, String company, String phone,
                                  String email, String streetAddress1,
                                  String townCity, String paymode,
                                  String notes, String flatCharges, String selfPickup) {
        model.addItemsToBooking(addToCarts, firstName, company, phone, email, streetAddress1, townCity, paymode, notes, flatCharges, selfPickup, new BillingTotalAmountViewModel.IFetchCartBookingDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Booking> AddToCartList) {
                if (AddToCartList == null || AddToCartList.size() == 0) {
                    view.showToastShortTime("No item found in cart");
                    return;
                } else {
                    view.bookingObject(AddToCartList);

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });
    }

    private void setAdapter(List<AddToCart> AddToCartList) {
        if (adapter == null) {
            adapter = new BillingTotalAmountViewAdapter(AddToCartList);
            view.showRecylerViewProductsDetail(adapter);
        } else {
            adapter.removeAll();
            adapter.addAll(AddToCartList);
        }

        getSubTotal(AddToCartList);
    }


    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());

        }
        view.setSubTotal(String.valueOf(price));
        //  view.setCartCounts(addToCarts.size());
    }
}