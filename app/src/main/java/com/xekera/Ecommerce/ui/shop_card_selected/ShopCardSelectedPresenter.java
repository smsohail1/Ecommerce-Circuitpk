package com.xekera.Ecommerce.ui.shop_card_selected;

import android.content.Context;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class ShopCardSelectedPresenter implements ShopCardSelectedMVP.Presenter {
    private ShopCardSelectedMVP.View view;
    private ShopCardSelectedMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;

    public ShopCardSelectedPresenter(Context context, ShopCardSelectedMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(ShopCardSelectedMVP.View view) {
        this.view = view;
    }


    @Override
    public void saveProductDetails(final AddToCart addToCart) {
        if (isFieldValid(addToCart.getItemName(), addToCart.getItemPrice(), addToCart.getItemQuantity(), addToCart.getAddress1(),
                addToCart.getAddress2(), addToCart.getLatitude(), addToCart.getLongitude())) {

            model.getProductCount(addToCart.getItemName(), new ShopCardSelectedModel.IFetchCartDetailsList() {
                @Override
                public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                    if (addToCartList == null || addToCartList.size() == 0) {
                        noProductFound(addToCart);
                        return;
                    } else {

                        long productPrice = Long.valueOf(addToCart.getItemIndividualPrice());
                        long itemQuantity = Long.valueOf(addToCart.getItemQuantity());

                        updateItemCountInDB(addToCart.getItemQuantity(), String.valueOf(productPrice * itemQuantity),
                                addToCart.getItemName());
                    }

                }

                @Override
                public void onErrorReceived(Exception ex) {
                    view.showToastLongTime("Error while in saving data.");

                }
            });

        }

    }

    private void noProductFound(AddToCart addToCart) {

        model.saveProductDetails(addToCart, new ShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastLongTime("Item added to cart successfully.");
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showSnackBarShortTime("Error while saving data.");

            }
        });
    }

    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName) {
        model.updateItemCountInDB(quantity, itemPrice, productName, new ShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                view.showToastLongTime("Item added to cart successfully.");

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showSnackBarShortTime("Error while updating data.");

            }
        });

    }


    private boolean isFieldValid(String productName, String price, String quantity, String deliveryAddress1,
                                 String deliveryAddress2, String latitude, String longitude) {
        if (utils.isTextNullOrEmpty(productName)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.product_name_is_empty));
            return false;
        }
        if (utils.isTextNullOrEmptyOrZero(price)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.price_is_zero_error));
            return false;
        }

        if (utils.isTextNullOrEmptyOrZero(quantity)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.quantity_error));
            return false;
        }


        if (utils.isTextNullOrEmpty(deliveryAddress1)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.address1_error));
            return false;
        }


        return true;
    }


}
