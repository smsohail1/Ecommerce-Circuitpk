package com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details;


import android.content.Context;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;

import java.util.List;

public interface AddToCartShopCardSelectedMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void setUpdatedQuantity();

        void showRecylerViewProductsImages(ProductsImagesAdapter productsImagesAdapter);

        void setSelectedImage(String clickedUrl);

        void setCountZero(int counts);

        void shakeAddToCartTextview();

        void enableAddtoFavouriteButton();

        void animateFavouriteButton();

        void animationAddButton();

        void setIsFavourite(boolean isFavourite);

        void setCartCounterTextview(int counts);


    }

    interface Presenter {
        void setView(AddToCartShopCardSelectedMVP.View view);


        //        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude);

        void saveProductDetails(AddToCart addToCart);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice);

        void setMultipleImagesItems(Context context, List<String> images);

        void onIncrementButtonClicked(long quantity, long price, long totalPrice, String productName,
                                      String cutPrice, byte[] byteImage, ImageView imgProductCopy);

        //  void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 ImageView imgProductCopy);

        void addItemToFavourites(Favourites favourites, boolean isChecked);

        void setIsFavourite(String productName);
    }

    interface Model {

        void saveProductDetails(AddToCart addToCart, AddToCartShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 AddToCartShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void updateItemCountInDBWithDate(String quantity, String itemPrice, String productName, String cutPrice, String createdDate,
                                         AddToCartShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);

        void getProductCount(String productName, AddToCartShopCardSelectedModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getCartDetails(AddToCartShopCardSelectedModel.IFetchCartDetailsList iFetchCartDetailsList);


        void addItemToFavourites(Favourites favourites, AddToCartShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);
//        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude,AddToCartShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void getFavouritesCount(AddToCartShopCardSelectedModel.IFetchFavouritesDetailsList iFetchFavouritesDetailsList);

        void getFavouritesCount(String productName, AddToCartShopCardSelectedModel.IFetchFavouritesDetails iFetchFavouritesDetails);

        void deleteItem(String itemName, AddToCartShopCardSelectedModel.IRemoveFavouriteItemDetails iRemoveFavouriteItemDetails);

    }
}
