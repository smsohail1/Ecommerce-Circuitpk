package com.xekera.Ecommerce.ui.favourites;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.google.gson.JsonObject;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.fetch_favourite_response.FetchFavouriteResponse;
import com.xekera.Ecommerce.data.rest.response.fetch_favourite_response.Product;
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse;
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
import okhttp3.ResponseBody;

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

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void setAdapter(List<Product> addToCarts);

        void removeItemFromFavourites(int position);

        void setCountZero(int counts);

        void setDecrementCount(int counts);

        void hideLoadingProgressDialog();

        void itemsCountsBottomView(int index, long counts);

        int getCartCount();

    }

    interface Presenter {
        void setView(FavouritesMVP.View view);

        void fetchFavouritesDetails();


        void insertSelectedFavouritesToCart(AddToCart addToCart, int position, ImageView imageView);


        void setActionListener(FavouritesPresenter.ProductItemActionListener actionListener);

        void setAddRemoveActionListener(FavouritesPresenter.ProductAddRemoveActionListener actionListener);


        void removeFromFavourites(String id, int position, String username, String email);

        void saveProductDetails(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[]
                byteImage, ImageView imgProductCopy, Bitmap bitmap, String imgUrl, String productID, String isEmailFav,
                                String productDesc, String imgArrList, String nameSku);

        void saveProductDecrementDetails(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                         byte[] byteImage, ImageView imgProductCopy, String imgUrl, String productID,
                                         String isEmailFav, String productDesc, String imgArrList, String nameSku);

        void removeItem(Favourites favourites);


        void addToCartApi(String productId, String quantity, String price, String discountPrice,
                          String randomKey, ImageView imgProductCopy, int position, String username, String email,
                          String productIdIncrement);

        void getAllProducts(String productIdIncrement, String quantity, String price, String discountPrice,
                            String randomKey, ImageView imgProductCopy, int position, String username, String email, String nameSku);

        void fetchFavouritesServer(String username, String email);
    }

    interface Model {
        void getFavouriteDetailsList(FavouritesModel.IFetchOrderDetailsList iFetchOrderDetailsList);

        void insertSelectedFavouritesToCart(AddToCart addToCart, FavouritesModel.ISaveProductDetails iSaveProductDetails);

        void checkItemAlreadyAddedOrNot(String itemName, FavouritesModel.IFetchCartDetailsList iFetchCartDetailsList);

        //void removeSelectedCartDetails(String name, FavouritesModel.IRemoveSelectedItemDetails iRemoveSelectedItemDetails);


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

        void addToCart(String productId, String quantity, String price, String discountPrice,
                       String randomKey
                , INetworkListGeneral<ResponseBody> iNetworkListGeneral);


        void fetchAllProducts(String productId, String quantity, String price, String discountPrice,
                              String randomKey
                , INetworkListGeneral<AllProductsResponse> iNetworkListGeneral);


        void fetchFavouritesServer(String username, String email, INetworkListGeneral<FetchFavouriteResponse> iNetworkListGeneral);

        void removeSelectedCartDetails(JsonObject jsonObject, INetworkListGeneral<ResponseBody> iNetworkListGeneral);
    }
}
