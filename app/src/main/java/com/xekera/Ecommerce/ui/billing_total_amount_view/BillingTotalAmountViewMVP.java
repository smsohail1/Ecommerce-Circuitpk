package com.xekera.Ecommerce.ui.billing_total_amount_view;

import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.INetworkPostOrder;
import com.xekera.Ecommerce.data.rest.response.SubmitAddressResponse;
import com.xekera.Ecommerce.data.rest.response.SubmitOrderSingleListResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

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

        void setAdapter(List<AddToCart> addToCarts);
    }

    interface Presenter {
        void setView(BillingTotalAmountViewMVP.View view);

        void fetchCartDetails();

        void deleteCartItems(List<String> items);

        void insertBooking(List<Booking> addToCart, String dateTime, String name,
                           String companyName, String phoneNo, String email, String address, String paymentMode, String orderNotes,
                           String selfPikup, String flatCharges, String usernameLogin);

        void addItemsToBooking(List<AddToCart> addToCarts, String firstName, String company, String phone,
                               String email, String streetAddress1,
                               String townCity, String paymode,
                               String notes, String flatCharges, String selfPickup);

    }

    interface Model {

        void getCartDetailsList(BillingTotalAmountViewModel.IFetchCartDetailsList iFetchCartDetailsList);


        void getCartDetailsListItems(BillingTotalAmountViewModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeSelectedCartDetails(List<String> items, BillingTotalAmountViewModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void insertBooking(List<Booking> addToCart, String dateTime, BillingTotalAmountViewModel.IBookingInsert iBookingInsert);

        void updateBooking(String orderId, BillingTotalAmountViewModel.IBookingInsert iBookingInsert);

        void addItemsToBooking(List<AddToCart> addToCarts,
                               String firstName, String company, String phone,
                               String email, String streetAddress1,
                               String townCity, String paymode,
                               String notes, String flatCharges, String selfPickup,
                               BillingTotalAmountViewModel.IFetchCartBookingDetailsList iFetchCartDetailsList);

        void postOrderDetails(String name,
                              String address,
                              String email,
                              String company,
                              String phone,
                              String payment,
                              String message, INetworkLoginSignup<SubmitAddressResponse> iNetworkLoginSignup);

        void setOrderDetailsDescription(String product_id,
                                        String itemQuantity, String itemPrice,
                                        String last_id, int countsID, INetworkPostOrder<SubmitOrderSingleListResponse> iNetworkLoginSignup);

    }
}
