package com.xekera.Ecommerce.ui.shop_card_selected;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsModel;
import okhttp3.ResponseBody;

import java.util.List;

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

        void showRecylerViewProductsImages(ProductsImagesAdapter productsImagesAdapter);

        void setSelectedImage(String clickedUrl);

        void setCountZero(int counts);

        void shakeAddToCartTextview();

        void enableAddtoFavouriteButton();

        void animateFavouriteButton();

        void animationAddButton();

        void setIsFavourite(boolean isFavourite);

        void setCartCounterTextview(int counts);

        void setFavCount();

        void countsForActionBar();


    }

    interface Presenter {
        void setView(ShopCardSelectedMVP.View view);


        //        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude);

        void saveProductDetails(AddToCart addToCart);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice);

        void setMultipleImagesItems(Context context, List<String> images);

        void onIncrementButtonClicked(long quantity, long price, long totalPrice, String productName,
                                      String cutPrice, byte[] byteImage, ImageView imgProductCopy);

        //  void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);

        void addItemToFavourites(Favourites favourites, boolean isChecked);

        void setIsFavourite(String productName);


        void addToCartApi(String productId, String quantity, String price, String discountPrice,
                          String randomKey);
    }

    interface Model {

        void saveProductDetails(AddToCart addToCart, ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void updateItemCountInDBWithDate(String quantity, String itemPrice, String productName, String cutPrice, String createdDate,
                                         ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);

        void getProductCount(String productName, ShopCardSelectedModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getCartDetails(ShopCardSelectedModel.IFetchCartDetailsList iFetchCartDetailsList);


        void addItemToFavourites(Favourites favourites, ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);
//        void saveProductDetails(String productName, String price, String quantity, String deliveryAddress1, String deliveryAddress2
//                , String latitude, String longitude,ShopCardSelectedModel.ISaveProductDetails iSaveProductDetails);


        void getFavouritesCount(ShopCardSelectedModel.IFetchFavouritesDetailsList iFetchFavouritesDetailsList);

        void getFavouritesCount(String productName, ShopCardSelectedModel.IFetchFavouritesDetails iFetchFavouritesDetails);

        void deleteItem(String itemName, ShopCardSelectedModel.IRemoveFavouriteItemDetails iRemoveFavouriteItemDetails);

        void addToCart(String productId, String quantity, String price, String discountPrice,
                       String randomKey
                , INetworkListGeneral<ResponseBody> iNetworkListGeneral);
    }
}
