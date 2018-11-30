package com.xekera.Ecommerce.ui.shop_card_selected;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;
import com.xekera.Ecommerce.ui.shop_card_selected.model.MultipleImagesItem;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ShopCardSelectedPresenter implements ShopCardSelectedMVP.Presenter, ProductsImagesAdapter.IMultipleImageAdapter {
    private ShopCardSelectedMVP.View view;
    private ShopCardSelectedMVP.Model model;
    private ProductsImagesAdapter productsImagesAdapter;
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
        if (isFieldValid(addToCart.getItemName(), addToCart.getItemPrice(), addToCart.getItemQuantity())) {

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

    @Override
    public void setMultipleImagesItems(Context context, List<String> images) {
        List<MultipleImagesItem> imagesItems = new ArrayList<>();

        for (String img : images) {
            imagesItems.add(new MultipleImagesItem(img));
        }
        productsImagesAdapter = new ProductsImagesAdapter(imagesItems, this, context);
        view.showRecylerViewProductsImages(productsImagesAdapter);


    }


    private boolean isFieldValid(String productName, String price, String quantity) {
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


        return true;
    }


    @Override
    public void onImageClick(String clickedUrl) {
        view.setSelectedImage(clickedUrl);
    }
}
