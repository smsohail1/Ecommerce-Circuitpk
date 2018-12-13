package com.xekera.Ecommerce.ui.continue_shopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.ContinueShoppingAdapter;

import java.util.List;

public interface ContinueShoppingMVP {

    interface View {

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showRecylerViewProductsDetail(ContinueShoppingAdapter ContinueShoppingAdapter);

        void setCountZero(int counts);

        void setDecrementCount(int counts);

        void showSnackBarShortTime(String message);

        void setFavouriteButtonStatus(boolean status, int position);

        void setFavouriteList(List<Favourites> favourites);

        void setUI(List<Favourites> favList);

        void setIsFavourites(boolean isFavourites, int position);
    }

    interface Presenter {
        void setView(ContinueShoppingMVP.View view);

        void setRecylerViewItems(Context context, List<ContinueShoppingObjectModel> items);

        void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[]
                byteImage, ImageView imgProductCopy, Bitmap bitmap);

        void saveProductDecrementDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy);


        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);

        void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice, ImageView imgProductCopy);


        void setActionListener(ContinueShoppingPresenter.ProductItemActionListener actionListener);

        void removeItem(ContinueShoppingObjectModel ContinueShoppingObjectModel);


        void removeItem(String productName, int position);

        void addItemToFavourites(Favourites favourites, boolean isChecked);

        void getFavouritesList();

        void getFavouritesListByProductName(String productName, int position);

    }

    interface Model {
        void getProductCount(String productName, ContinueShoppingModel.IFetchCartDetailsList iFetchCartDetailsList);

        void saveProductDetails(AddToCart addToCart, ContinueShoppingModel.ISaveProductDetails iSaveProductDetails);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 ContinueShoppingModel.ISaveProductDetails iSaveProductDetails);

        void removeSelectedCartDetails(String productName, ContinueShoppingModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void getCartDetails(ContinueShoppingModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeFromFavourite(String productName, ContinueShoppingModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);

        void addItemToFavourites(Favourites favourites, ContinueShoppingModel.ISaveProductDetails iSaveProductDetails);

        void checkItemAlreadyAddedOrNot(String itemName, ContinueShoppingModel.IFetchFavDetailsList iFetchCartDetailsList);

        void checkItemAlreadyAddedOrNot(String itemName, ContinueShoppingModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getFavouriteDetailsList(ContinueShoppingModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void getFavouriteDetailsListByName(String productName, ContinueShoppingModel.IFetchOrderDetailsList iFetchOrderDetailsList);


    }
}
