package com.xekera.Ecommerce.ui.shop_card_selected;


import com.xekera.Ecommerce.data.room.model.AddToCart;

public interface ShopCardSelectedMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void setUpdatedQuantity();

    }

    interface Presenter {
        void setView(ShopCardSelectedMVP.View view);


        //        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude);

        void saveProductDetails(AddToCart addToCart);

        void updateItemCountInDB(String quantity, String itemPrice, String productName);

    }

    interface Model {

        void saveProductDetails(AddToCart addToCart, ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);

        void getProductCount(String productName, ShopCardSelectedModel.IFetchCartDetailsList iFetchCartDetailsList);

//        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude,ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


    }
}
