package com.xekera.Ecommerce.ui.billing_total_amount_view;

import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

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
                    setAdapter(addToCarts);
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
    public void insertBooking(List<Booking> addToCart,String dateTime) {
        model.insertBooking(addToCart,dateTime, new BillingTotalAmountViewModel.IBookingInsert() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {

                    view.deleteItemsFromCart();
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
    public void addItemsToBooking(List<AddToCart> addToCarts, String firstName, String lastName, String company, String phone,
                                  String email, String streetAddress1, String streetAddress2,
                                  String country, String stateCountry, String townCity, String paymode,
                                  String notes, String flatCharges, String postalCode) {
        model.addItemsToBooking(addToCarts, firstName, lastName, company, phone, email, streetAddress1, streetAddress2, country,
                stateCountry, townCity, paymode, notes, flatCharges, postalCode, new BillingTotalAmountViewModel.IFetchCartBookingDetailsList() {
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
            adapter = new BillingTotalAmountViewAdapter(AddToCartList, this);
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