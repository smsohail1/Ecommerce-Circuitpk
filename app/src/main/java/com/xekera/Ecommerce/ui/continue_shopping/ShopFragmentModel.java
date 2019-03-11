package com.xekera.Ecommerce.ui.continue_shopping;

//import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
//import com.xekera.Ecommerce.data.rest.XekeraAPI;
//import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
//import com.xekera.Ecommerce.data.room.AppDatabase;
//import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
//import com.xekera.Ecommerce.data.room.model.AddToCart;

import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.util.Utils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ShopFragmentModel implements ShopFragmentMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public ShopFragmentModel(XekeraAPI xekeraAPI, AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

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
    public void getDashboardItemsDetails(final INetworkListGeneral<CategoryResponse> iNetworkListGeneral) {
        Call<CategoryResponse> call = xekeraAPI.getCategory();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                try {
                    CategoryResponse categoryResponse = response.body();

                    iNetworkListGeneral.onSuccess(categoryResponse);
//                    if(messageResponse == null){
//                        iNetworkLoginSignup.onErrorList(getMessageResponse(utils.getStringFromResourceId(R.string.error),
//                                utils.getStringFromResourceId(R.string.null_response_received)));
//                        return;
//                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
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


    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }


}
