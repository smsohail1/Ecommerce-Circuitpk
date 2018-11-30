package com.xekera.Ecommerce.ui.shop_card_selected;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.varunest.sparkbutton.SparkButton;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.home_delivery_Address.DeliveryAddressActivity;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;


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
    @BindView(R.id.deliveryAddressImageView)
    protected ImageView deliveryAddressImageView;
    @BindView(R.id.deliveryAddressValueTextView)
    protected TextView deliveryAddressValueTextView;
    @BindView(R.id.deliveryAddress2ValueEdittext)
    protected EditText deliveryAddress2ValueEdittext;
    @BindView(R.id.btnAddToCart)
    protected Button btnAddToCart;
    @BindView(R.id.recyclerViewImageDetails)
    protected RecyclerView recyclerViewImageDetails;

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


    String latitude = "";
    String longitude = "";
    String placeName = "";

    Animation shake;

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
//            placeName = sessionManager.getKeyPlaceName();
//            latitude = sessionManager.getKeyLatitude();
//            longitude = sessionManager.getKeyLongitude();
//            if (!utils.isTextNullOrEmpty(placeName)) {
//                deliveryAddressValueTextView.setText(placeName);
//            } else {
//                deliveryAddressValueTextView.setText("");
//
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTitle() {
        //((BaseActivity) getActivity()).setTitle(getString(R.string.shop_dashboard));
        try {


            //  ((BaseActivity) getActivity()).setTitle("kj");

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

//            if (shoppingDetailModel != null && shoppingDetailModel.getProductName() != null)
//                ((BaseActivity) getActivity()).setTitle(shoppingDetailModel.getProductName());


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
        deliveryAddressImageView.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);
        recyclerViewImageDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shakeanimation);


        setProductDetails();

        presenter.setMultipleImagesItems(getActivity(), shoppingDetailModel.getImage());

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

    public void showSnackBarShortTime(String message) {
        snackUtil.showSnackBarShortTime(getView(), message);
    }

    @Override
    public void setUpdatedQuantity() {
        //  counterTextview.setText(String.valueOf(noOfProductsIntIncrement));
    }

    @Override
    public void showRecylerViewProductsImages(ProductsImagesAdapter productsImagesAdapter) {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImageDetails.setLayoutManager(horizontalLayoutManager);
        recyclerViewImageDetails.setAdapter(productsImagesAdapter);
    }

    @Override
    public void setSelectedImage(String clickedUrl) {
        if (!utils.isTextNullOrEmpty(clickedUrl)) {
            Glide.with(getActivity()).load(clickedUrl)
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgProduct);
        } else {
            imgProduct.setImageResource(R.drawable.placeholder);

        }
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

    // long noOfProductsIntIncrement = 1;
    // long noOfProductsIntDecrement = 1;

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

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(0);
                        }
                        return;
                    } else {

                        noOfProductsIntIncrement = noOfProductsIntIncrement + 1;
                        counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(noOfProductsIntIncrement);
                        }

                        //  presenter.updateItemCountInDB(String.valueOf(noOfProductsIntIncrement)
                        //        , productNameLabelTextView.getText().toString());


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

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
                        }

                        return;
                    } else {
                        if (noOfProductsIntDecrement == 1) {
                            counterTextview.setText(String.valueOf(noOfProductsIntDecrement));
                            if (shoppingDetailModel != null) {
                                shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
                            }
                            //   shoppingDetailModel.setItemQuantity(1);
                            // decrementCounter = 1;
                            noOfProductsIntDecrement = 1;

//                            presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
//                                    , productNameLabelTextView.getText().toString());
                            return;
                        }
                        noOfProductsIntDecrement = noOfProductsIntDecrement - 1;

                        //  presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
                        //        , productNameLabelTextView.getText().toString());
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
                        }


                    }

                } catch (Exception e) {

                }
                break;

            case R.id.favouriteButton:
                try {

                    if (isFavourite) {
                        showSnackBarShortTime("Add item to favourites.", getView());
                        isFavourite = false;

                    } else {
                        showSnackBarShortTime("Remove item from favourites.", getView());
                    }

                } catch (Exception e) {

                }
                break;
            case R.id.deliveryAddressImageView:

                Intent i = new Intent(getActivity(), DeliveryAddressActivity.class);
                getActivity().startActivity(i);


                break;
            case R.id.btnAddToCart:
                String productName = productNameLabelTextView.getText().toString();
                String price = priceTextView.getText().toString();
                String quantity = counterTextview.getText().toString();
//                String deliveryAddress1 = deliveryAddressValueTextView.getText().toString();
//                String deliveryAddress2 = deliveryAddress2ValueEdittext.getText().toString();
                String itemCutPrice = discountPriceTextView.getText().toString();

                long totalAmount = Long.valueOf(quantity) * Long.valueOf(price);
                //  latitude = sessionManager.getKeyLatitude();
                // longitude = sessionManager.getKeyLongitude();

                byte[] byteArray = new byte[0];
                if (bitmapImage != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    byteArray = stream.toByteArray();

                }
                AddToCart addToCart = new AddToCart("1", productName, String.valueOf(totalAmount), quantity, "N", byteArray, itemCutPrice, price);
                presenter.saveProductDetails(addToCart);

                break;
        }
    }

}
