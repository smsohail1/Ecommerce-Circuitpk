package com.xekera.Ecommerce.ui.dasboard_shopping_details;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.Category;
import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
import com.xekera.Ecommerce.data.rest.response.Product;
import com.xekera.Ecommerce.data.rest.response.ProductResponse;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.FavouritesDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingModel;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingObjectModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.util.AppConstants;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShopDetailsPresenter implements ShopDetailsMVP.Presenter {
    private ShopDetailsMVP.View view;
    private ShopDetailsMVP.Model model;
    private SessionManager sessionManager;
    private ShopDetailsAdapter shopDetailsAdapter;
    private Utils utils;
    private ProductItemActionListener actionListener;
    AppDatabase appDatabase;

    public ShopDetailsPresenter(ShopDetailsMVP.Model model, SessionManager sessionManager, Utils utils, AppDatabase appDatabase) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
        this.appDatabase = appDatabase;
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
    public void removeItem(final Product shoppingDetailModel) {

        model.checkItemAlreadyAddedOrNot(shoppingDetailModel.getName(), new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    //view.showToastShortTime("Item not available in cart");
                    return;
                } else {
                    removeItem(shoppingDetailModel.getName());

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
        model.removeSelectedCartDetails(productName, new ShopDetailsModel.IRemoveSelectedItemDetails() {
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
        model.removeFromFavourite(productName, new ShopDetailsModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.showToastShortTime("Item removed from favorite.");
                view.setFavouriteButtonStatus(false, position);
                view.getFavCounts();

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
    public void getFavouritesList(ProductResponse response) {
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

    @Override
    public void getFavouritesListByProductName(String productName, final int position) {
        // isFavourite(productName, position);
        model.getFavouriteDetailsListByName(productName, new ShopDetailsModel.IFetchOrderDetailsList() {
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

    @Override
    public void isAlreadyAddedInFavourites(final Product productItems, final int position, final Bitmap bitmap,
                                           final String quantity, final String imgUrl, final String productID,
                                           final String isEmailFav, final String productDesc, final List<String> imgArrList,
                                           final String nameSku) {
        model.getFavouriteDetailsListByName(productItems.getName(), new ShopDetailsModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> favourites) {
                if (favourites == null || favourites.size() == 0) {
                    //   byte[] bmp = bitmapToByteArray(bitmap);
                    byte[] bmp = new byte[0];

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    String jsonObjectImg = new Gson().toJson(imgArrList);

                    Favourites fav;
                    if (Long.valueOf(quantity) == 0) {
                        long totalPrice = Long.valueOf(productItems.getPrice()) * 1;

                        fav = new Favourites(productItems.getName(), productItems.getPrice(),
                                String.valueOf(productItems.getRegularPrice()), "In Stock", formattedDate,
                                bmp, "1", String.valueOf(totalPrice), imgUrl, productID, isEmailFav, productDesc,
                                jsonObjectImg, nameSku);
                    } else {
                        long totalPrice = Long.valueOf(productItems.getPrice()) * Long.valueOf(quantity);

                        fav = new Favourites(productItems.getName(), productItems.getPrice(),
                                String.valueOf(productItems.getRegularPrice()), "In Stock", formattedDate,
                                bmp, String.valueOf(quantity), String.valueOf(totalPrice), imgUrl, productID, isEmailFav, productDesc,
                                jsonObjectImg, nameSku);
                    }

                    addItemToFavourites(fav, true);

                } else {
                    removeItem(productItems.getName(), position);

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
    public void setProductItemsDetails(Context context, String sku) {

        model.getProductItemsDetails(sku, new INetworkListGeneral<ProductResponse>() {
            @Override
            public void onSuccess(ProductResponse response) {
                if (response == null) {
                    view.showToastShortTime("Error while fetch data.");
                    view.hideCircularProgressBar();
                    view.hideData();
                    view.hideAllData();

                    return;
                } else {
                    List<Product> productResponses = response.getProduct();
                    if (productResponses == null) {
                        view.showToastShortTime("Error while fetch data.");
                        view.hideCircularProgressBar();
                        view.hideData();
                        view.hideAllData();
                        return;
                    }
                    if (productResponses.size() > 0) {

                        //view.getFavourites(response);
                        view.setAdapterItems(productResponses);

                    } else {
                        view.showToastShortTime("No product available.");
                        view.hideCircularProgressBar();
                        view.hideData();
                        view.hideAllData();
//
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideCircularProgressBar();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while fetching products.");
                }

            }
        });
    }

    @Override
    public void addToCartApi(String productId, String quantity, String price, String discountPrice, String randomKey,
                             final ImageView imgProductCopy) {
        view.showProgressDialogPleaseWait();
        model.addToCart(productId, quantity, price, discountPrice, randomKey, new INetworkListGeneral<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response) {
                view.hideProgressDialogPleaseWait();
                view.hideCircularProgressBar();

                if (response == null) {
                    view.showToastShortTime("Error while add to cart.");

                    return;
                } else {
                    view.showToastShortTime("Item added to cart.");

                    if (actionListener != null)
                        actionListener.onItemTap(imgProductCopy, view.getCartCount());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideCircularProgressBar();
                view.hideProgressDialogPleaseWait();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while add product.");
                }

            }
        });
    }


    public void insertSelectedFavourites(final Favourites favourites) {

//        model.checkItemAlreadyAddedOrNot(favourites.getItemName(), new ShopDetailsModel.IFetchFavDetailsList() {
//            @Override
//            public void onCartDetailsReceived(List<Favourites> addToCarts) {
//                if (addToCarts == null || addToCarts.size() == 0) {
        addItem(favourites);
//                    return;
//                } else {
//                    view.showToastShortTime("Item already available in favourite.");
//                    // setAdapter(addToCarts);
//                }
//            }

//        @Override
//        public void onErrorReceived (Exception ex){
//            ex.printStackTrace();
//            view.showToastShortTime(ex.getMessage());
//        }
//    });

    }


    private void addItem(final Favourites favourites) {

        model.addItemToFavourites(favourites, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean isAdded) {
                if (isAdded) {
                    //  List<String> listFavourite = new ArrayList<>();
//                    listFavourite.add(favourites.getItemName());
//
//                    Set<String> favouriteSet = new HashSet<String>(listFavourite);
//
//                    sessionManager.setIsFavouriteList(favouriteSet);


                    view.showToastShortTime("Item added to favorites.");
                    //  getFavourites();

                    view.getFavCounts();
                    //  view.enableAddtoFavouriteButton();
                    // view.animationAddButton();

                } else {
                    // view.enableAddtoFavouriteButton();
                    // view.animationAddButton();
                    view.getFavCounts();

                    view.showToastShortTime("Error while add to favorites.");

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
        model.getFavouriteDetailsList(new ShopDetailsModel.IFetchOrderDetailsList() {
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
                                   final long cutPrice, final ImageView imgProductCopy,
                                   final Bitmap bitmap, final String imgUrl, final String productID, final String isEmailSent,
                                   final String productDesc, final List<String> imgArrList, final String nameSku) {
        model.getProductCount(productName, new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {

                    //  byte[] bmp = bitmapToByteArray(bitmap);
                    byte[] bmp = new byte[0];
                    String formattedDate = "";
                    formattedDate = getCurrentDate();
                    String jsonObjectImg = new Gson().toJson(imgArrList);

                    AddToCart addToCart = new AddToCart("", productName, totalPrice, String.valueOf(quantity),
                            "N", bmp, String.valueOf(cutPrice), price, formattedDate, imgUrl, productID,
                            isEmailSent, productDesc, jsonObjectImg, nameSku);
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    @Override
    public void saveProductDecrementDetails(final long quantity, final String price, final String totalPrice, final String productName,
                                            final long cutPrice, final ImageView imgProductCopy, final Bitmap bitmapAdd,
                                            final String imgUrl, final String productID, final String isEmailSent,
                                            final String productDesc, final List<String> imgArrList, final String nameSku) {
        model.getProductCount(productName, new ShopDetailsModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCartList) {
                if (addToCartList == null || addToCartList.size() == 0) {

                    String formattedDate = "";
                    formattedDate = getCurrentDate();
                    // byte[] byteImg = bitmapToByteArray(bitmapAdd);
                    byte[] byteImg = new byte[0];
                    String jsonObjectImg = new Gson().toJson(imgArrList);

                    AddToCart addToCart = new AddToCart("", productName, totalPrice, String.valueOf(quantity),
                            "N", byteImg, String.valueOf(cutPrice), price, formattedDate, imgUrl,
                            productID, isEmailSent, productDesc, jsonObjectImg, nameSku);
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
                view.showToastShortTime("Error while saving data.");

            }
        });
    }

    private void noProductFoundForDecrement(AddToCart addToCart, final ImageView imgProductCopy) {

        model.saveProductDetails(addToCart, new ShopDetailsModel.ISaveProductDetails() {
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
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ShopDetailsModel.ISaveProductDetails() {
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
        model.updateItemCountInDB(quantity, itemPrice, productName, cutPrice, new ShopDetailsModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    // view.showToastShortTime("Cart updated successfully.");

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
