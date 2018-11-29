package com.xekera.Ecommerce.ui.billing_total_amount_view;

import com.xekera.Ecommerce.data.room.model.AddToCart;
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
    }

    interface Presenter {
        void setView(BillingTotalAmountViewMVP.View view);

        void fetchCartDetails();

        void deleteCartItems(List<String> items);

    }

    interface Model {

        void getCartDetailsList(BillingTotalAmountViewModel.IFetchCartDetailsList iFetchCartDetailsList);


        void removeSelectedCartDetails(List<String> items, BillingTotalAmountViewModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

    }
}
