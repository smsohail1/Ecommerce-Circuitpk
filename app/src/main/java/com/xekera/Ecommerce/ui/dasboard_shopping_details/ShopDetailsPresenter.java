package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import android.content.Context;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ShopDetailsPresenter implements ShopDetailsMVP.Presenter {
    private ShopDetailsMVP.View view;
    private ShopDetailsMVP.Model model;
    private SessionManager sessionManager;
    private ShopDetailsAdapter shopDetailsAdapter;
    private Utils utils;
    private ProductItemActionListener actionListener;


    public ShopDetailsPresenter(ShopDetailsMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(ShopDetailsMVP.View view) {
        this.view = view;
    }

    @Override
    public void setRecylerViewItems(Context context, List<ShoppingDetailModel> items) {

        // shopDetailsAdapter = new ShopDetailsAdapter(context, items, this);
        // view.showRecylerViewProductsDetail(shopDetailsAdapter);
    }

    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void removeItem(ShoppingDetailModel shoppingDetailModel) {
        model.removeSelectedCartDetails(shoppingDetailModel.getProductName(), new ShopDetailsModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                getUpdatedTotalCount();

            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });


    }

    @Override
    public void removeItem(String productName, final int position) {
        model.removeFromFavourite(productName, new ShopDetailsModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.showToastShortTime("Item removed from favourite.");
                view.setFavouriteButtonStatus(false, position);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });


    }

    @Override
    public void addItemToFavourites(Favourites favourites, boolean isChecked) {
        insertSelectedFavourites(favourites);
    }

    @Override
    public void getFavouritesList() {
        model.getFavouriteDetailsList(new ShopDetailsModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    view.setFavouriteList(favourites);
                    return;
                } else {
                    view.setFavouriteList(favourites);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();


                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    public void insertSelectedFavourites(final Favourites favourites) {

        model.checkItemAlreadyAddedOrNot(favourites.getItemName(), new ShopDetailsModel.IFetchFavDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    addItem(favourites);
                    return;
                } else {
                    view.showToastShortTime("Item already available in favourite.");
                    // setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });

    }


    private void addItem(Favourites favourites) {

        model.addItemToFavourites(favourites, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Item added to favourite.");
                    //  view.enableAddtoFavouriteButton();
                    // view.animationAddButton();

                } else {
                    // view.enableAddtoFavouriteButton();
                    // view.animationAddButton();

                    view.showToastShortTime("Error while add to favourite.");

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                //view.enableAddtoFavouriteButton();
                //view.animationAddButton();

                view.showToastShortTime("Error while saving data.");

            }
        });
    }


    @Override
    public void saveProductDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                   final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy) {
        model.getProductCount(productName, new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
                            "N", byteImage, String.valueOf(cutPrice), price, formattedDate);
                    noProductFound(addToCart, imgProductCopy);
                    return;
                } else {

                    updateItemCountInDB(String.valueOf(quantity), totalPrice,
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while in saving data.");

            }
        });

    }

    @Override
    public void saveProductDecrementDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                            final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy) {
        model.getProductCount(productName, new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
                            "N", byteImage, String.valueOf(cutPrice), price, formattedDate);
                    noProductFoundForDecrement(addToCart, imgProductCopy);
                    return;
                } else {

                    updateItemCountInDBForDecrement(String.valueOf(quantity), totalPrice,
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while in saving data.");

            }
        });

    }


    private void noProductFound(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    //view.showSnackBarShortTime("Item added to cart successfully.");
                    getUpdatedTotalCount(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while saving data.");

            }
        });
    }

    private void noProductFoundForDecrement(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showSnackBarShortTime("Cart updated successfully.");
                    getUpdatedTotalCountForDecrement(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while saving data.");

            }
        });
    }

    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, final ImageView imgProductCopy) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    //  view.showToastShortTime("Cart updated successfully.");

                    getUpdatedTotalCount(imgProductCopy);
                } else {
                    view.showToastLongTime("Error while saving data.");

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastLongTime("Error while saving data.");

            }
        });

    }

    @Override
    public void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice,
                                                final ImageView imgProductCopy) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showSnackBarShortTime("Cart updated successfully.");

                    getUpdatedTotalCountForDecrement(imgProductCopy);
                } else {
                    view.showToastLongTime("Error while saving data.");

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastLongTime("Error while saving data.");

            }
        });

    }


    private void getUpdatedTotalCount(final ImageView imgProductCopy) {
        model.getCartDetails(new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy, 0);
                    view.setCountZero(0);


                    return;
                } else {
                    if (actionListener != null)
                        actionListener.onItemTap(imgProductCopy, addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }


    private void getUpdatedTotalCount() {
        model.getCartDetails(new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCountZero(0);

                    return;
                } else {
                    view.setDecrementCount(addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }


    private void getUpdatedTotalCountForDecrement(final ImageView imgProductCopy) {
        model.getCartDetails(new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCountZero(0);


                    return;
                } else {
//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy, addToCarts.size());

                    view.setDecrementCount(addToCarts.size());
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


    public interface ProductItemActionListener {
        void onItemTap(ImageView imageView, int cartsCount);
    }
}
