package com.xekera.Ecommerce.ui.favourites;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;
import com.google.gson.JsonObject;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.fetch_favourite_response.FetchFavouriteResponse;
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.AllProductsResponse;
import com.xekera.Ecommerce.data.rest.response.searchAllProductReponse.Product;
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
import okhttp3.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumMap;
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
    public void removeFromFavourites(String id, final int position, final String username, final String email) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("postid", id);
        view.showProgressDialogPleaseWait();
        model.removeSelectedCartDetails(jsonObject, new INetworkListGeneral<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                // view.hideProgressDialogPleaseWait();

                view.showToastShortTime("Item Removed from favorite.");
                fetchFavouritesCounts(username, email, position);
//                view.removeItemFromFavourites(position);
                //view.itemsCountsBottomView(1, 0);
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while delete favorite.");
                }
                //   view.itemsCountsBottomView(1, 0);

            }
        });

//        model.removeSelectedCartDetails(id, new FavouritesModel.IRemoveSelectedItemDetails() {
//            @Override
//            public void onSuccess(boolean success) {
//                if (success) {
//                    //  getCount(position);
//                    view.showToastShortTime("Item Removed from favourites.");
//                    view.removeItemFromFavourites(position);
//                    getFavouriteCount();
//
//                }
//
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                view.showToastShortTime(ex.getMessage());
//
//            }
//        });
    }

    public void removeFromFavouritesServer(final String id, final int position, final String username, final String email,
                                           final ImageView imgProductCopy) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("postid", id);
        model.removeSelectedCartDetails(jsonObject, new INetworkListGeneral<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {

                //  fetchFavouritesCounts(username, email, position);
                // view.hideProgressDialogPleaseWait();
                //  view.removeItemFromFavourites(position);
                if (actionListener != null)
                    actionListener.onItemTap(imgProductCopy, view.getCartCount(), position);

                fetchFavouritesServerCounts(username, email, imgProductCopy, position,
                        id);


//                view.removeItemFromFavourites(position);
                //view.itemsCountsBottomView(1, 0);
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                }
                //   view.itemsCountsBottomView(1, 0);

            }
        });

