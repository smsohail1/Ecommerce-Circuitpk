package com.xekera.Ecommerce.ui.favourites;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.history.HistoryModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class FavouritesPresenter implements FavouritesMVP.Presenter {
    private FavouritesMVP.View view;
    private FavouritesMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;
    private Context context;
    private FavoritesAdapter adapter;
    private ProductItemActionListener actionListener;


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

    @Override
    public void removeFromFavourites(Favourites favourites, final int position) {
        model.removeSelectedCartDetails(favourites.getItemName(), new FavouritesModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess(boolean success) {
                if (success) {
                    //  getCount(position);
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
                    view.showToastShortTime("Item already available in cart");
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
                    view.showToastLongTime("Item added to cart successfully.");
                    removeItemFromFavourites(addToCart.getItemName(), position, imageView);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                view.showToastLongTime("Error while saving data.");

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

}
