package com.xekera.Ecommerce.ui.add_to_cart;

import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.add_remove_cart_response.AddRemoveCartResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.data.rest.response.delete_item_cart_response.DeleteItemCartResponse;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
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

public class AddToCartModel implements AddToCartMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public AddToCartModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }

    @Override
    public void getCartDetailsList(final IFetchCartDetailsList IFetchCartDetailsList) {
        try {
            Observable.just(appDatabase.getAddToCartDao()).
                    map(new Function<AddToCartDao, List<AddToCart>>() {
                        @Override
                        public List<AddToCart> apply(AddToCartDao addToCartDao) throws Exception {
                            return addToCartDao.getAllCartDetailsWithRespectToDateDesc();
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
    public void removeSelectedCartDetails(final AddToCart productItems, final IRemoveSelectedItemDetails iRemoveSelectedItemDetails) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            appDatabase.getAddToCartDao().deleteItem(productItems.getItemName());


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
                            iRemoveSelectedItemDetails.onSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e.getMessage() != null) {
                                iRemoveSelectedItemDetails.onError((Exception) e);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iRemoveSelectedItemDetails.onError(ex);
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
    public void fetchCarts(String randomKey, final INetworkListGeneral<AddToCartResponse> iNetworkListGeneral) {
        Call<AddToCartResponse> call = xekeraAPI.getAllCarts(randomKey);
        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                try {
                    AddToCartResponse productResponse = response.body();

                    iNetworkListGeneral.onSuccess(productResponse);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
    }

    @Override
    public void deleteCartItem(String id, final INetworkListGeneral<DeleteItemCartResponse> iNetworkListGeneral) {
        Call<DeleteItemCartResponse> call = xekeraAPI.deleteSingleItemCart(id);
        call.enqueue(new Callback<DeleteItemCartResponse>() {
            @Override
            public void onResponse(Call<DeleteItemCartResponse> call, Response<DeleteItemCartResponse> response) {
                try {
                    DeleteItemCartResponse productResponse = response.body();

                    iNetworkListGeneral.onSuccess(productResponse);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DeleteItemCartResponse> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
    }

    @Override
    public void addRemoveCartServer(String quantity, String productId, final INetworkListGeneral<AddRemoveCartResponse> iNetworkListGeneral) {
        Call<AddRemoveCartResponse> call = xekeraAPI.addRemoveItemsCart(quantity, productId);
        call.enqueue(new Callback<AddRemoveCartResponse>() {
            @Override
            public void onResponse(Call<AddRemoveCartResponse> call, Response<AddRemoveCartResponse> response) {
                try {
                    AddRemoveCartResponse productResponse = response.body();

                    iNetworkListGeneral.onSuccess(productResponse);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddRemoveCartResponse> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
    }

    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }


    public interface IRemoveSelectedItemDetails {
        void onSuccess();

        void onError(Exception ex);
    }

    interface ISaveProductDetails {
        void onProductDetailsSaved(boolean isAdded);

        void onErrorReceived(Exception ex);
    }
}