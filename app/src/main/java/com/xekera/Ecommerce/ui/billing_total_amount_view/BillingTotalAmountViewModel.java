package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.util.Log;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.XekeraAPI;
import com.xekera.Ecommerce.data.rest.response.LoginSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SignUpSuccessResponse;
import com.xekera.Ecommerce.data.rest.response.SubmitOrderResponse;
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
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class BillingTotalAmountViewModel implements BillingTotalAmountViewMVP.Model {
    private XekeraAPI xekeraAPI;
    private AppDatabase appDatabase;
    private Utils utils;

    public BillingTotalAmountViewModel(AppDatabase appDatabase, Utils utils, XekeraAPI xekeraAPI) {
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
                            return addToCartDao.getAllCartOrderDetails();
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

    @Override
    public void insertBooking(final List<Booking> addToCart, final String dateTime, final IBookingInsert iBookingInsert) {
        try {
            Observable.just(appDatabase)
                    .map(new Function<AppDatabase, Boolean>() {
                        @Override
                        public Boolean apply(AppDatabase appDatabase) throws Exception {
                            for (Booking b : addToCart) {
                                b.setCreatedDate(dateTime);
                            }
                            appDatabase.getBookingDao().insert(addToCart);

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
                        public void onNext(Boolean added) {
                            iBookingInsert.onSuccess(added);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iBookingInsert.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception ex) {
            iBookingInsert.onErrorReceived(ex);
        }
    }

    @Override
    public void addItemsToBooking(final List<AddToCart> addToCarts, final String firstName, final String company, final String phone,
                                  final String email, final String streetAddress1,
                                  final String townCity, final String paymode,
                                  final String notes, final String flatCharges, final String selfPickup, final IFetchCartBookingDetailsList iFetchCartBookingDetailsList) {

        try {
            Observable.just(appDatabase.getBookingDao()).
                    map(new Function<BookingDao, List<Booking>>() {
                        @Override
                        public List<Booking> apply(BookingDao bookingDao) throws Exception {
                            // byte[] itemImage = new byte[0];
                            List<Booking> bookingList = new ArrayList<>();
                            for (AddToCart addToCart : addToCarts) {
                                Booking booking = new Booking("", addToCart.getItemName(),
                                        addToCart.getItemIndividualPrice(), addToCart.getItemPrice(),
                                        addToCart.getItemCutPrice(), addToCart.getItemQuantity(),
                                        firstName, company, phone, email, streetAddress1, townCity, paymode, notes, flatCharges, "", addToCart.getItemImage(),
                                        "N", selfPickup, addToCart.getImage(), addToCart.getProduct_id());
                                bookingList.add(booking);
                            }
                            return bookingList;
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
                            iFetchCartBookingDetailsList.onCartDetailsReceived(bookings);
                        }

                        @Override
                        public void onError(Throwable e) {
                            iFetchCartBookingDetailsList.onErrorReceived((Exception) e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            iFetchCartBookingDetailsList.onErrorReceived(e);
        }

    }

    @Override
    public void postOrderDetails(String fullData, final INetworkLoginSignup<ResponseBody> postOrder) {
        Call<ResponseBody> call = xekeraAPI.postOrderDeatils(fullData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody submitOrderResponse = response.body();
                    postOrder.onSuccess(submitOrderResponse);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                postOrder.onFailure(t);
            }
        });

    }

    interface IFetchCartDetailsList {
        void onCartDetailsReceived(List<AddToCart> AddToCartList);

        void onErrorReceived(Exception ex);
    }


    interface IFetchCartBookingDetailsList {
        void onCartDetailsReceived(List<Booking> AddToCartList);

        void onErrorReceived(Exception ex);
    }

    public interface IRemoveSelectedItemDetails {
        void onSuccess();

        void onError(Exception ex);
    }


    interface IBookingInsert {
        void onSuccess(boolean success);

        void onErrorReceived(Exception ex);
    }

    interface ISubmitOrder {

        void onSuccess(String data);

        void onErrorReceived(Exception ex);
    }
}
