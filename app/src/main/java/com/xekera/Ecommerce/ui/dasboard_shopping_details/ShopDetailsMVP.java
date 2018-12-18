package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;

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

        void setFavouriteButtonStatus(boolean status, int position);

        void setFavouriteList(List<Favourites> favourites);

        void setUI(List<Favourites> favList);

        void setIsFavourites(boolean isFavourites, int position);
    }

    interface Presenter {
        void setView(ShopDetailsMVP.View view);

        void setRecylerViewItems(Context context, List<ShoppingDetailModel> items);

        void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[]
                byteImage, ImageView imgProductCopy, Bitmap bitmap);

        void saveProductDecrementDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);

        void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);


        void setActionListener(ShopDetailsPresenter.ProductItemActionListener actionListener);

        void removeItem(ShoppingDetailModel shoppingDetailModel);


        void removeItem(String productName, int position);

        void addItemToFavourites(Favourites favourites, boolean isChecked);

        void getFavouritesList();

        void getFavouritesListByProductName(String productName, int position);

        void isAlreadyAddedInFavourites(ShoppingDetailModel productItems, int position, Bitmap bitmap);
    }

    interface Model {

        void getProductCount(String productName, ShopDetailsModel.IFetchCartDetailsList iFetchCartDetailsList);

        void saveProductDetails(AddToCart addToCart, ShopDetailsModel.ISaveProductDetails iSaveProductDetails);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 ShopDetailsModel.ISaveProductDetails iSaveProductDetails);

        void removeSelectedCartDetails(String productName, ShopDetailsModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void getCartDetails(ShopDetailsModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeFromFavourite(String productName, ShopDetailsModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void addItemToFavourites(Favourites favourites, ShopDetailsModel.ISaveProductDetails iSaveProductDetails);

        void checkItemAlreadyAddedOrNot(String itemName, ShopDetailsModel.IFetchFavDetailsList iFetchCartDetailsList);

        void getFavouriteDetailsList(ShopDetailsModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void getFavouriteDetailsListByName(String productName, ShopDetailsModel.IFetchOrderDetailsList iFetchOrderDetailsList);


        void checkItemAlreadyAddedOrNot(String itemName, ShopDetailsModel.IFetchCartDetailsList iFetchCartDetailsList);

    }
}
