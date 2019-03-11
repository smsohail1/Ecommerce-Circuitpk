package com.xekera.Ecommerce.ui.add_to_cart;

import android.os.Handler;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.add_remove_cart_response.AddRemoveCartResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.Product;
import com.xekera.Ecommerce.data.rest.response.delete_item_cart_response.DeleteItemCartResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import okhttp3.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddToCartPresenter implements AddToCartMVP.Presenter {
    private AddToCartMVP.View view;
    private AddToCartMVP.Model model;
    private AddToCartAdapter adapter;

    private SessionManager sessionManager;
    private Utils utils;

    public AddToCartPresenter(AddToCartMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(AddToCartMVP.View view) {
        this.view = view;
    }

    @Override
    public void fetchCartDetails() {
        model.getCartDetailsList(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    view.hideLoadingProgressDialog();
                    // view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.showRecyclerView();
                    // view.setAdapter(addToCarts);
                    //setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                //view.setCartCounts(0);

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void fetchCartDetailsOnBack(final int i) {

        model.getCartDetailsList(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    // view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.showRecyclerView();
                    // setAdapter(addToCarts, i);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                //  view.setCartCounts(0);

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void removeItemFromCart(final Product productItems, final int position, final String quantity) {

        model.deleteCartItem(productItems.getId(), new INetworkListGeneral<DeleteItemCartResponse>() {
            @Override
            public void onSuccess(DeleteItemCartResponse response) {

                if (response == null) {
                    view.hideProgressDialogPleaseWait();
                    view.showToastShortTime("Error while delete item from cart");
                    return;
                } else {
                    view.showToastShortTime("Item removed from Cart.");
                    view.removeItemFromAdapter(position);
                    updatePrice(productItems,quantity);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                view.showToastShortTime("Error while delete item from cart");

            }
        });

//        model.removeSelectedCartDetails(productItems, new AddToCartModel.IRemoveSelectedItemDetails() {
//            @Override
//            public void onSuccess() {
//                view.showToastShortTime("Item removed from Cart.");
//                view.removeItemFromAdapter(position);
//                getUpdatedTotalAmount();
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                ex.printStackTrace();
//                view.showToastShortTime(ex.getMessage());
//            }
//        });


    }


    private void updatePrice(Product productItems,String quantity) {
        view.updatePrice(productItems,quantity);

    }

    private void getUpdatedTotalAmount() {
        model.getCartDetails(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    // view.setCartCounts(0);
                    view.setCartCounterTextview(0);
                    return;
                } else {
                    getSubTotal(addToCarts);

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                // view.setCartCounts(0);
                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    private void setAdapter(List<AddToCart> AddToCartList) {
//        if (adapter == null) {
//            adapter = new AddToCartAdapter(AddToCartList, this);
//            view.showRecylerViewProductsDetail(adapter);
//        } else {
//            adapter.removeAll();
//            adapter.addAll(AddToCartList);
//        }

        getSubTotal(AddToCartList);
    }

    private void setAdapter(List<AddToCart> AddToCartList, int i) {
//        if (i == 1 || adapter == null) {
//            adapter = new AddToCartAdapter(AddToCartList, this);
//            view.showRecylerViewProductsDetail(adapter);
//        } else {
//            adapter.removeAll();
//            adapter.addAll(AddToCartList);
//        }

        getSubTotal(AddToCartList);
    }


    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());
            // price = price + (Long.valueOf(i.getItemPrice()) * Long.valueOf(i.getItemQuantity()));

        }
        view.setSubTotal(String.valueOf(price));
        //  view.setCartCounts(addToCarts.size());
        view.setCartCounterTextview(addToCarts.size());

    }

    private String getCurrentDate() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_TWO);
            return df.format(c.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new AddToCartModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    getUpdatedTotalAmount();

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    @Override
    public void getCartCountList() {
        model.getCartDetails(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    view.showMessageZeroItemOnCart();
                    return;
                } else {
                    view.navigateToBillingDetailScreen();
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.showMessageZeroItemOnCart();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void saveProductDetails(final String quantity, final long individualPrice, final String itemPrice, final String productName,
                                   final String cutPrice, final byte[] bytes, final String imgUrl, final String prodcutID,
                                   final String isEmailSent, final String productDesc, final String imgArrList,
                                   final String nameSku) {
        model.getProductCount(productName, new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {
                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("", productName, itemPrice, quantity, "N",
                            bytes, cutPrice, String.valueOf(individualPrice), formattedDate, imgUrl, prodcutID, isEmailSent,
                            productDesc, imgArrList, nameSku);
                    noProductFound(addToCart);
                    return;
                } else {

                    updateItemCountInDB(quantity, itemPrice,
                            productName, cutPrice);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while in saving data.");

            }
        });

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
                    view.txtNoCartItemFound();
                    view.hideLoadingProgressDialog();

                    //                    view.hideCircularProgressBar();
//                    view.hideData();
//                    view.hideAllData();

                    return;
                } else {

                    List<Product> productResponses = response.getProduct();
                    if (productResponses == null) {
                        view.hideRecyclerView();
                        view.setParentFields();
                        view.showToastShortTime("Error while fetch data.");
                        view.txtNoCartItemFound();
                        view.hideLoadingProgressDialog();

                        return;
                    }
                    if (productResponses.size() > 0) {
                        view.showRecyclerView();
                        view.hideNoCartItemFound();
                        view.setAdapter(productResponses);


                    } else {

                        view.hideRecyclerView();
                        view.setParentFields();
                        // view.showToastShortTime("No item added in cart.");
                        view.txtNoCartItemFound();
                        view.hideLoadingProgressDialog();

//                        view.hideData();
//                        view.hideAllData();
//
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                view.hideNoCartItemFound();
                view.hideRecyclerView();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while getting cart items.");
                }

            }
        });
    }

    @Override
    public void addRemoveCartServer(String quantity, String productId, final String price) {

        view.showProgressDialogPleaseWait();
        model.addRemoveCartServer(quantity, productId, new INetworkListGeneral<AddRemoveCartResponse>() {
            @Override
            public void onSuccess(AddRemoveCartResponse response) {
                view.hideProgressDialogPleaseWait();

                if (response == null) {
                    view.showToastShortTime("Error while update cart item.");
                    return;
                }

                view.incrementPriceOnClick(price);


            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while update cart item.");
                }
            }
        });
    }


    @Override
    public void removeCartServer(String quantity, String productId, final String price) {

        view.showProgressDialogPleaseWait();
        model.addRemoveCartServer(quantity, productId, new INetworkListGeneral<AddRemoveCartResponse>() {
            @Override
            public void onSuccess(AddRemoveCartResponse response) {
                view.hideProgressDialogPleaseWait();

                if (response == null) {
                    view.showToastShortTime("Error while update cart item.");
                    return;
                }

                view.updatePriceOnClick(price);


            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while update cart item.");
                }
            }
        });
    }

    private void noProductFound(AddToCart addToCart) {

        model.saveProductDetails(addToCart, new AddToCartModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastLongTime("Item added to cart successfully.");
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while saving data.");

            }
        });
    }

}
