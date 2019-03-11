package com.xekera.Ecommerce.ui.billing_total_amount_view;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.stripe.android.model.Card;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.INetworkLoginSignup;
import com.xekera.Ecommerce.data.rest.INetworkPostOrder;
import com.xekera.Ecommerce.data.rest.response.SubmitAddressResponse;
import com.xekera.Ecommerce.data.rest.response.SubmitOrderSingleListResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.Product;
import com.xekera.Ecommerce.data.rest.response.submit_order_json_response.SubmitOrderJsonResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.ui.adapter.BillingTotalAmountViewAdapter;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                    //  view.setAdapter(addToCarts);
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
                // view.hideProgressDialogPleaseWait();

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
    public void insertBooking(final List<AddToCart> addToCart, String dateTime, final String name,
                              final String companyName, final String phoneNo, final String email, final String address,
                              final String paymentMode, final String orderNotes,
                              final String selfPikup, final String flatCharges, final String usernameLogin) {
        model.insertBooking(addToCart, dateTime, new BillingTotalAmountViewModel.IBookingInsert() {
            @Override
            public void onSuccess(boolean success) {
//                if (success) {
//
//                    // view.deleteItemsFromCart();
//                    //  String jsonObjectStr = new Gson().toJson(addToCart);
//                    //  prolist prolist = new prolist();
//
//                    //List<Object> prolist = new ArrayList<>();
//                    //  HashMap<String, Object> map = new HashMap<String, Object>();
//
////                    String addressData = "{" +
////                            "\"Phone\":" + "\"" + phoneNo + "\"" + "," +
////                            "\"Email\":" + "\"" + email + "\"" + "," +
////                            "\"Address\":" + "\"" + address + "\"" + "," +
////                            "\"Payment\":" + "\"" + paymentMode + "\"" + "," +
////                            "\"Message\":" + "\"" + orderNotes + "\"" + "," +
////                            "\"selfPikup\":" + "\"" + selfPikup + "\"" + "," +
////                            "\"flatCharges\":" + "\"" + flatCharges + "\"" + "," +
////                            "\"Company\":" + "\"" + companyName + "\"" + "," +
////                            "\"name\":" + "\"" + name + "\""
////                            + "}";
//
////                    JSONObject jsonObjectAddress = new JSONObject();
////                    try {
////                        jsonObjectAddress.put("Phone", phoneNo);
////                        jsonObjectAddress.put("Email", email);
////                        jsonObjectAddress.put("Address", address);
////                        jsonObjectAddress.put("Payment", paymentMode);
////                        jsonObjectAddress.put("Message", orderNotes);
////                        jsonObjectAddress.put("selfPikup", selfPikup);
////                        jsonObjectAddress.put("flatCharges", flatCharges);
////                        jsonObjectAddress.put("Company", companyName);
////                        jsonObjectAddress.put("name", name);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
//
//
////                    String addressData = "\"nameValuePairs\":" + "{" +
////                            "\"Phone\":" + "\"" + phoneNo + "\"" + "," +
////                            "\"Email\":" + "\"" + email + "\"" + "," +
////                            "\"Address\":" + "\"" + address + "\"" + "," +
////                            "\"Payment\":" + "\"" + paymentMode + "\"" + "," +
////                            "\"Message\":" + "\"" + orderNotes + "\"" + "," +
////                            "\"selfPikup\":" + "\"" + selfPikup + "\"" + "," +
////                            "\"flatCharges\":" + "\"" + flatCharges + "\"" + "," +
////                            "\"Company\":" + "\"" + companyName + "\"" + "," +
////                            "\"name\":" + "\"" + usernameLogin + "\"" +
////                            "}";
//
//
//                    //  String jsonObjectAddress1 = new Gson().toJson(jsonObjectAddress);
//
//                    // prolist.add(jsonObjectStr);
//                    // prolist.add(addressData);
//
//                    //  String jsonObject = new Gson().toJson(prolist);
//                    // map.put("\"prolist\"", jsonObjectStr);
//                    // map.put("\"Address\"", jsonObjectAddress1);
//                    //    String jsonObjectdata = new Gson().toJson(map);
//
//                    // jsonObjectdata = "jsondata=" + jsonObjectdata;
//
//                    // String fullData =  "{\"prolist\":" + jsonObjectStr
//                    //           + "," + addressData + "}";
////
////                    String fullData = "{\"prolist\":" + jsonObjectStr
////                            + "," + addressData + "}";
//                    //  String fullData = "{" + addressData + "}";
//
//                    // String AllProductsResponse = jsonObjectStr.replace("\"", "");
//
//                    //  String fullData = jsonObjectAddress1;
//
//                    //String obj = new Gson().toJson(fullData);
//                    //  Log.d("jkjk", obj);
////                    JSONObject jsnobject = null;
////                    try {
////                        jsnobject = new JSONObject(fullData);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////
////                    }
//
//
////                    JSONObject postData = new JSONObject();
////                    try {
////                        postData.put("nameValuePairs", addressData);
////                        postData.put("prolist", jsonObjectStr);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////
////                    new SendDeviceDetails().execute(BASE_URL_LIVE + "Submit_Order/submitorder.php", postData.toString());
//
//                    // Log.d("test_Data", "jsondata=" + jsnobject);
//
//                    model.postOrderDetails(name, address, email, companyName, phoneNo, paymentMode, orderNotes, "",
//                            "", "", new
//                                    INetworkLoginSignup<SubmitAddressResponse>() {
//                                        @Override
//                                        public void onSuccess(SubmitAddressResponse response) {
//                                            //  view.hideProgressDialogPleaseWait();
//                                            //Log.d("messh1", response.getMessage() + "," + response.getStatus());
//                                            // if (response == null) {
//                                            //   view.showToastShortTime("Error while booking order.");
//                                            //  return;
//                                            //}
//                                            //     if (response.getStatus()) {
//                                            //view.showToastShortTime(response.getMessage());
//                                            int i = 0;
//                                            if (!response.getStatus()) {
//                                                view.hideProgressDialogPleaseWait();
//
//                                                view.showToastShortTime("Order booking failed.");
//                                                return;
//                                            } else if (utils.isTextNullOrEmpty(response.getOrderID())) {
//                                                view.hideProgressDialogPleaseWait();
//                                                view.showToastShortTime("Order booking failed.");
//                                                return;
//                                            }
//
//                                            // view.deleteItemsFromCart();
//                                            String orderIdResponse = "";
//                                            orderIdResponse = response.getOrderID();
//                                            final String finalOrderIdResponse = orderIdResponse;
//                                            model.updateBooking(response.getOrderID(), new BillingTotalAmountViewModel.IBookingInsert() {
//                                                @Override
//                                                public void onSuccess(boolean success) {
//                                                    if (success) {
//                                                        model.getCartDetailsListItems(new BillingTotalAmountViewModel.IFetchCartDetailsList() {
//                                                            @Override
//                                                            public void onCartDetailsReceived(final List<AddToCart> AddToCartList) {
//                                                                // String jsonObjectStr = new Gson().toJson(AddToCartList);
//                                                                // jsonObjectStr = "{\"test\":" + jsonObjectStr + "}";
//                                                                if (AddToCartList.size() > 0) {
//                                                                    int countsID = 0;
//                                                                    countsID = countsID + 1;
//
//                                                                    if (AddToCartList.size() == 1) {
//                                                                        model.setOrderDetailsDescription(AddToCartList.get(0).getProduct_id(),
//                                                                                AddToCartList.get(0).getItemQuantity(),
//                                                                                AddToCartList.get(0).getItemPrice(),
//                                                                                AddToCartList.get(0).getOrderID(),
//                                                                                email, "one",
//                                                                                countsID,
//                                                                                new INetworkPostOrder<SubmitOrderSingleListResponse>() {
//                                                                                    @Override
//                                                                                    public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
//                                                                                        if (counts == AddToCartList.size()) {
//                                                                                            view.deleteItemsFromCart();
//                                                                                            view.hideProgressDialogPleaseWait();
//                                                                                            return;
//                                                                                        } else {
//                                                                                            sendData(AddToCartList, counts, email, paymentMode, finalOrderIdResponse);
//
//                                                                                        }
//                                                                                    }
//
//                                                                                    @Override
//                                                                                    public void onFailure(Throwable t) {
//                                                                                        view.hideProgressDialogPleaseWait();
//                                                                                        view.showToastShortTime("Error while booking order.");
//                                                                                    }
//                                                                                });
//                                                                    } else {
//                                                                        model.setOrderDetailsDescription(AddToCartList.get(0).getProduct_id(),
//                                                                                AddToCartList.get(0).getItemQuantity(),
//                                                                                AddToCartList.get(0).getItemPrice(),
//                                                                                AddToCartList.get(0).getOrderID(),
//                                                                                email, "zero",
//                                                                                countsID,
//                                                                                new INetworkPostOrder<SubmitOrderSingleListResponse>() {
//                                                                                    @Override
//                                                                                    public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
//                                                                                        if (counts == AddToCartList.size()) {
//                                                                                            view.deleteItemsFromCart();
//                                                                                            view.hideProgressDialogPleaseWait();
//
//                                                                                            return;
//                                                                                        } else {
//                                                                                            // sendData(AddToCartList, counts, email);
//
//                                                                                        }
//                                                                                    }
//
//                                                                                    @Override
//                                                                                    public void onFailure(Throwable t) {
//                                                                                        view.hideProgressDialogPleaseWait();
//                                                                                        view.showToastShortTime("Error while booking order.");
//                                                                                    }
//                                                                                });
//                                                                    }
//                                                                } else {
//                                                                    view.hideProgressDialogPleaseWait();
//                                                                    view.showToastShortTime("Please add items in cart.");
//
//                                                                }
//                                                            }
//
//                                                            @Override
//                                                            public void onErrorReceived(Exception ex) {
//                                                                view.hideProgressDialogPleaseWait();
//
//                                                                if (ex.getMessage() != null) {
//                                                                    view.showToastShortTime("Error while booking order.");
//                                                                } else {
//                                                                    view.showToastShortTime("Error while booking order.");
//                                                                }
//                                                            }
//                                                        });
//                                                    } else {
//                                                        view.hideProgressDialogPleaseWait();
//                                                        view.showToastShortTime("Error while order booking.");
//                                                    }
//
//                                                }
//
//                                                @Override
//                                                public void onErrorReceived(Exception ex) {
//                                                    view.hideProgressDialogPleaseWait();
//                                                    view.showToastShortTime("Error while booking order.");
//
//                                                }
//                                            });
//
//
//                                            //   } else {
//                                            // view.showToastShortTime("Error while booking order.");
//                                            // }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Throwable t) {
//                                            view.hideProgressDialogPleaseWait();
//                                            view.showToastShortTime("Can't submit order.Error while submit data.");
//                                        }
//                                    });
//                } else {
//                    view.hideProgressDialogPleaseWait();
//                    view.showToastShortTime("Error while saving data");
//                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });

    }

    private void sendData(final List<AddToCart> addToCarts, int counts, final String email, final String paymode, final String orderIdResponse) {
        int cc = counts;
        counts = counts + 1;
        if (counts == addToCarts.size()) {
            model.setOrderDetailsDescription(addToCarts.get(cc).
                            getProduct_id(),
                    addToCarts.get(cc).getItemQuantity(),
                    addToCarts.get(cc).getItemPrice(),
                    addToCarts.get(cc).getOrderID(),
                    email,
                    "one",
                    counts,
                    new INetworkPostOrder<SubmitOrderSingleListResponse>() {
                        @Override
                        public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
                            if (counts == addToCarts.size()) {
                                if (!utils.isTextNullOrEmpty(paymode)) {
                                    if (paymode.equalsIgnoreCase("Credit Card (Stripe)")) {
                                        sendStripeData(orderIdResponse, "");
                                    } else {
                                        sessionManager.removeCreditCardSession();
                                        view.deleteItemsFromCart();
                                        view.hideProgressDialogPleaseWait();
                                    }
                                } else {
                                    sessionManager.removeCreditCardSession();
                                    view.deleteItemsFromCart();
                                    view.hideProgressDialogPleaseWait();
                                }

                                return;
                            } else {
                                sendData(addToCarts, counts, email, paymode, orderIdResponse);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            sessionManager.removeCreditCardSession();
                            view.hideProgressDialogPleaseWait();
                            view.showToastShortTime("Error while booking order.");
                        }
                    });
        } else {
            model.setOrderDetailsDescription(addToCarts.get(cc).
                            getProduct_id(),
                    addToCarts.get(cc).getItemQuantity(),
                    addToCarts.get(cc).getItemPrice(),
                    addToCarts.get(cc).getOrderID(),
                    email,
                    "zero",
                    counts,
                    new INetworkPostOrder<SubmitOrderSingleListResponse>() {
                        @Override
                        public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
                            if (counts == addToCarts.size()) {
                                sessionManager.removeCreditCardSession();
                                view.deleteItemsFromCart();
                                view.hideProgressDialogPleaseWait();
                                return;
                            } else {
                                sendData(addToCarts, counts, email, paymode, orderIdResponse);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            sessionManager.removeCreditCardSession();
                            view.hideProgressDialogPleaseWait();
                            view.showToastShortTime("Error while booking order.");
                        }
                    });
        }
    }


    @Override
    public void addItemsToBooking(List<AddToCart> addToCarts, final String firstName, final String company, final String phone,
                                  final String email, final String streetAddress1, final String paymode,
                                  final String notes, String selfPickup, final String flatCharges, final String username,
                                  final String gst, final String totalAmount) {
        model.addItemsToBooking(addToCarts, firstName, company, phone, email, streetAddress1, paymode, notes,
                flatCharges, selfPickup, new BillingTotalAmountViewModel.IBookingInsert() {
                    @Override
                    public void onSuccess(boolean success) {
                        if (success) {

                            model.postOrderDetails(firstName, streetAddress1, sessionManager.getEmail(),
                                    company, phone, paymode, notes, username, flatCharges, gst, totalAmount, new
                                            INetworkLoginSignup<SubmitAddressResponse>() {
                                                @Override
                                                public void onSuccess(SubmitAddressResponse response) {
                                                    //  view.hideProgressDialogPleaseWait();
                                                    //Log.d("messh1", response.getMessage() + "," + response.getStatus());
                                                    // if (response == null) {
                                                    //   view.showToastShortTime("Error while booking order.");
                                                    //  return;
                                                    //}
                                                    //     if (response.getStatus()) {
                                                    //view.showToastShortTime(response.getMessage());
                                                    int i = 0;
                                                    if (!response.getStatus()) {
                                                        view.hideProgressDialogPleaseWait();
                                                        sessionManager.removeCreditCardSession();
                                                        view.showToastShortTime("Order booking failed.");
                                                        return;
                                                    } else if (utils.isTextNullOrEmpty(response.getOrderID())) {
                                                        sessionManager.removeCreditCardSession();
                                                        view.hideProgressDialogPleaseWait();
                                                        view.showToastShortTime("Order booking failed.");
                                                        return;
                                                    }


                                                    String orderIdResponse = "";
                                                    orderIdResponse = response.getOrderID();
                                                    final String finalOrderIdResponse = orderIdResponse;

                                                    // view.deleteItemsFromCart();
                                                    model.updateBooking(response.getOrderID(), new BillingTotalAmountViewModel.IBookingInsert() {
                                                        @Override
                                                        public void onSuccess(boolean success) {
                                                            if (success) {
                                                                model.getCartDetailsListItems(new BillingTotalAmountViewModel.IFetchCartDetailsList() {
                                                                    @Override
                                                                    public void onCartDetailsReceived(final List<AddToCart> AddToCartList) {
                                                                        // String jsonObjectStr = new Gson().toJson(AddToCartList);
                                                                        // jsonObjectStr = "{\"test\":" + jsonObjectStr + "}";
                                                                        if (AddToCartList.size() > 0) {
                                                                            int countsID = 0;
                                                                            countsID = countsID + 1;

                                                                            if (AddToCartList.size() == 1) {
                                                                                model.setOrderDetailsDescription(AddToCartList.get(0).getProduct_id(),
                                                                                        AddToCartList.get(0).getItemQuantity(),
                                                                                        AddToCartList.get(0).getItemPrice(),
                                                                                        AddToCartList.get(0).getOrderID(),
                                                                                        email, "one",
                                                                                        countsID,
                                                                                        new INetworkPostOrder<SubmitOrderSingleListResponse>() {
                                                                                            @Override
                                                                                            public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
                                                                                                if (counts == AddToCartList.size()) {
                                                                                                    if (!utils.isTextNullOrEmpty(paymode)) {
                                                                                                        if (paymode.equalsIgnoreCase("Credit Card (Stripe)")) {
                                                                                                            sendStripeData(finalOrderIdResponse, "");
                                                                                                        } else {
                                                                                                            sessionManager.removeCreditCardSession();
                                                                                                            view.deleteItemsFromCart();
                                                                                                            view.hideProgressDialogPleaseWait();
                                                                                                        }
                                                                                                    } else {
                                                                                                        sessionManager.removeCreditCardSession();
                                                                                                        view.deleteItemsFromCart();
                                                                                                        view.hideProgressDialogPleaseWait();
                                                                                                    }
                                                                                                    return;
                                                                                                } else {
                                                                                                    sendData(AddToCartList, counts, email, paymode, finalOrderIdResponse);

                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Throwable t) {
                                                                                                sessionManager.removeCreditCardSession();
                                                                                                view.hideProgressDialogPleaseWait();
                                                                                                view.showToastShortTime("Error while booking order.");
                                                                                            }
                                                                                        });
                                                                            } else {
                                                                                model.setOrderDetailsDescription(AddToCartList.get(0).getProduct_id(),
                                                                                        AddToCartList.get(0).getItemQuantity(),
                                                                                        AddToCartList.get(0).getItemPrice(),
                                                                                        AddToCartList.get(0).getOrderID(),
                                                                                        email, "zero",
                                                                                        countsID,
                                                                                        new INetworkPostOrder<SubmitOrderSingleListResponse>() {
                                                                                            @Override
                                                                                            public void onSuccess(SubmitOrderSingleListResponse response, int counts) {
                                                                                                if (counts == AddToCartList.size()) {
                                                                                                    sessionManager.removeCreditCardSession();
                                                                                                    view.deleteItemsFromCart();
                                                                                                    view.hideProgressDialogPleaseWait();

                                                                                                    return;
                                                                                                } else {
                                                                                                    sendData(AddToCartList, counts, email, paymode, finalOrderIdResponse);

                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Throwable t) {
                                                                                                sessionManager.removeCreditCardSession();
                                                                                                view.hideProgressDialogPleaseWait();
                                                                                                view.showToastShortTime("Error while booking order.");
                                                                                            }
                                                                                        });
                                                                            }
                                                                        } else {
                                                                            //sessionManager.removeCreditCardSession();
                                                                            view.hideProgressDialogPleaseWait();
                                                                            view.showToastShortTime("Please add items in cart.");

                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onErrorReceived(Exception ex) {
                                                                        sessionManager.removeCreditCardSession();
                                                                        view.hideProgressDialogPleaseWait();

                                                                        if (ex.getMessage() != null) {
                                                                            view.showToastShortTime("Error while booking order.");
                                                                        } else {
                                                                            view.showToastShortTime("Error while booking order.");
                                                                        }
                                                                    }
                                                                });
                                                            } else {
                                                                sessionManager.removeCreditCardSession();
                                                                view.hideProgressDialogPleaseWait();
                                                                view.showToastShortTime("Error while order booking.");
                                                            }

                                                        }

                                                        @Override
                                                        public void onErrorReceived(Exception ex) {
                                                            sessionManager.removeCreditCardSession();
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
                                                    sessionManager.removeCreditCardSession();
                                                    view.hideProgressDialogPleaseWait();
                                                    view.showToastShortTime("Can't submit order.Error while submit data.");
                                                }
                                            });
                        } else {
                            sessionManager.removeCreditCardSession();
                            view.hideProgressDialogPleaseWait();
                            view.showToastShortTime("Can't submit order.Error while submit data.");
                        }
                    }

                    @Override
                    public void onErrorReceived(Exception ex) {
                        sessionManager.removeCreditCardSession();
                        view.showToastShortTime("Can't submit order.Error while submit data.");
                        view.hideProgressDialogPleaseWait();

                    }
                });
    }


    @Override
    public void addItemsToBookingServer(List<AddToCart> addToCarts, final String firstName, final String company, final String phone,
                                        final String email, final String streetAddress1, final String paymode,
                                        final String notes, String selfPickup, final String flatCharges, final String username,
                                        final String gst, final String totalAmount,
                                        final String randomKey) {

//                            JSONObject jsonObjectAddress = new JSONObject();
//                            try {
//                                jsonObjectAddress.put("orderkeyid", randomKey);
//                                jsonObjectAddress.put("orderfrom", "mobile");
//                                jsonObjectAddress.put("name", firstName);
//                                jsonObjectAddress.put("username", username);
//                                jsonObjectAddress.put("address", streetAddress1);
//                                jsonObjectAddress.put("email", email);
//                                jsonObjectAddress.put("company", company);
//                                jsonObjectAddress.put("phone", phone);
//                                jsonObjectAddress.put("payment", paymode);
//                                jsonObjectAddress.put("message", notes);
//                                jsonObjectAddress.put("GST", gst);
//
//                                jsonObjectAddress.put("FlatCharges", flatCharges);
//                                jsonObjectAddress.put("Total", totalAmount);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }


        //   String jsonObjectStr = new Gson().toJson(jsonObjectAddress);


        model.postOrderDetailsJson(randomKey, "mobile", firstName,
                username, streetAddress1, email, company, phone, paymode, notes,
                flatCharges, gst, totalAmount,
                new INetworkLoginSignup<SubmitOrderJsonResponse>() {
                    @Override
                    public void onSuccess(SubmitOrderJsonResponse response) {

                        if (response == null) {
                            view.showToastShortTime("Can't submit order.Error while submit data.");
                            view.hideProgressDialogPleaseWait();

                            return;
                        } else {
                            if (response.getStatus()) {
                                String dataTime = getCurrentDateTime();
                                if (!utils.isTextNullOrEmpty(paymode)) {
                                    if (paymode.equalsIgnoreCase("Credit Card (Stripe)")) {
                                        sendStripeData(response.getId(), "mobile" + dataTime);
                                    } else {
                                        sessionManager.removeRandomKeySession();
                                        sessionManager.setKeyRandomKey("mobile" + dataTime);
                                        sessionManager.removeCreditCardSession();
                                        view.hideProgressDialogPleaseWait();
                                        view.itemRemovedFromCart();
                                    }
                                } else {
                                    sessionManager.removeRandomKeySession();
                                    sessionManager.setKeyRandomKey("mobile" + dataTime);
                                    sessionManager.removeCreditCardSession();
                                    view.hideProgressDialogPleaseWait();
                                    view.itemRemovedFromCart();
                                }
                            } else {
                                view.hideProgressDialogPleaseWait();
                                sessionManager.removeCreditCardSession();
                                view.showToastShortTime("Can't submit order.Error while submit data.");
                            }

                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        sessionManager.removeCreditCardSession();
                        view.hideProgressDialogPleaseWait();
                        view.showToastShortTime("Can't submit order.Error while submit data.");
                    }
                });
    }

    private String getCurrentDateTime() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOUR);
            return df.format(c.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    private void sendStripeData(String orderID, String randomKey) {

        Card card;

        if (utils.isInternetAvailable()) {
            if (!utils.isTextNullOrEmpty(sessionManager.getExpiryDate()) ||
                    !utils.isTextNullOrEmpty(sessionManager.getCardNumber()) ||
                    !utils.isTextNullOrEmpty(sessionManager.getCVCNumber())) {
                String[] cardExpiryDate = sessionManager.getExpiryDate().split("/");
                card = new Card(sessionManager.getCardNumber(), Integer.valueOf(cardExpiryDate[0]), Integer.valueOf(cardExpiryDate[1]), sessionManager.getCVCNumber());
                // view.showProgressDialogPleaseWait();
                view.sendStripe(card, orderID, randomKey);
            } else {
                view.hideProgressDialogPleaseWait();
                view.showToastShortTime("Please Enter Credit Card Details.");
                view.gotoStripe();
            }


        } else {
            sessionManager.removeCreditCardSession();
            view.hideProgressDialogPleaseWait();
            view.showToastShortTime("Please connect to internet.");

        }
    }


    @Override
    public void fetchCartsFromServer(String randomKey) {
        view.showProgressDialogPleaseWait();
        model.fetchCarts(randomKey, new INetworkListGeneral<AddToCartResponse>() {
            @Override
            public void onSuccess(AddToCartResponse response) {
                view.hideProgressDialogPleaseWait();

                if (response == null) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.showToastShortTime("Error while fetch data.");

                    return;
                } else {

                    List<Product> productResponses = response.getProduct();
                    if (productResponses == null) {
                        view.hideRecyclerView();
                        view.setParentFields();
                        view.showToastShortTime("Error while fetch data.");


                        return;
                    }
                    if (productResponses.size() > 0) {
                        view.showRecyclerView();
                        view.setAdapter(productResponses);
                        return;

                    } else {

                        view.hideRecyclerView();
                        view.setParentFields();

                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                view.hideRecyclerView();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while add product.");
                }

            }
        });
    }


//    private void setAdapter(List<AddToCart> AddToCartList) {
//        if (adapter == null) {
//            adapter = new BillingTotalAmountViewAdapter(AddToCartList);
//            view.showRecylerViewProductsDetail(adapter);
//        } else {
//            adapter.removeAll();
//            adapter.addAll(AddToCartList);
//        }
//
//        getSubTotal(AddToCartList);
//    }


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