package com.xekera.Ecommerce.ui.favourites;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FavouritesFragment extends Fragment implements FavouritesMVP.View, FavoritesAdapter.IFvaouritesAddToCartAdapter {


    @BindView(R.id.recyclerViewAddToCartDetails)
    protected RecyclerView recyclerViewAddToCartDetails;
    @BindView(R.id.linearParent)
    protected LinearLayout linearParent;
    @BindView(R.id.txtNoCartItemFound)
    protected TextView txtNoCartItemFound;

    @Inject
    protected FavouritesMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    FavoritesAdapter adapter;


    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            // ((BaseActivity) getActivity()).showBottomNavigation();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);

        initializeViews(v);

        return v;
    }

    public void setTitle() {
        // ((BaseActivity) getActivity()).setTitle(getString(R.string.history_dashboard));
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        //((BaseActivity) getActivity()).showBottomNavigation();

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.setActionListener(new FavouritesPresenter.ProductItemActionListener() {
            @Override
            public void onItemTap(ImageView imageView, int cartsCount, final int position) {
                if (imageView != null) {
                    ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
                    ((BaseActivity) getActivity()).addItemToCart(cartsCount);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            removeItemFromFavourites(position);

                        }
                    }, 700);

                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchFavouritesDetails();

            }
        }, 600);


    }


    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }


    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void showRecylerViewProductsDetail(FavoritesAdapter favoritesAdapter) {
        recyclerViewAddToCartDetails.setAdapter(favoritesAdapter);

    }

    @Override
    public void showRecyclerView() {
        linearParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        linearParent.setVisibility(View.GONE);
    }

    @Override
    public void setCartCounts(long counts) {

        ((BaseActivity) getActivity()).setCartsCounts(counts, 1, "Favourite");

    }

    @Override
    public void setCartCounterTextview(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }


    @Override
    public void txtNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideNoCartItemFound() {
        txtNoCartItemFound.setVisibility(View.GONE);
    }


    @Override
    public void setAdapter(List<Favourites> addToCarts) {

        adapter = new FavoritesAdapter(getActivity(), addToCarts, this);
        showRecylerViewProductsDetail(adapter);

        //  setCartCounts(addToCarts.size());

    }

    @Override
    public void removeItemFromFavourites(int position) {
        adapter.removeByPosition(position);
    }


    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }

    @Override
    public void showSnackBarShortTime(String message, View view) {
        snackUtil.showSnackBarShortTime(view, message);
    }

    boolean isShowing = true;

    @Override
    public void addToCartFavourites(final Favourites favourites, final int position, final ImageView img) {
        if (isShowing) {
            isShowing = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    AddToCart addToCart = new AddToCart("15", favourites.getItemName(), favourites.getItemIndividualPrice()
                            , favourites.getItemQuantity(),
                            "N", favourites.getItemImage(), favourites.getItemCutPrice(), favourites.getItemIndividualPrice(),
                            formattedDate);
                    presenter.insertSelectedFavouritesToCart(addToCart, position, img);
                    isShowing = true;
                }
            }, 100);
        }
    }

    @Override
    public void removeFavourites(final Favourites favourites, final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.removeFromFavourites(favourites, position);

            }
        }, 150);
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

