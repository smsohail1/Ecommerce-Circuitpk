package com.xekera.Ecommerce.ui.history;

import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.dao.BookingDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.util.Utils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class HistoryModel implements HistoryMVP.Model {
    private AppDatabase appDatabase;
    private Utils utils;

    public HistoryModel(AppDatabase appDatabase, Utils utils) {
        this.appDatabase = appDatabase;
        this.utils = utils;

    }

    @Override
    public void getOrderDetailsList(final IFetchOrderDetailsList iFetchOrderDetailsList) {
        try {
            Observable.just(appDatabase.getBookingDao()).
                    map(new Function<BookingDao, List<Booking>>() {
                        @Override
                        public List<Booking> apply(BookingDao bookingDao) throws Exception {
                            return bookingDao.getAllBookingDetails();
                        }
                    }).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Observer<List<Booking>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Booking> bookings) {
                            iFetchOrderDetailsList.onCartDetailsReceived(bookings);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iFetchOrderDetailsList.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            iFetchOrderDetailsList.onErrorReceived(e);
        }
    }


    @Override
    public void getCartDetailsList(final IFetchCartDetailsList IFetchCartDetailsList) {
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


    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }

    interface IFetchOrderDetailsList {
        void onCartDetailsReceived(List<Booking> bookings);

        void onErrorReceived(Exception ex);
    }
}
