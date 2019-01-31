package com.xekera.Ecommerce.ui.favourites;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.history.HistoryModel;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FavouritesPresenter implements FavouritesMVP.Presenter {
    private FavouritesMVP.View view;
    private FavouritesMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;
    private FavoritesAdapter adapter;
    private ProductItemActionListener actionListener;
    private ProductAddRemoveActionListener productAddRemoveActionListener;


    public FavouritesPresenter(Context context, FavouritesMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.context = context;
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(FavouritesMVP.View view) {
        this.view = view;

    }

    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void setAddRemoveActionListener(ProductAddRemoveActionListener actionListener) {
        this.productAddRemoveActionListener = actionListener;
    }


    @Override
    public void removeFromFavourites(Favourites favourites, final int position) {
        model.removeSelectedCartDetails(favourites.getItemName(), new FavouritesModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {
                    //  getCount(position);
                    view.showToastShortTime("Item Removed from favourites.");
                    view.removeItemFromFavourites(position);


                }

            }

            @Override
            public void onError(Exception ex) {
                view.showToastShortTime(ex.getMessage());

            }
        });
    }

    @Override
    public void fetchFavouritesDetails() {
        model.getFavouriteDetailsList(new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    view.hideRecyclerView();
                    view.txtNoCartItemFound();
                    view.hideLoadingProgressDialog();
                    //  view.setCartCounts(0);
                    // view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();

                    view.showRecyclerView();
                    view.setAdapter(favourites);
                    // setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.hideRecyclerView();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void insertSelectedFavouritesToCart(final AddToCart addToCart, final int position, final ImageView imageView) {

        model.checkItemAlreadyAddedOrNot(addToCart.getItemName(), new FavouritesModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    addRecord(addToCart, position, imageView);
                    return;
                } else {
                    // view.showToastShortTime("Item already available in cart");
                    updateItemAddToCart(addToCart.getItemQuantity(), addToCart.getItemPrice(), addToCart.getItemName(),
                            addToCart.getItemCutPrice(), imageView, position);
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

    private void addRecord(final AddToCart addToCart, final int position, final ImageView imageView) {

        model.insertSelectedFavouritesToCart(addToCart, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Item added to cart successfully.");
                    removeItemFromFavourites(addToCart.getItemName(), position, imageView);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    private void getCount(final ImageView img, final int position) {
        model.getCartDetails(new FavouritesModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCartCounterTextview(0);
                    return;
                } else {
                    //  view.setCartCounterTextview(addToCarts.size());
                    if (actionListener != null)
                        actionListener.onItemTap(img, addToCarts.size(), position);

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }


    private void getCount(final int position) {

        model.getCartDetails(new FavouritesModel.IFetchCartDetailsList() {
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

    private void removeItemFromFavourites(String itemName, final int position, final ImageView imageView) {
        model.removeSelectedCartDetails(itemName, new FavouritesModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess(boolean success) {
                // view.removeItemFromFavourites(position);
                getCount(imageView, position);

            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }


    public interface ProductItemActionListener {
        void onItemTap(ImageView imageView, int cartsCount, int position);
    }

    public interface ProductAddRemoveActionListener {
        void onItemTap(ImageView imageView, int cartsCount);
    }

    private void getUpdatedTotalCount() {
        model.getTotalCounts(new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCartCounts(0);
                    return;
                } else {
                    view.setCartCounts(addToCarts.size());
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

    @Override
    public void saveProductDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                   final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy,
                                   final Bitmap bitmap, final String imgUrl, final String productID, final String isEmailFav) {
        model.getTotalCountsByName(productName, new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> bookings) {
                if (bookings == null || bookings.size() == 0) {

                    // byte[] bmp = bitmapToByteArray(bitmap);
                    byte[] bmp = new byte[0];
                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    Favourites favourites = new Favourites(productName, price, String.valueOf(cutPrice),
                            "In Stock", formattedDate, bmp, String.valueOf(quantity), totalPrice, imgUrl,
                            productID, isEmailFav);

                    noProductFound(favourites, imgProductCopy);

//                    Favourites favourites = new Favourites(itemName, itemIndividualPrice, itemCutPrice,
//                            availabilityInStock, formattedDate,
//                            byteArray, quantity);
//                    presenter.addItemToFavourites(favourites, favourite);


                    return;
                } else {

                    updateItemCountInDB(String.valueOf(quantity), price,
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while in saving data.");

            }
        });

    }

    public void updateItemCountInDB(String quantity, String itemPrice, String productName, String cutPrice,
                                    final ImageView imgProductCopy) {
        model.updateFavouriteItemCountInDB(quantity, itemPrice, productName, cutPrice, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    // view.showToastShortTime("Item updated successfully.");

                    // getUpdatedTotalCount(imgProductCopy);
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


    public void updateItemAddToCart(String quantity, String itemPrice, final String productName, String cutPrice,
                                    final ImageView imgProductCopy, final int position) {
        model.updateItemAddToCart(quantity, itemPrice, productName, cutPrice, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    view.showToastShortTime("Item updated successfully.");

                    getUpdatedTotalCount(imgProductCopy, productName, position);
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


    private void noProductFound(Favourites favourites, final ImageView imgProductCopy) {

        model.savefavouritesDetails(favourites, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    view.showToastShortTime("Item updated successfully.");
                    // getUpdatedTotalCount(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    private byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    @Override
    public void saveProductDecrementDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                            final long cutPrice, final byte[] byteImage, final ImageView imgProductCopy,
                                            final String imgUrl, final String productID, final String isEmailFav) {
        model.getProductCount(productName, new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> bookings) {
                if (bookings == null || bookings.size() == 0) {

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

//                    AddToCart addToCart = new AddToCart("434", productName, totalPrice, String.valueOf(quantity),
//                            "N", byteImage, String.valueOf(cutPrice), price, formattedDate);
//                    noProductFoundForDecrement(addToCart, imgProductCopy);
                    byte[] itemImage;
                    Favourites favourites = new Favourites(productName, price, String.valueOf(cutPrice),
                            "In Stock", formattedDate, byteImage, String.valueOf(quantity), totalPrice, imgUrl,
                            productID, isEmailFav);

                    noProductFoundForDecrement(favourites, imgProductCopy);


                    return;
                } else {

                    updateItemCountInDBForDecrement(String.valueOf(quantity), price,
                            productName, String.valueOf(cutPrice), imgProductCopy);
                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while in saving data.");

            }
        });

    }

    @Override
    public void removeItem(final Favourites favourites) {
        model.checkItemAlreadyAddedOrNot(favourites.getItemName(), new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> bookings) {
                if (bookings == null || bookings.size() == 0) {
                    //view.showToastShortTime("Item not available in cart");
                    return;
                } else {
                    removeItem(favourites.getItemName());

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
        model.removeItemFromCart(productName, new FavouritesModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {
                    view.showToastShortTime("Item removed from cart.");
                    getTotalCount();
                } else {
                    view.showToastShortTime("Error while remove item.");
                }
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });

    }

    private void getTotalCount() {
        model.getCartDetails(new FavouritesModel.IFetchCartDetailsList() {
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


    private void noProductFoundForDecrement(Favourites favourites, final ImageView imgProductCopy) {

        model.savefavouritesDetails(favourites, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    //view.showToastShortTime("Updated successfully.");
                    //  getUpdatedTotalCountForDecrement(imgProductCopy);


                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastShortTime("Error while saving data.");

            }
        });
    }


    public void updateItemCountInDBForDecrement(String quantity, String itemPrice, String productName, String cutPrice,
                                                final ImageView imgProductCopy) {
        model.updateFavouriteItemCountInDB(quantity, itemPrice, productName, cutPrice, new FavouritesModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    //view.showToastShortTime("Item updated successfully.");

                    //  getUpdatedTotalCountForDecrement(imgProductCopy);
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

    private void getUpdatedTotalCountForDecrement(final ImageView imgProductCopy) {
        model.getCartDetails(new FavouritesModel.IFetchCartDetailsList() {
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


    private void getUpdatedTotalCount(final ImageView imgProductCopy, final String productName, final int position) {
        model.getCartDetails(new FavouritesModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
//                    if (actionListener != null)
//                        actionListener.onItemTap(imgProductCopy, 0);
                    view.setCountZero(0);


                    return;
                } else {
                    removeItemFromFavourites(productName, position, imgProductCopy);

                    // if (productAddRemoveActionListener != null)
                    //   productAddRemoveActionListener.onItemTap(imgProductCopy, addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

}
