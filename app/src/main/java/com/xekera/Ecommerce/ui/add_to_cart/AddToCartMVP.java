package com.xekera.Ecommerce.ui.add_to_cart;

import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;

public interface AddToCartMVP {
    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showRecylerViewProductsDetail(AddToCartAdapter addToCartAdapter);

        void showRecyclerView();

        void hideRecyclerView();

        void setSubTotal(String setSubToal);

        void showMessageRemoveItemFromCart(String message);

        void setParentFields();

        void txtNoCartItemFound();

        void hideNoCartItemFound();


    }

    interface Presenter {
        void setView(View view);

        void fetchCartDetails();

        void removeItemFromCart(AddToCart productItems);

        void updateItemCountInDB(String quantity, String itemPrice, String productName);


    }

    interface Model {

        void getCartDetailsList(AddToCartModel.IFetchCartDetailsList iFetchCartDetailsList);


        void removeSelectedCartDetails(AddToCart productItems, AddToCartModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, AddToCartModel.ISaveProductDetails iSaveProductDetails);

    }
}