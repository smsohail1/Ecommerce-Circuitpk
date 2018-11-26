package com.xekera.Ecommerce.ui.add_to_cart;

import android.os.Handler;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedModel;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.List;

public class AddToCartPresenter implements AddToCartMVP.Presenter {
    private AddToCartMVP.View view;
    private AddToCartMVP.Model model;
    private AddToCartAdapter adapter;

    private SessionManager sessionManager;
    private Utils utils;

    public AddToCartPresenter(AddToCartMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(AddToCartMVP.View view) {
        this.view = view;
    }

    @Override
    public void fetchCartDetails() {
        model.getCartDetailsList(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    return;
                } else {
                    view.hideNoCartItemFound();
                    view.showRecyclerView();
                    setAdapter(addToCarts);
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void removeItemFromCart(AddToCart productItems) {

        model.removeSelectedCartDetails(productItems, new AddToCartModel.IRemoveSelectedItemDetails() {
            @Override
            public void onSuccess() {
                view.showToastShortTime("Item removed from Cart.");

                getUpdatedTotalAmount();
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                view.showToastShortTime(ex.getMessage());
            }
        });


    }

    private void getUpdatedTotalAmount() {
        model.getCartDetails(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    return;
                } else {
                    getSubTotal(addToCarts);

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    private void setAdapter(List<AddToCart> AddToCartList) {
        if (adapter == null) {
            adapter = new AddToCartAdapter(AddToCartList, this);
            view.showRecylerViewProductsDetail(adapter);
        } else {
            adapter.removeAll();
            adapter.addAll(AddToCartList);
        }

        getSubTotal(AddToCartList);
    }


    private void getSubTotal(List<AddToCart> addToCarts) {
        long price = 0;

        for (AddToCart i : addToCarts) {
            price = price + Long.valueOf(i.getItemPrice());
            // price = price + (Long.valueOf(i.getItemPrice()) * Long.valueOf(i.getItemQuantity()));

        }
        view.setSubTotal(String.valueOf(price));
    }


    @Override
    public void updateItemCountInDB(String quantity, String itemPrice, String productName) {
        model.updateItemCountInDB(quantity, itemPrice, productName, new AddToCartModel.ISaveProductDetails() {
            @Override
            public void onProductDetailsSaved(boolean updated) {
                if (updated) {
                    getUpdatedTotalAmount();

                }

            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    @Override
    public void getCartCountList() {
        model.getCartDetails(new AddToCartModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.hideRecyclerView();
                    view.setParentFields();
                    view.txtNoCartItemFound();
                    view.showMessageZeroItemOnCart();
                    return;
                } else {
                    view.navigateToBillingDetailScreen();
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();
                view.setParentFields();
                view.hideRecyclerView();
                view.showMessageZeroItemOnCart();
                view.showToastShortTime(ex.getMessage());
            }
        });
    }
}
