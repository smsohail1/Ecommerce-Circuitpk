package com.xekera.Ecommerce.ui.add_to_cart;

import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;

import java.util.List;

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

        void showMessageZeroItemOnCart();

        void navigateToBillingDetailScreen();

        void setCartCounts(long counts);

        void setCartCounterTextview(int counts);

        void setAdapter(List<AddToCart> addToCarts);

        void removeItemFromAdapter(int position);

        void hideLoadingProgressDialog();

    }

    interface Presenter {
        void setView(View view);

        void fetchCartDetails();


        void fetchCartDetailsOnBack(int i);

        void removeItemFromCart(AddToCart productItems, int position);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice);

        void getCartCountList();

        void saveProductDetails(String quantity, long individualPrice, String itemPrice, String productName,
                                String cutPrice, byte[] bytes,String imgUrl,String prodcutID,String isEmailSent);

    }

    interface Model {

        void getCartDetailsList(AddToCartModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getCartDetails(AddToCartModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeSelectedCartDetails(AddToCart productItems, AddToCartModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 AddToCartModel.ISaveProductDetails iSaveProductDetails);

        void getProductCount(String productName, AddToCartModel.IFetchCartDetailsList iFetchCartDetailsList);

        void saveProductDetails(AddToCart addToCart, AddToCartModel.ISaveProductDetails iSaveProductDetails);

    }
}
