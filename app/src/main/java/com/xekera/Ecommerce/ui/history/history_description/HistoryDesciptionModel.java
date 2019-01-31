package com.xekera.Ecommerce.ui.history.history_description;

import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.HistoryDetailsResponse;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderIdDiscriptionResponse;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderIdResponse;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.dao.BookingDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
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

public class HistoryDesciptionModel implements HistoryDesciptionMVP.Model {
    private AppDatabase appDatabase;
    private Utils utils;
    private XekeraAPI xekeraAPI;

    public HistoryDesciptionModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
        this.appDatabase = appDatabase;
        this.utils = utils;
        this.xekeraAPI = xekeraAPI;

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

    @Override
    public void fetchOrderHistoryIdDescription(String orderId, final INetworkListGeneral<HistoryOrderIdDiscriptionResponse> iNetworkListGeneral) {
        Call<HistoryOrderIdDiscriptionResponse> call = xekeraAPI.getOrderHistoryIdDetail(orderId);
        call.enqueue(new Callback<HistoryOrderIdDiscriptionResponse>() {
            @Override
            public void onResponse(Call<HistoryOrderIdDiscriptionResponse> call, Response<HistoryOrderIdDiscriptionResponse> response) {
                try {
                    HistoryOrderIdDiscriptionResponse historyDetailsResponse = response.body();

                    iNetworkListGeneral.onSuccess(historyDetailsResponse);
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
            public void onFailure(Call<HistoryOrderIdDiscriptionResponse> call, Throwable t) {
                iNetworkListGeneral.onFailure(t);
            }
        });
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
