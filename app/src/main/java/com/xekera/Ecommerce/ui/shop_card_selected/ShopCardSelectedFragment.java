package com.xekera.Ecommerce.ui.shop_card_selected;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.varunest.sparkbutton.SparkButton;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCardSelectedFragment extends Fragment implements ShopCardSelectedMVP.View, View.OnClickListener {

    @BindView(R.id.imgProduct)
    protected ImageView imgProduct;
    @BindView(R.id.priceTextView)
    protected TextView priceTextView;
    @BindView(R.id.discountPriceTextView)
    protected TextView discountPriceTextView;
    @BindView(R.id.productNameLabelTextView)
    protected TextView productNameLabelTextView;
    @BindView(R.id.availabilitValueTextView)
    protected TextView availabilitValueTextView;
    @BindView(R.id.descriptionValueTextView)
    protected TextView descriptionValueTextView;
    @BindView(R.id.counterTextview)
    protected TextView counterTextview;
    @BindView(R.id.decrementImageButton)
    protected ImageView decrementImageButton;
    @BindView(R.id.incrementImageButton)
    protected ImageView incrementImageButton;
    @BindView(R.id.favouriteButton)
    protected SparkButton favouriteButton;



    @Inject
    protected ShopCardSelectedMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;

    public static final String KEY_SHOP_CARD_SELECTED_DETAILS = "shop_card_selected_details";
    public static final String KEY_SHOP_CARD_SELECTED_IMAGE = "shop_card_selected_image";

    ShoppingDetailModel shoppingDetailModel;
    Bitmap bitmapImage;

    long productsCartCounter = 0;
    boolean isFavourite = true;


    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    public ShopCardSelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        shoppingDetailModel = (ShoppingDetailModel) getArguments().getSerializable(KEY_SHOP_CARD_SELECTED_DETAILS);
        bitmapImage = getArguments().getParcelable(KEY_SHOP_CARD_SELECTED_IMAGE);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        try {
            setProductDetails();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTitle() {
        //((BaseActivity) getActivity()).setTitle(getString(R.string.shop_dashboard));
        try {


            ((BaseActivity) getActivity()).setTitle("kj");

        } catch (Exception e) {

        }
    }


    public void hideHumbergIcon() {
        ((BaseActivity) getActivity()).hideHumberIcon();

    }

    public void showBackImageIcon() {
        ((BaseActivity) getActivity()).showBackImageIcon();

    }

    public void hideLoginIcon() {
        ((BaseActivity) getActivity()).hideLoginIcon();
    }


    public void setProductDetails() {
        try {

            if (shoppingDetailModel != null && shoppingDetailModel.getProductName() != null)
                ((BaseActivity) getActivity()).setTitle(shoppingDetailModel.getProductName());


            if (bitmapImage != null)
                imgProduct.setImageBitmap(bitmapImage);

            if (shoppingDetailModel != null && shoppingDetailModel.getProductPrice() != null) {
                priceTextView.setText(shoppingDetailModel.getProductPrice());
            }

            if (shoppingDetailModel != null) {
                // discountPriceTextView.setText(shoppingDetailModel.getProductPrice());
                discountPriceTextView.setText("120");

            }

            if (shoppingDetailModel != null) {
                counterTextview.setText(String.valueOf(shoppingDetailModel.getItemQuantity()));
            }

            if (shoppingDetailModel != null && shoppingDetailModel.getProductName() != null) {
                productNameLabelTextView.setText(shoppingDetailModel.getProductName());
            }

        } catch (Exception e) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop_card_selected, container, false);

        initializeViews(v);


        return v;
    }


    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        incrementImageButton.setOnClickListener(this);
        decrementImageButton.setOnClickListener(this);
        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);
        setProductDetails();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void showProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.showDialog();
    }

    @Override
    public void hideProgressDialogPleaseWait() {
        progressDialogControllerPleaseWait.hideDialog();
    }

    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message);
    }

    @Override
    public void showToastLongTime(String message) {
        toastUtil.showToastLongTime(message);
    }

    @Override
    public void showSnackBarShortTime(String message, View view) {
        snackUtil.showSnackBarShortTime(view, message);
    }

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }


    public ShopCardSelectedFragment newInstance(ShoppingDetailModel shoppingDetailModel, Bitmap bitmapImg) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SHOP_CARD_SELECTED_DETAILS, shoppingDetailModel);
        bundle.putParcelable(KEY_SHOP_CARD_SELECTED_IMAGE, bitmapImg);
        ShopCardSelectedFragment fragment = new ShopCardSelectedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.incrementImageButton:
                try {

                    String noOfProductsIncrement = counterTextview.getText().toString();
                    long noOfProductsIntIncrement = Long.valueOf(noOfProductsIncrement);
                    if (utils.isTextNullOrEmpty(noOfProductsIncrement) || noOfProductsIntIncrement == 0) {
                        snackUtil.showSnackBarShortTime(view, "Product not available");
                        //  shoppingDetailModel.setItemQuantity(0);
                        noOfProductsIntIncrement = 0;
                        counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

                        return;
                    } else {
                        // productsCartCounter = shoppingDetailModel.getItemQuantity() + 1;

                        noOfProductsIntIncrement = noOfProductsIntIncrement  + 1;

                        // shoppingDetailModel.setItemQuantity(productsCartCounter);
                        //productsCartCounter = productsCartCounter + 1;
                        counterTextview.setText(String.valueOf(noOfProductsIntIncrement));
                    }

                } catch (Exception e) {

                }
                break;
            case R.id.decrementImageButton:
                try {
                    String noOfProductsDecrement = counterTextview.getText().toString();
                    long noOfProductsIntDecrement = Long.valueOf(noOfProductsDecrement);
                    if (utils.isTextNullOrEmpty(noOfProductsDecrement)) {
                        snackUtil.showSnackBarShortTime(view, "Product not available");
                        //  shoppingDetailModel.setItemQuantity(0);
                        //decrementCounter = 0;
                        noOfProductsIntDecrement = 0;
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));

                        return;
                    } else {
                        if (noOfProductsIntDecrement == 1) {
                            counterTextview.setText(String.valueOf(noOfProductsIntDecrement));
                            //   shoppingDetailModel.setItemQuantity(1);
                            // decrementCounter = 1;
                            noOfProductsIntDecrement = 1;
                            return;
                        }
                        //  productsCartCounter = shoppingDetailModel.getItemQuantity() - 1;
                        noOfProductsIntDecrement = noOfProductsIntDecrement - 1;
                        // shoppingDetailModel.setItemQuantity(productsCartCounter);

                        //productsCartCounter = productsCartCounter - 1;
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));
                    }

                } catch (Exception e) {

                }
                break;

            case R.id.favouriteButton:
                if (isFavourite) {
                    showSnackBarShortTime("Add item to favourites.", getView());
                    isFavourite = false;

                } else {
                    showSnackBarShortTime("Remove item from favourites.", getView());
                }
                break;
        }
    }


//    public void setCallBack(ShopDetailsFragment shopDetailsFragment) {
//        this.shopDetailsFragment = shopDetailsFragment;
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
//        shopDetailsFragment.notifyAdapter(shoppingDetailModel);
//    }
}
