package com.xekera.Ecommerce.ui.billing_total_amount_view;

import com.xekera.Ecommerce.data.rest.XekeraAPI;
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

import java.util.List;

public class BillingTotalAmountViewModel implements BillingTotalAmountViewMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public BillingTotalAmountViewModel(AppDatabase appDatabase, Utils utils) {
        this.xekeraAPI = xekeraAPI;
        this.appDatabase = appDatabase;
        this.utils = utils;

    }

    @Override
    public void getCartDetailsList(final IFetchCartDetailsList iFetchCartDetailsList) {
        try {
            Observable.just(appDatabase.getAddToCartDao()).
                    map(new Function<AddToCartDao, List<AddToCart>>() {
                        @Override
                        public List<AddToCart> apply(AddToCartDao addToCartDao) throws Exception {
                            return addToCartDao.getAllCartDetails();
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
    public void removeSelectedCartDetails(final List<String> items, final IRemoveSelectedItemDetails iRemoveSelectedItemDetails) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            for (String itemName : items) {

                                appDatabase.getAddToCartDao().deleteItem(itemName);

                            }

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

    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }

    public interface IRemoveSelectedItemDetails {
        void onSuccess();

        void onError(Exception ex);
    }

}
