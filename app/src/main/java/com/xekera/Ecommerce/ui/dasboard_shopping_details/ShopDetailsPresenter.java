package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import android.content.Context;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

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
    public void saveProductDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                   final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy) {
        model.getProductCount(productName, new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {
                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
                            "N", byteImage, String.valueOf(cutPrice), price);
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
                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
                            "N", byteImage, String.valueOf(cutPrice), price);
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


    public interface ProductItemActionListener {
        void onItemTap(ImageView imageView, int cartsCount);
    }
}
