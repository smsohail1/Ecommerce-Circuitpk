package com.xekera.Ecommerce.ui.continue_shopping;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ContinueShoppingPresenter implements ContinueShoppingMVP.Presenter {
    private ContinueShoppingMVP.View view;
    private ContinueShoppingMVP.Model model;
    private SessionManager sessionManager;
    private ShopDetailsAdapter shopDetailsAdapter;
    private Utils utils;
    private ProductItemActionListener actionListener;
    AppDatabase appDatabase;
    Context context;

    public ContinueShoppingPresenter(Context context, ContinueShoppingMVP.Model model, SessionManager sessionManager, Utils utils, AppDatabase appDatabase) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
        this.appDatabase = appDatabase;
    }

    @Override
    public void setView(ContinueShoppingMVP.View view) {
        this.view = view;
    }

    @Override
    public void setRecylerViewItems(Context context, List<ContinueShoppingObjectModel> items) {

        // shopDetailsAdapter = new ShopDetailsAdapter(context, items, this);
        // view.showRecylerViewProductsDetail(shopDetailsAdapter);
    }

    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public void removeItem(final ContinueShoppingObjectModel ContinueShoppingObjectModel) {
        model.checkItemAlreadyAddedOrNot(ContinueShoppingObjectModel.getProductName(), new ContinueShoppingModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    //view.showToastShortTime("Item not available in cart");
                    return;
                } else {
                    removeItem(ContinueShoppingObjectModel.getProductName());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });


    }

    private void removeItem(String productName) {
        model.removeSelectedCartDetails(productName, new ContinueShoppingModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.showToastShortTime("Item removed from cart.");
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
        model.removeFromFavourite(productName, new ContinueShoppingModel.IRemoveSelectedItemDetails() {
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
        model.getFavouriteDetailsList(new ContinueShoppingModel.IFetchOrderDetailsList() {
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

    @Override
    public void getFavouritesListByProductName(String productName, final int position) {
        // isFavourite(productName, position);
        model.getFavouriteDetailsListByName(productName, new ContinueShoppingModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    view.setIsFavourites(false, position);
                    // view.setFavouriteList(favourites);
                    return;
                } else {
                    view.setIsFavourites(true, position);
                    //view.setFavouriteList(favourites);
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

        model.checkItemAlreadyAddedOrNot(favourites.getItemName(), new ContinueShoppingModel.IFetchFavDetailsList() {
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


    private void addItem(final Favourites favourites) {

        model.addItemToFavourites(favourites, new ContinueShoppingModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    //  List<String> listFavourite = new ArrayList<>();
//                    listFavourite.add(favourites.getItemName());
//
//                    Set<String> favouriteSet = new HashSet<String>(listFavourite);
//
//                    sessionManager.setIsFavouriteList(favouriteSet);


                    view.showToastShortTime("Item added to favourite.");

                    getFavourites();
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

    public void getFavourites() {
        model.getFavouriteDetailsList(new ContinueShoppingModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {

                if (favourites == null || favourites.size() == 0) {
                    List<String> listFavourite = new ArrayList<>();

                    for (Favourites fav : favourites) {
                        listFavourite.add(fav.getItemName());
                    }
                    Set<String> favouriteSet = new HashSet<String>(listFavourite);

                    sessionManager.setIsFavouriteList(favouriteSet);

                    return;
                } else {
                    List<String> listFavourite = new ArrayList<>();

                    for (Favourites fav : favourites) {
                        listFavourite.add(fav.getItemName());
                    }
                    Set<String> favouriteSet = new HashSet<String>(listFavourite);

                    sessionManager.setIsFavouriteList(favouriteSet);

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
    public void saveProductDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                   final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy, final Bitmap bitmap) {
        model.getProductCount(productName, new ContinueShoppingModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {

                    byte[] bmp = bitmapToByteArray(bitmap);

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
                            "N", bmp, String.valueOf(cutPrice), price, formattedDate);
                    noProductFound(addToCart, imgProductCopy);
                    return;
                } else {

                    updateItemCountInDB(String.valueOf(quantity), totalPrice,
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while in saving data.");

            }
        });

    }

    private byte[] bitmapToByteArray(Bitmap bmp) {
        byte[] byteArray = new byte[0];
        if (bmp != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            bmp.recycle();
        }
        return byteArray;
    }

    @Override
    public void saveProductDecrementDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                            final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy) {
        model.getProductCount(productName, new ContinueShoppingModel.IFetchCartDetailsList() {
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
                view.showToastShortTime("Error while in saving data.");

            }
        });

    }


    private void noProductFound(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new ContinueShoppingModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    //view.showSnackBarShortTime("Item added to cart successfully.");
                    getUpdatedTotalCount(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    private void noProductFoundForDecrement(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new ContinueShoppingModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Cart updated successfully.");
                    getUpdatedTotalCountForDecrement(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice, final ImageView imgProductCopy) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ContinueShoppingModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    //  view.showToastShortTime("Cart updated successfully.");

                    getUpdatedTotalCount(imgProductCopy);
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
    public void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice,
                                                final ImageView imgProductCopy) {
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ContinueShoppingModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showToastShortTime("Cart updated successfully.");

                    getUpdatedTotalCountForDecrement(imgProductCopy);
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


    private void getUpdatedTotalCount(final ImageView imgProductCopy) {
        model.getCartDetails(new ContinueShoppingModel.IFetchCartDetailsList() {
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
        model.getCartDetails(new ContinueShoppingModel.IFetchCartDetailsList() {
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
        model.getCartDetails(new ContinueShoppingModel.IFetchCartDetailsList() {
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

