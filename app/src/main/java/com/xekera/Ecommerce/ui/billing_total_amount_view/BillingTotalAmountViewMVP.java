package com.xekera.Ecommerce.ui.billing_total_amount_view;

import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;

import java.util.List;

public interface BillingTotalAmountViewMVP {
    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showRecylerViewProductsDetail(BillingTotalAmountViewAdapter billingTotalAmountViewAdapter);

        void showRecyclerView();

        void hideRecyclerView();

        void setSubTotal(String setSubToal);

        void showMessageRemoveItemFromCart(String message);

        void setParentFields();


        void showMessageZeroItemOnCart();


        void setCartCounts(long counts);

        void cartLists(List<AddToCart> addToCarts);

        void itemRemovedFromCart();

        void bookingObject(List<Booking> AddToCartList);

        void deleteItemsFromCart();
    }

    interface Presenter {
        void setView(BillingTotalAmountViewMVP.View view);

        void fetchCartDetails();

        void deleteCartItems(List<String> items);

        void insertBooking(List<Booking> addToCart, String dateTime);

        void addItemsToBooking(List<AddToCart> addToCarts, String firstName, String lastName, String company, String phone,
                               String email, String streetAddress1, String streetAddress2,
                               String country, String stateCountry, String townCity, String paymode,
                               String notes, String flatCharges, String postalCode);

    }

    interface Model {

        void getCartDetailsList(BillingTotalAmountViewModel.IFetchCartDetailsList iFetchCartDetailsList);


        void removeSelectedCartDetails(List<String> items, BillingTotalAmountViewModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void insertBooking(List<Booking> addToCart, String dateTime, BillingTotalAmountViewModel.IBookingInsert iBookingInsert);

        void addItemsToBooking(List<AddToCart> addToCarts,
                               String firstName, String lastName, String company, String phone,
                               String email, String streetAddress1, String streetAddress2,
                               String country, String stateCountry, String townCity, String paymode,
                               String notes, String flatCharges, String postalCode,
                               BillingTotalAmountViewModel.IFetchCartBookingDetailsList iFetchCartDetailsList);

    }
}
