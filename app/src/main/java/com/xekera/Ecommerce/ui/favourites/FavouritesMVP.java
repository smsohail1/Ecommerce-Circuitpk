package com.xekera.Ecommerce.ui.favourites;

import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
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


    }

    interface Presenter {
        void setView(FavouritesMVP.View view);

        void fetchFavouritesDetails();

        void insertSelectedFavouritesToCart(AddToCart addToCart, int position);

    }

    interface Model {
        void getFavouriteDetailsList(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void insertSelectedFavouritesToCart(AddToCart addToCart, FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void checkItemAlreadyAddedOrNot(String itemName, FavouritesModel.IFetchCartDetailsList iFetchCartDetailsList);

        void removeSelectedCartDetails(String itemName, FavouritesModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);


        void getTotalCounts(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);
    }
}
