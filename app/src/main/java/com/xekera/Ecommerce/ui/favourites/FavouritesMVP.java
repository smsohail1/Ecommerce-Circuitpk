package com.xekera.Ecommerce.ui.favourites;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.history.HistoryMVP;
import com.xekera.Ecommerce.ui.history.HistoryModel;

import java.util.List;

public interface FavouritesMVP {
    interface View {

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showSnackBarShortTime(String message, android.view.View view);

        void showSnackBarLongTime(String message, android.view.View view);

        void showSnackBarShortTime(String message);

        void showRecylerViewProductsDetail(FavoritesAdapter favoritesAdapter);

        void showRecyclerView();

        void hideRecyclerView();

        void setCartCounts(long counts);

        void setCartCounterTextview(int counts);


        void txtNoCartItemFound();

        void hideNoCartItemFound();


        void setAdapter(List<Favourites> addToCarts);

        void removeItemFromFavourites(int position);

        void setCountZero(int counts);

        void setDecrementCount(int counts);

        void hideLoadingProgressDialog();

    }

    interface Presenter {
        void setView(FavouritesMVP.View view);

        void fetchFavouritesDetails();

        void insertSelectedFavouritesToCart(AddToCart addToCart, int position, ImageView imageView);


        void setActionListener(FavouritesPresenter.ProductItemActionListener actionListener);

        void setAddRemoveActionListener(FavouritesPresenter.ProductAddRemoveActionListener actionListener);


        void removeFromFavourites(Favourites favourites, int position);

        void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[]
                byteImage, ImageView imgProductCopy, Bitmap bitmap);

        void saveProductDecrementDetails(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                         byte[] byteImage, ImageView imgProductCopy);

        void removeItem(Favourites favourites);


    }

    interface Model {
        void getFavouriteDetailsList(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void insertSelectedFavouritesToCart(AddToCart addToCart, FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void checkItemAlreadyAddedOrNot(String itemName, FavouritesModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeSelectedCartDetails(String itemName, FavouritesModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);


        void getTotalCounts(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);


        void getTotalCountsByName(String name, FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);


        void getCartDetails(FavouritesModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getCartDetails(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);


        void getProductCount(String productName, FavouritesModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getProductCount(String productName, FavouritesModel.IFetchOrderDetailsList iFetchCartDetailsList);

        void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                 FavouritesModel.ISaveProductDetails iSaveProductDetails);


        void updateFavouriteItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                          FavouritesModel.ISaveProductDetails iSaveProductDetails);


        void updateItemAddToCart(String quantity, String itemPrice, String productName, String cutPrice,
                                          FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void saveProductDetails(AddToCart addToCart, FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void savefavouritesDetails(Favourites favourites, FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void removeItemFromCart(String productName, FavouritesModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);


        void checkItemAlreadyAddedOrNot(String itemName, FavouritesModel.IFetchOrderDetailsList iFetchCartDetailsList);
    }
}
