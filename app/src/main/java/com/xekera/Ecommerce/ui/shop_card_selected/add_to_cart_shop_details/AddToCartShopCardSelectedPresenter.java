package com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details;

import android.content.Context;
import android.widget.ImageView;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.model.MultipleImagesItem;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToCartShopCardSelectedPresenter implements AddToCartShopCardSelectedMVP.Presenter,
        ProductsImagesAdapter.IMultipleImageAdapter {
    private AddToCartShopCardSelectedMVP.View view;
    private AddToCartShopCardSelectedMVP.Model model;
    private ProductsImagesAdapter productsImagesAdapter;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;

    public AddToCartShopCardSelectedPresenter(Context context, AddToCartShopCardSelectedMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(AddToCartShopCardSelectedMVP.View view) {
        this.view = view;
    }


    @Override
    public void saveProductDetails(final AddToCart addToCart) {
        if (isFieldValid(addToCart.getItemName(), addToCart.getItemPrice(), addToCart.getItemQuantity(), addToCart.getItemIndividualPrice())) {

            model.getProductCount(addToCart.getItemName(), new AddToCartShopCardSelectedModel.IFetchCartDetailsList() {
                @Override
                public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                    if (addToCartList == null || addToCartList.size() == 0) {
                        noProductFound(addToCart);
                        return;
                    } else {

                        //    long productPrice = Long.valueOf(addToCart.getItemIndividualPrice());
                        //  long itemQuantity = Long.valueOf(addToCart.getItemQuantity());

                        updateItemCountInDB(addToCart.getItemQuantity(), addToCart.getItemPrice(),
                                addToCart.getItemName(), addToCart.getItemCutPrice());
                    }

                }

                @Override
                public void onErrorReceived(Exception ex) {
                    view.showToastShortTime("Error while in saving data.");

                }
            });

        }

    }

    private void noProductFound(AddToCart addToCart) {

        model.saveProductDetails(addToCart, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Item added to cart successfully.");
                    getCount();
                    //view.shakeAddToCartTextview();
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    private void getCount() {
        model.getCartDetails(new AddToCartShopCardSelectedModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCartCounterTextview(0);

                    return;
                } else {
                    view.setCartCounterTextview(addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }


    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showToastShortTime("Item added to cart successfully.");
                    // view.shakeAddToCartTextview();

                } else {
                    view.showToastShortTime("Error while saving data.");

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while updating data.");

            }
        });

    }

    public void updateItemCountInDBWithDate(String quantity, String itemPrice, String productName, String cutPrice, String createdDate) {
        model.updateItemCountInDBWithDate(quantity, itemPrice, productName, cutPrice, createdDate, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showToastShortTime("Item added to cart successfully.");
                    // view.shakeAddToCartTextview();

                } else {
                    view.showToastShortTime("Error while saving data.");

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while updating data.");

            }
        });

    }


    @Override
    public void setMultipleImagesItems(Context context, List<String> images) {
        List<MultipleImagesItem> imagesItems = new ArrayList<>();
//        for (int i = 0; i < images.length; i++) {
//            imagesItems.add(new MultipleImagesItem(images[i]));

        for (String img : images) {
            imagesItems.add(new MultipleImagesItem(img));
        }
        // }
        productsImagesAdapter = new ProductsImagesAdapter(imagesItems, this, context);
        view.showRecylerViewProductsImages(productsImagesAdapter);


    }

    @Override
    public void onIncrementButtonClicked(long quantity, long price, long totalPrice, String productName, String cutPrice,
                                         byte[] byteImage, ImageView imgProductCopy) {
        saveProductDetails(quantity, price, totalPrice, productName,
                cutPrice, byteImage, imgProductCopy, "", "0");
    }


    private boolean isFieldValid(String productName, String totalPrice, String quantity, String individualPrice) {
        if (utils.isTextNullOrEmpty(productName)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.product_name_is_empty));
            return false;
        }

        if (utils.isTextNullOrEmptyOrZero(quantity)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.quantity_error));
            return false;
        }

        if (utils.isTextNullOrEmptyOrZero(totalPrice)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.quantity_error));
            return false;
        }


        if (utils.isTextNullOrEmptyOrZero(individualPrice)) {
            view.showToastShortTime(utils.getStringFromResourceId(R.string.price_is_zero_error));
            return false;
        }

        return true;
    }


    @Override
    public void onImageClick(String clickedUrl) {
        view.setSelectedImage(clickedUrl);
    }


    public void saveProductDetails(final long quantity, final long price, final long totalPrice, final String productName,
                                   final String cutPrice, final byte[] byteImage, final ImageView imgProductCopy,
                                   final String productID, final String isEmailSent) {
        model.getProductCount(productName, new AddToCartShopCardSelectedModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {
                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("", productName, String.valueOf(totalPrice), String.valueOf(quantity),
                            "N", byteImage, String.valueOf(cutPrice), String.valueOf(price), formattedDate,
                            "", productID, isEmailSent);
                    noProductFound(addToCart, imgProductCopy);
                    return;
                } else {

                    updateItemCountInDB(String.valueOf(quantity), String.valueOf(totalPrice),
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while in saving data.");

            }
        });

    }


    private void noProductFound(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    // view.showToastLongTime("Item added to cart successfully.");
                    getUpdatedTotalCount();


                } else {
                    view.showToastShortTime("Error while saving data.");

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                    final ImageView imgProductCopy) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showToastShortTime("Cart updated successfully.");

                    getUpdatedTotalCount();
                } else {
                    view.showToastShortTime("Error while saving data.");

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime("Error while saving data.");

            }
        });

    }

    @Override
    public void addItemToFavourites(final Favourites favourites, final boolean isChecked) {

        if (isChecked) {
            deleteItem(favourites.getItemName());

        } else {
            addItem(favourites);

        }

//        model.getFavouritesCount(new AddToCartShopCardSelectedModel.IFetchFavouritesDetailsList() {
//            @Override
//            public void onCartDetailsReceived(List<Favourites> favouritesList) {
//                if (favouritesList == null || favouritesList.size() == 0) {
//                    addItem(favourites);
//                    return;
//                } else {
//                    deleteItem(favourites.getItemName());
//                }
//
//            }
//
//            @Override
//            public void onErrorReceived(Exception ex) {
//                view.showToastLongTime("Error while in saving data.");
//
//            }
//        });


    }

    @Override
    public void setIsFavourite(String productName) {
        model.getFavouritesCount(productName, new AddToCartShopCardSelectedModel.IFetchFavouritesDetails() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    view.setIsFavourite(false);
                    return;
                } else {
                    view.setIsFavourite(true);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.setIsFavourite(false);

                view.showToastShortTime("Error while in saving data.");

            }
        });

    }

    private void addItem(Favourites favourites) {

        model.addItemToFavourites(favourites, new AddToCartShopCardSelectedModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Item added to favorites.");
                    view.enableAddtoFavouriteButton();
                    view.animationAddButton();
                    view.setFavCount();


                } else {
                    view.enableAddtoFavouriteButton();
                    view.animationAddButton();
                    view.setFavCount();

                    view.showToastShortTime("Error while add to favorites.");

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.enableAddtoFavouriteButton();
                view.animationAddButton();

                view.showToastShortTime("Error while saving data.");

            }
        });
    }


    private void deleteItem(String itemName) {
        model.deleteItem(itemName, new AddToCartShopCardSelectedModel.IRemoveFavouriteItemDetails() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {
                    view.enableAddtoFavouriteButton();
                    view.animateFavouriteButton();
                    view.showToastShortTime("Item removed from favourite.");
                    view.setFavCount();

                    return;
                } else {
                    view.enableAddtoFavouriteButton();
                    view.animateFavouriteButton();
                    view.showToastShortTime("Error while remove to favourite.");
                    view.setFavCount();


                }

            }

            @Override
            public void onError(Exception ex) {
                view.enableAddtoFavouriteButton();
                view.animateFavouriteButton();
                view.showToastShortTime("Error while in saving data.");

            }
        });
    }


    private void getUpdatedTotalCount() {
        model.getCartDetails(new AddToCartShopCardSelectedModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCountZero(0);

                    return;
                } else {
                    view.setCountZero(addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
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
}