//        model.removeSelectedCartDetails(id, new FavouritesModel.IRemoveSelectedItemDetails() {
//            @Override
//            public void onSuccess(boolean success) {
//                if (success) {
//                    //  getCount(position);
//                    view.showToastShortTime("Item Removed from favourites.");
//                    view.removeItemFromFavourites(position);
//                    getFavouriteCount();
//
//                }
//
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                view.showToastShortTime(ex.getMessage());
//
//            }
//        });
    }


    public void getFavouriteCount() {
        model.getTotalCounts(new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.itemsCountsBottomView(1, 0);
                    return;
                } else {
                    view.itemsCountsBottomView(1, addToCarts.size());
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
    public void fetchFavouritesDetails() {
        model.getFavouriteDetailsList(new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    view.hideRecyclerView();
                    view.txtNoCartItemFound();
                    view.hideLoadingProgressDialog();
                    view.itemsCountsBottomView(1, 0);
                    //  view.setCartCounts(0);
                    // view.setCartCounts(0);
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.itemsCountsBottomView(1, favourites.size());
                    view.showRecyclerView();
                    //view.setAdapter(favourites);

                    // setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.hideRecyclerView();
                view.itemsCountsBottomView(1, 0);

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
                    //removeItemFromFavourites(addToCart.getItemName(), position, imageView);

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
                    getFavouriteCount();

                    return;
                } else {
                    //  view.setCartCounterTextview(addToCarts.size());
                    if (actionListener != null)
                        actionListener.onItemTap(img, addToCarts.size(), position);

                    getFavouriteCount();


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
//        model.removeSelectedCartDetails(itemName, new FavouritesModel.IRemoveSelectedItemDetails() {
//            @Override
//            public void onSuccess(boolean success) {
//                // view.removeItemFromFavourites(position);
//                getCount(imageView, position);
//
//            }
//
//            @Override
//            public void onError(Exception ex) {
//                ex.printStackTrace();
//                view.showToastShortTime(ex.getMessage());
//            }
//        });
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
                                   final Bitmap bitmap, final String imgUrl, final String productID, final String isEmailFav,
                                   final String productDesc, final String imgArrList, final String nameSku) {
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
                            productID, isEmailFav, productDesc, imgArrList, nameSku);

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
                                            final String imgUrl, final String productID, final String isEmailFav, final String productDesc, final String imgArrList,
                                            final String nameSku) {
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
                            productID, isEmailFav, productDesc, imgArrList, nameSku);

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


    @Override
    public void addToCartApi(String productId, String quantity, String price, String discountPrice, String randomKey,
                             final ImageView imgProductCopy, final int position, final String username, final String email,
                             final String productIdIncrement) {
        //view.showProgressDialogPleaseWait();
        model.addToCart(productId, quantity, price, discountPrice, randomKey, new INetworkListGeneral<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                // view.hideProgressDialogPleaseWait();

                if (response == null) {
                    view.showToastShortTime("Error while add to cart.");
                    view.hideProgressDialogPleaseWait();
                    return;
                } else {
                    view.showToastShortTime("Item added to cart.");
                    removeFromFavouritesServer(productIdIncrement, position, username, email, imgProductCopy);

//                    fetchFavouritesServerCounts(username, email, imgProductCopy, position,
//                            productIdIncrement);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while add to cart.");
                }

            }
        });
    }

    @Override
    public void getAllProducts(final String productIdIncrement, final String quantity, final String price, final String discountPrice, String randomKey,
                               final ImageView imgProductCopy, final int position, String username, String email, final String nameSku) {
        view.showProgressDialogPleaseWait();
        model.fetchAllProducts(productIdIncrement, quantity, price, discountPrice, randomKey, new INetworkListGeneral<AllProductsResponse>() {
            @Override
            public void onSuccess(AllProductsResponse response) {

                String productId = "";
                if (response == null) {
                    view.showToastShortTime("Error while add to cart.");
                    view.hideProgressDialogPleaseWait();
                    return;
                } else if (response.getProducts() == null) {
                    view.showToastShortTime("Error while add to cart.");
                    view.hideProgressDialogPleaseWait();

                    return;
                } else if (response.getProducts().size() == 0) {
                    view.showToastShortTime("Error while add to cart.");
                    view.hideProgressDialogPleaseWait();

                    return;
                } else {
                    for (Product product : response.getProducts()) {
                        if (product.getName_sku().equalsIgnoreCase(nameSku)) {
                            productId = product.getId();
                            break;
                        }
                    }

                    addToCartApi(productId, quantity, price, discountPrice,
                            sessionManager.getKeyRandomKey(), imgProductCopy, position,
                            sessionManager.getusername(), sessionManager.getEmail(),
                            productIdIncrement
                    );

                    // view.showToastShortTime("Item added to cart.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while add to cart.");
                }

            }
        });
    }

    @Override
    public void fetchFavouritesServer(String username, String email) {
        view.showProgressDialogPleaseWait();
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("username", sessionManager.getusername());
//        jsonObject.addProperty("useremail", sessionManager.getEmail());
        model.fetchFavouritesServer(username, email, new INetworkListGeneral<FetchFavouriteResponse>() {
            @Override
            public void onSuccess(FetchFavouriteResponse response) {

                view.hideProgressDialogPleaseWait();
                if (response == null) {
                    view.hideRecyclerView();
                    view.txtNoCartItemFound();
                    view.itemsCountsBottomView(1, 0);

                    return;
                } else if (response.getProducts() == null) {
                    view.hideRecyclerView();
                    view.txtNoCartItemFound();
                    view.itemsCountsBottomView(1, 0);
                    return;
                } else if (response.getProducts().size() == 0) {
                    view.hideRecyclerView();
                    view.txtNoCartItemFound();
                    view.itemsCountsBottomView(1, 0);
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.itemsCountsBottomView(1, response.getProducts().size());
                    view.showRecyclerView();
                    view.setAdapter(response.getProducts());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while fetch favorite.");
                }
                view.itemsCountsBottomView(1, 0);

            }
        });
    }

    private void fetchFavouritesServerCounts(final String username, final String email, final ImageView imgProductCopy,
                                             final int position,
                                             final String productIdIncrement) {

        model.fetchFavouritesServer(username, email, new INetworkListGeneral<FetchFavouriteResponse>() {
            @Override
            public void onSuccess(FetchFavouriteResponse response) {

               view.hideProgressDialogPleaseWait();
//                view.removeItemFromFavourites(position);


                if (response == null) {
                    view.itemsCountsBottomView(1, 0);

                    return;
                } else if (response.getProducts() == null) {

                    view.itemsCountsBottomView(1, 0);

                    return;
                } else if (response.getProducts().size() == 0) {

                    view.itemsCountsBottomView(1, 0);

                    return;
                } else {
                    view.itemsCountsBottomView(1, response.getProducts().size());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();

            }
        });
    }

    private void fetchFavouritesCounts(String username, String email, final int position) {

        model.fetchFavouritesServer(username, email, new INetworkListGeneral<FetchFavouriteResponse>() {
            @Override
            public void onSuccess(FetchFavouriteResponse response) {

                view.hideProgressDialogPleaseWait();
                view.removeItemFromFavourites(position);

                if (response == null) {
                    view.itemsCountsBottomView(1, 0);
                    return;
                } else if (response.getProducts() == null) {

                    view.itemsCountsBottomView(1, 0);
                    return;
                } else if (response.getProducts().size() == 0) {

                    view.itemsCountsBottomView(1, 0);
                    return;
                } else {
                    view.itemsCountsBottomView(1, response.getProducts().size());
                }

            }

            @Override
            public void onFailure(Throwable t) {
                view.hideProgressDialogPleaseWait();

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
                    getFavouriteCount();

                    return;
                } else {
                    // removeItemFromFavourites(productName, position, imgProductCopy);

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
