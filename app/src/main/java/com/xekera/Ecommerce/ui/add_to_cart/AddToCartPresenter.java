package com.xekera.Ecommerce.ui.add_to_cart;

import android.os.Handler;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

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
                    view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.showRecyclerView();
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
    public void fetchCartDetailsOnBack(final int i) {

        model.getCartDetailsList(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    view.setCartCounts(0);
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
                view.setCartCounts(0);

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void removeItemFromCart(AddToCart productItems) {

        model.removeSelectedCartDetails(productItems, new AddToCartModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.showToastShortTime("Item removed from Cart.");

                getUpdatedTotalAmount();
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });


    }

    private void getUpdatedTotalAmount() {
        model.getCartDetails(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    view.setCartCounts(0);
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
                view.setCartCounts(0);
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
        view.setCartCounts(addToCarts.size());
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
                                   final String cutPrice, final byte[] bytes) {
        model.getProductCount(productName, new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {
                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("43", productName, itemPrice, quantity, "N",
                            bytes, cutPrice, String.valueOf(individualPrice), formattedDate);
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
