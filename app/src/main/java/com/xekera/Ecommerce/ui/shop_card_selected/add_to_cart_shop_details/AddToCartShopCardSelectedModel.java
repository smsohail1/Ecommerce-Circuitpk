package com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details;

import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.dao.FavouritesDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.util.Utils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class AddToCartShopCardSelectedModel implements AddToCartShopCardSelectedMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public AddToCartShopCardSelectedModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }


    @Override
    public void saveProductDetails(final AddToCart addToCart, final ISaveProductDetails iSaveProductDetails) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getAddToCartDao().insert(addToCart);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean isAdded) {
                            iSaveProductDetails.onProductDetailsSaved(isAdded);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iSaveProductDetails.onErrorReceived((Exception) e);
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iSaveProductDetails.onErrorReceived(ex);
        }

    }

    @Override
    public void updateItemCountInDB(final String quantity, final String itemPrice, final String productName, final String cutPrice,
                                    final ISaveProductDetails iSaveProductDetails) {

        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getAddToCartDao().updateItemCount(quantity, itemPrice, productName, cutPrice);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean updated) {
                            iSaveProductDetails.onProductDetailsSaved(updated);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iSaveProductDetails.onErrorReceived((Exception) e);
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iSaveProductDetails.onErrorReceived(ex);
        }

    }

    @Override
    public void updateItemCountInDBWithDate(final String quantity, final String itemPrice, final String productName, final String cutPrice,
                                            final String createdDate,
                                            final ISaveProductDetails iSaveProductDetails) {

        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getAddToCartDao().updateItemCountWithDate(quantity, itemPrice, productName, cutPrice, createdDate);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean updated) {
                            iSaveProductDetails.onProductDetailsSaved(updated);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iSaveProductDetails.onErrorReceived((Exception) e);
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iSaveProductDetails.onErrorReceived(ex);
        }

    }


    @Override
    public void getProductCount(final String productName, final IFetchCartDetailsList iFetchCartDetailsList) {
        try {
            Observable.just(appDatabase.getAddToCartDao()).
                    map(new Function<AddToCartDao, List<AddToCart>>() {
                        @Override
                        public List<AddToCart> apply(AddToCartDao addToCartDao) throws Exception {
                            return addToCartDao.getCartDetailsByProductName(productName);
                        }
                    }).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<List<AddToCart>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<AddToCart> AddToCartList) {
                            iFetchCartDetailsList.onCartDetailsReceived(AddToCartList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iFetchCartDetailsList.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            iFetchCartDetailsList.onErrorReceived(e);
        }
    }


    @Override
    public void getCartDetails(final IFetchCartDetailsList IFetchCartDetailsList) {
        try {
            Observable.just(appDatabase.getAddToCartDao()).
                    map(new Function<AddToCartDao, List<AddToCart>>() {
                        @Override
                        public List<AddToCart> apply(AddToCartDao addToCartDao) throws Exception {
                            return addToCartDao.getAllCartCount();
                        }
                    }).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<List<AddToCart>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<AddToCart> AddToCartList) {
                            IFetchCartDetailsList.onCartDetailsReceived(AddToCartList);
                        }

                        @Override
                        public void onError(Throwable e) {
                            IFetchCartDetailsList.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            IFetchCartDetailsList.onErrorReceived(e);
        }
    }

    @Override
    public void addItemToFavourites(final Favourites favourites, final ISaveProductDetails iSaveProductDetails) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getFavouritesDao().insert(favourites);
                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean isAdded) {
                            iSaveProductDetails.onProductDetailsSaved(isAdded);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iSaveProductDetails.onErrorReceived((Exception) e);
                            }

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iSaveProductDetails.onErrorReceived(ex);
        }

    }

    @Override
    public void getFavouritesCount(final IFetchFavouritesDetailsList iFetchFavouritesDetailsList) {
        try {
            Observable.just(appDatabase.getFavouritesDao()).
                    map(new Function<FavouritesDao, List<Favourites>>() {
                        @Override
                        public List<Favourites> apply(FavouritesDao favouritesDao) throws Exception {
                            return favouritesDao.getAllFavourites();
                        }
                    }).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<List<Favourites>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Favourites> favourites) {
                            iFetchFavouritesDetailsList.onCartDetailsReceived(favourites);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iFetchFavouritesDetailsList.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            iFetchFavouritesDetailsList.onErrorReceived(e);
        }


    }

    @Override
    public void getFavouritesCount(final String productName, final IFetchFavouritesDetails iFetchFavouritesDetails) {
        try {
            Observable.just(appDatabase.getFavouritesDao()).
                    map(new Function<FavouritesDao, List<Favourites>>() {
                        @Override
                        public List<Favourites> apply(FavouritesDao favouritesDao) throws Exception {
                            return favouritesDao.getFavouritesByName(productName);
                        }
                    }).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<List<Favourites>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Favourites> favourites) {
                            iFetchFavouritesDetails.onCartDetailsReceived(favourites);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iFetchFavouritesDetails.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            iFetchFavouritesDetails.onErrorReceived(e);
        }


    }

    @Override
    public void deleteItem(final String itemName, final IRemoveFavouriteItemDetails iRemoveFavouriteItemDetails) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getFavouritesDao().deleteItem(itemName);


                            return true;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean success) {
                            iRemoveFavouriteItemDetails.onSuccess(success);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iRemoveFavouriteItemDetails.onError((Exception) e);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iRemoveFavouriteItemDetails.onError(ex);
        }
    }

    @Override
    public void addToCart(String productId, String quantity, String price, String discountPrice, String randomKey,
                          final INetworkListGeneral<ResponseBody> iNetworkListGeneral) {
        Call<ResponseBody> call = xekeraAPI.addToProducts(productId, quantity, price, randomKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    // AddToCartResponse productResponse = response.body();

                    iNetworkListGeneral.onSuccess(response.body());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
    }

    interface ISaveProductDetails {
        void onProductDetailsSaved(boolean isAdded);

        void onErrorReceived(Exception ex);
    }


    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }

    interface IFetchFavouritesDetailsList {
        void onCartDetailsReceived(List<Favourites> AddToCartList);

        void onErrorReceived(Exception ex);
    }

    interface IFetchFavouritesDetails {
        void onCartDetailsReceived(List<Favourites> favourites);

        void onErrorReceived(Exception ex);
    }


    public interface IRemoveFavouriteItemDetails {
        void onSuccess(boolean success);

        void onError(Exception ex);
    }

}