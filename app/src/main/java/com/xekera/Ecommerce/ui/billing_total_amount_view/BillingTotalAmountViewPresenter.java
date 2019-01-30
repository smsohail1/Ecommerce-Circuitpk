package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.response.SubmitAddressResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import okhttp3.ResponseBody;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BillingTotalAmountViewPresenter implements BillingTotalAmountViewMVP.Presenter {
    private BillingTotalAmountViewMVP.View view;
    private BillingTotalAmountViewMVP.Model model;
    private BillingTotalAmountViewAdapter adapter;

    private SessionManager sessionManager;
    private Utils utils;

    public BillingTotalAmountViewPresenter(BillingTotalAmountViewMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(BillingTotalAmountViewMVP.View view) {
        this.view = view;
    }

    @Override
    public void fetchCartDetails() {
        model.getCartDetailsList(new BillingTotalAmountViewModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.setCartCounts(0);
                    return;
                } else {
                    view.showRecyclerView();
                    view.cartLists(addToCarts);
                    view.setAdapter(addToCarts);
                    //setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.setCartCounts(0);

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void deleteCartItems(List<String> items) {

        model.removeSelectedCartDetails(items, new BillingTotalAmountViewModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.hideProgressDialogPleaseWait();

                view.itemRemovedFromCart();
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });

    }

    @Override
    public void insertBooking(final List<Booking> addToCart, String dateTime, final String name,
                              final String companyName, final String phoneNo, final String email, final String address,
                              final String paymentMode, final String orderNotes,
                              final String selfPikup, final String flatCharges, final String usernameLogin) {
        model.insertBooking(addToCart, dateTime, new BillingTotalAmountViewModel.IBookingInsert() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {

                    // view.deleteItemsFromCart();
                    //  String jsonObjectStr = new Gson().toJson(addToCart);
                    //  prolist prolist = new prolist();

                    //List<Object> prolist = new ArrayList<>();
                    //  HashMap<String, Object> map = new HashMap<String, Object>();

//                    String addressData = "{" +
//                            "\"Phone\":" + "\"" + phoneNo + "\"" + "," +
//                            "\"Email\":" + "\"" + email + "\"" + "," +
//                            "\"Address\":" + "\"" + address + "\"" + "," +
//                            "\"Payment\":" + "\"" + paymentMode + "\"" + "," +
//                            "\"Message\":" + "\"" + orderNotes + "\"" + "," +
//                            "\"selfPikup\":" + "\"" + selfPikup + "\"" + "," +
//                            "\"flatCharges\":" + "\"" + flatCharges + "\"" + "," +
//                            "\"Company\":" + "\"" + companyName + "\"" + "," +
//                            "\"name\":" + "\"" + name + "\""
//                            + "}";

//                    JSONObject jsonObjectAddress = new JSONObject();
//                    try {
//                        jsonObjectAddress.put("Phone", phoneNo);
//                        jsonObjectAddress.put("Email", email);
//                        jsonObjectAddress.put("Address", address);
//                        jsonObjectAddress.put("Payment", paymentMode);
//                        jsonObjectAddress.put("Message", orderNotes);
//                        jsonObjectAddress.put("selfPikup", selfPikup);
//                        jsonObjectAddress.put("flatCharges", flatCharges);
//                        jsonObjectAddress.put("Company", companyName);
//                        jsonObjectAddress.put("name", name);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }


                    String addressData = "\"nameValuePairs\":" + "{" +
                            "\"Phone\":" + "\"" + phoneNo + "\"" + "," +
                            "\"Email\":" + "\"" + email + "\"" + "," +
                            "\"Address\":" + "\"" + address + "\"" + "," +
                            "\"Payment\":" + "\"" + paymentMode + "\"" + "," +
                            "\"Message\":" + "\"" + orderNotes + "\"" + "," +
                            "\"selfPikup\":" + "\"" + selfPikup + "\"" + "," +
                            "\"flatCharges\":" + "\"" + flatCharges + "\"" + "," +
                            "\"Company\":" + "\"" + companyName + "\"" + "," +
                            "\"name\":" + "\"" + usernameLogin + "\"" +
                            "}";


                    //  String jsonObjectAddress1 = new Gson().toJson(jsonObjectAddress);

                    // prolist.add(jsonObjectStr);
                    // prolist.add(addressData);

                    //  String jsonObject = new Gson().toJson(prolist);
                    // map.put("\"prolist\"", jsonObjectStr);
                    // map.put("\"Address\"", jsonObjectAddress1);
                    //    String jsonObjectdata = new Gson().toJson(map);

                    // jsonObjectdata = "jsondata=" + jsonObjectdata;

                    // String fullData =  "{\"prolist\":" + jsonObjectStr
                    //           + "," + addressData + "}";
//
//                    String fullData = "{\"prolist\":" + jsonObjectStr
//                            + "," + addressData + "}";
                    String fullData = "{" + addressData + "}";

                    // String dd = jsonObjectStr.replace("\"", "");

                    //  String fullData = jsonObjectAddress1;

                    //String obj = new Gson().toJson(fullData);
                    //  Log.d("jkjk", obj);
//                    JSONObject jsnobject = null;
//                    try {
//                        jsnobject = new JSONObject(fullData);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//
//                    }


//                    JSONObject postData = new JSONObject();
//                    try {
//                        postData.put("nameValuePairs", addressData);
//                        postData.put("prolist", jsonObjectStr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    new SendDeviceDetails().execute(BASE_URL_LIVE + "Submit_Order/submitorder.php", postData.toString());

                    // Log.d("test_Data", "jsondata=" + jsnobject);

                    model.postOrderDetails(name, address, email, companyName, phoneNo, paymentMode, orderNotes, new
                            INetworkLoginSignup<SubmitAddressResponse>() {
                                @Override
                                public void onSuccess(SubmitAddressResponse response) {
                                    view.hideProgressDialogPleaseWait();
                                    //Log.d("messh1", response.getMessage() + "," + response.getStatus());
                                    // if (response == null) {
                                    //   view.showToastShortTime("Error while booking order.");
                                    //  return;
                                    //}
                                    //     if (response.getStatus()) {
                                    //view.showToastShortTime(response.getMessage());
                                    int i = 0;
                                    if (!response.getStatus()) {
                                        view.showToastShortTime("Order booking failed.");
                                        return;
                                    } else if (utils.isTextNullOrEmpty(response.getOrderID())) {
                                        view.showToastShortTime("Order booking failed.");
                                        return;
                                    }

                                    // view.deleteItemsFromCart();
                                    model.updateBooking(response.getOrderID(), new BillingTotalAmountViewModel.IBookingInsert() {
                                        @Override
                                        public void onSuccess(boolean success) {
                                            if (success) {
                                                model.getCartDetailsListItems(new BillingTotalAmountViewModel.IFetchCartDetailsList() {
                                                    @Override
                                                    public void onCartDetailsReceived(List<AddToCart> AddToCartList) {
                                                        String jsonObjectStr = new Gson().toJson(addToCart);
                                                        int i = 0;
                                                        model.setOrderDetailsDescription(jsonObjectStr, new INetworkLoginSignup<ResponseBody>() {
                                                            @Override
                                                            public void onSuccess(ResponseBody response) {
                                                                view.deleteItemsFromCart();

                                                            }

                                                            @Override
                                                            public void onFailure(Throwable t) {
                                                                view.hideProgressDialogPleaseWait();
                                                                view.showToastShortTime("Error while booking order.");


                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onErrorReceived(Exception ex) {
                                                        if (ex.getMessage() != null) {
                                                            view.showToastShortTime("Error while booking order.");
                                                        } else {
                                                            view.showToastShortTime("Error while booking order.");
                                                        }
                                                    }
                                                });
                                            } else {
                                                view.hideProgressDialogPleaseWait();
                                                view.showToastShortTime("Error while order booking.");
                                            }

                                        }

                                        @Override
                                        public void onErrorReceived(Exception ex) {
                                            view.hideProgressDialogPleaseWait();
                                            view.showToastShortTime("Error while booking order.");

                                        }
                                    });


                                    //   } else {
                                    // view.showToastShortTime("Error while booking order.");
                                    // }
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    view.hideProgressDialogPleaseWait();
                                    view.showToastShortTime("Can't submit order.Error while submit data.");
                                }
                            });
                } else {
                    view.showToastShortTime("Error while saving data");
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });

    }


    @Override
    public void addItemsToBooking(List<AddToCart> addToCarts, String firstName, String company, String phone,
                                  String email, String streetAddress1,
                                  String townCity, String paymode,
                                  String notes, String flatCharges, String selfPickup) {
        model.addItemsToBooking(addToCarts, firstName, company, phone, email, streetAddress1, townCity, paymode, notes, flatCharges, selfPickup, new BillingTotalAmountViewModel.IFetchCartBookingDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Booking> AddToCartList) {
                if (AddToCartList == null || AddToCartList.size() == 0) {
                    view.showToastShortTime("No item found in cart");
                    return;
                } else {
                    view.bookingObject(AddToCartList);

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });
    }

    private void setAdapter(List<AddToCart> AddToCartList) {
        if (adapter == null) {
            adapter = new BillingTotalAmountViewAdapter(AddToCartList);
            view.showRecylerViewProductsDetail(adapter);
        } else {
            adapter.removeAll();
            adapter.addAll(AddToCartList);
        }

        getSubTotal(AddToCartList);
    }


    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());

        }
        view.setSubTotal(String.valueOf(price));
        //  view.setCartCounts(addToCarts.size());
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("jsondata=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}