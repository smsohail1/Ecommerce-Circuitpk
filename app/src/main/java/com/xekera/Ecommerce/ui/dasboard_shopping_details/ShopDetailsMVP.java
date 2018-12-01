package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.content.Context;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;

import java.util.List;

public interface ShopDetailsMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showRecylerViewProductsDetail(ShopDetailsAdapter shopDetailsAdapter);

        void setCountZero(int counts);

        void setDecrementCount(int counts);

        void showSnackBarShortTime(String message);
    }

    interface Presenter {
        void setView(ShopDetailsMVP.View view);

        void setRecylerViewItems(Context context, List<ShoppingDetailModel> items);

        void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);

        void saveProductDecrementDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);

        void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);


        void setActionListener(ShopDetailsPresenter.ProductItemActionListener actionListener);

        void removeItem(ShoppingDetailModel shoppingDetailModel);
    }

    interface Model {

        void getProductCount(String productName, ShopDetailsModel.IFetchCartDetailsList iFetchCartDetailsList);

        void saveProductDetails(AddToCart addToCart, ShopDetailsModel.ISaveProductDetails iSaveProductDetails);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 ShopDetailsModel.ISaveProductDetails iSaveProductDetails);

        void removeSelectedCartDetails(String productName, ShopDetailsModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void getCartDetails(ShopDetailsModel.IFetchCartDetailsList iFetchCartDetailsList);


    }
}
