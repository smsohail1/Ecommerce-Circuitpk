package com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.varunest.sparkbutton.SparkButton;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.home_delivery_Address.DeliveryAddressActivity;
import com.xekera.Ecommerce.ui.shop_card_selected.multiple_image_slider_view.MultipeImageSliderViewFragment;
import com.xekera.Ecommerce.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToCartShopCardSelectedFragment extends Fragment implements AddToCartShopCardSelectedMVP.View,
        View.OnClickListener {

    @BindView(R.id.imgProduct)
    protected ImageView imgProduct;
    @BindView(R.id.imgProductCopy)
    protected ImageView imgProductCopy;
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
    @BindView(R.id.availabilitStockTextView)
    protected TextView availabilitStockTextView;
    @BindView(R.id.productNameTextview)
    protected TextView productNameTextview;
    @BindView(R.id.avloadingIndicatorView)
    protected AVLoadingIndicatorView avloadingIndicatorView;
    @BindView(R.id.imgFullScreen)
    protected ImageView imgFullScreen;
    @BindView(R.id.linearLayoutDiscount)
    protected LinearLayout linearLayoutDiscount;


    @Inject
    protected AddToCartShopCardSelectedMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SnackUtil snackUtil;
    @Inject
    protected SessionManager sessionManager;
    @Inject
    protected AppDatabase appDatabase;

    public static final String KEY_SHOP_CARD_SELECTED_DETAILS = "cart_shop_card_selected_details";
    public static final String KEY_SHOP_CARD_SELECTED_PRODUCT_NAME = "cart_shop_card_selected_product_name";
    public static final String KEY_SHOP_CARD_SELECTED_PRICE = "cart_shop_card_selected_price";
    public static final String KEY_SHOP_CARD_SELECTED_CUT_PRICE = "cart_shop_card_selected_cut_price";
    public static final String KEY_SHOP_CARD_SELECTED_QUANTITY = "cart_shop_card_selected_quantity";
    public static final String KEY_SHOP_CARD_SELECTED_IMAGE_LIST = "cart_shop_card_selected_image_list";
    public static final String KEY_SHOP_CARD_SELECTED_IMAGE = "cart_shop_card_selected_image";
    public static final String KEY_SHOP_CARD_SELECTED_ABOUT = "cart_shop_card_selected_about";
    public static final String KEY_SHOP_CARD_SELECTED_SKU = "cart_shop_card_selected_sku";
    public static final String KEY_SHOP_CARD_SELECTED_PRODUCT_ID = "cart_shop_card_selected_product_id";


    ShoppingDetailModel shoppingDetailModel;
    String bitmapImage;
    String productName = "", price = "", cutPrice = "", quantity = "", about = "", sku = "", producdID = "";

    List<String> imgList;
    String imgListJson = "";


    long productsCartCounter = 0;
    boolean isFavourite = true;


    String latitude = "";
    String longitude = "";
    String placeName = "";

    Animation shake;
    View toastView;


    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    public AddToCartShopCardSelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        try {


            //  shoppingDetailModel = (ShoppingDetailModel) getArguments().getSerializable(KEY_SHOP_CARD_SELECTED_DETAILS);
            imgList = new ArrayList<>();


            productName = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRODUCT_NAME);
            price = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRICE);
            cutPrice = getArguments().getString(KEY_SHOP_CARD_SELECTED_CUT_PRICE);
            quantity = getArguments().getString(KEY_SHOP_CARD_SELECTED_QUANTITY);
            imgListJson = getArguments().getString(KEY_SHOP_CARD_SELECTED_IMAGE_LIST);

            JSONArray jsonArray = new JSONArray(imgListJson);

            for (int j = 0; j < jsonArray.length(); j++) {
                imgList.add(jsonArray.getString(j));
            }
            bitmapImage = getArguments().getString(KEY_SHOP_CARD_SELECTED_IMAGE);

            about = getArguments().getString(KEY_SHOP_CARD_SELECTED_ABOUT);
            sku = getArguments().getString(KEY_SHOP_CARD_SELECTED_SKU);
            producdID = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRODUCT_ID);

//            String bitmapStr = "";
//            bitmapStr = getArguments().getString(KEY_SHOP_CARD_SELECTED_IMAGE);
//            bitmapImage = stringToBitMap(bitmapStr);

        } catch (Exception e) {

        }
    }


    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        try {
            //  ((BaseActivity) getActivity()).hideBottomNavigation();
            //setProductDetails();

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

    byte[] byteArray;

    public void setProductDetails() {
        try {

//            if (shoppingDetailModel != null && shoppingDetailModel.getProductName() != null)
//                ((BaseActivity) getActivity()).setTitle(shoppingDetailModel.getProductName());

            if (!utils.isTextNullOrEmpty(productName)) {
                productNameLabelTextView.setText(productName);
            }


            if (!utils.isTextNullOrEmpty(productName)) {
                productNameTextview.setText(productName);
            }


            if (!utils.isTextNullOrEmpty(about)) {
                descriptionValueTextView.setText(Html.fromHtml(about.trim()));
            }

            if (!utils.isTextNullOrEmpty(sku)) {
                availabilitValueTextView.setText(sku);
            }

            if (!utils.isTextNullOrEmpty(price)) {
                priceTextView.setText(price);
            }

            if (!utils.isTextNullOrEmpty(cutPrice)) {
                if (cutPrice.equalsIgnoreCase("0")) {
                    discountPriceTextView.setText("0");
                    linearLayoutDiscount.setVisibility(View.GONE);
                } else if (!utils.isTextNullOrEmpty(cutPrice)) {
                    discountPriceTextView.setText(cutPrice);
                    linearLayoutDiscount.setVisibility(View.VISIBLE);
                }
            } else {
                discountPriceTextView.setText("0");
                linearLayoutDiscount.setVisibility(View.GONE);

            }


            // if (shoppingDetailModel != null) {
            // discountPriceTextView.setText(shoppingDetailModel.getProductPrice());
            //discountPriceTextView.setText("120");

            //}

            if (!utils.isTextNullOrEmpty(quantity)) {
                counterTextview.setText(quantity);
            }


            //if (bitmapImage != null) {
            //imgProduct.setImageBitmap(bitmapImage);
            if (imgList != null && imgList.size() > 0) {

                avloadingIndicatorView.setVisibility(View.VISIBLE);
                imgProduct.setVisibility(View.GONE);
                imgProductCopy.setVisibility(View.GONE);
                imgFullScreen.setVisibility(View.VISIBLE);

                Glide.with(getActivity())
                        .load(imgList.get(0))
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        // .override(130, 50)
                        .centerCrop()

                        // .into(homeViewHolder.imgHomeItem);
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                avloadingIndicatorView.setVisibility(View.GONE);
                                imgProduct.setImageBitmap(resource);
                                imgProduct.setVisibility(View.VISIBLE);
                                imgProductCopy.setImageBitmap(resource);
                                imgProductCopy.setVisibility(View.GONE);

                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                avloadingIndicatorView.setVisibility(View.GONE);
                                imgProduct.setVisibility(View.VISIBLE);

                            }
                        });


//                try {
//                    Observable.just(appDatabase)
//                            .map(new Function<AppDatabase, Boolean>() {
//                                @Override
//                                public Boolean apply(AppDatabase appDatabase) throws Exception {
//                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                                    byteArray = stream.toByteArray();
//
//
//                                    return true;
//                                }
//                            })
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(new Observer<Boolean>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(Boolean success) {
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                } catch (Exception ex) {
//                    Log.d("sd", ":fdd");
//                }

            }


            // if (bitmapImage != null)
            //   imgProductCopy.setImageBitmap(bitmapImage);


        } catch (
                Exception e) {

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
        try {


            ButterKnife.bind(this, v);
            presenter.setView(this);

            if (imgList.size() == 1) {
                recyclerViewImageDetails.setVisibility(View.GONE);
            } else {
                recyclerViewImageDetails.setVisibility(View.VISIBLE);

            }
            toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);
            // ((BaseActivity) getActivity()).hideBottomNavigation();

            incrementImageButton.setOnClickListener(this);
            decrementImageButton.setOnClickListener(this);
            deliveryAddressImageView.setOnClickListener(this);
            favouriteButton.setOnClickListener(this);
            btnAddToCart.setOnClickListener(this);
            imgFullScreen.setOnClickListener(this);
            progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);
            recyclerViewImageDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

            //   shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shakeanimation);

            setProductDetails();
            presenter.setIsFavourite(productName);

            presenter.setMultipleImagesItems(getActivity(), imgList);


        } catch (Exception e) {

        }
    }


    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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
        toastUtil.showToastShortTime(message, toastView);
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
        try {

            avloadingIndicatorView.setVisibility(View.VISIBLE);
            imgProduct.setVisibility(View.GONE);
            imgFullScreen.setVisibility(View.GONE);


            if (!utils.isTextNullOrEmpty(clickedUrl)) {
                try {
                    Glide.with(getActivity()).load(clickedUrl)
                            .asBitmap()
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder_error)

//                        .listener(new RequestListener<String, GlideDrawable>() {
//                            @Override
//                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                avloadingIndicatorView.setVisibility(View.GONE);
//                                imgProduct.setVisibility(View.VISIBLE);
//
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                avloadingIndicatorView.setVisibility(View.GONE);
//                                imgProduct.setVisibility(View.VISIBLE);
//                                return false;
//                            }
//                        })
//                        .into(imgProduct);

                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    avloadingIndicatorView.setVisibility(View.GONE);
                                    imgProduct.setImageBitmap(resource);
                                    imgProduct.setVisibility(View.VISIBLE);
                                    imgFullScreen.setVisibility(View.VISIBLE);

                                }

                                @Override
                                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                    super.onLoadFailed(e, errorDrawable);
                                    avloadingIndicatorView.setVisibility(View.GONE);
                                    imgProduct.setVisibility(View.VISIBLE);
                                    imgFullScreen.setVisibility(View.VISIBLE);


                                }
                            });


                } catch (Exception e) {

                }
            } else {
                avloadingIndicatorView.setVisibility(View.GONE);
                imgProduct.setVisibility(View.VISIBLE);
                imgProduct.setImageResource(R.drawable.placeholder);
                imgFullScreen.setVisibility(View.GONE);

            }

        } catch (Exception e) {

        }
    }

    @Override
    public void setCountZero(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);
    }

    @Override
    public void shakeAddToCartTextview() {
        //  ((BaseActivity) getActivity()).AnimateCartTextview();

    }

    @Override
    public void enableAddtoFavouriteButton() {
        favouriteButton.setEnabled(true);
    }

    @Override
    public void animateFavouriteButton() {
        favouriteButton.setChecked(false);
        // favouriteButton.playAnimation();
        // favouriteButton.setInactiveImage(R.drawable.heart);

    }

    @Override
    public void animationAddButton() {
        favouriteButton.setChecked(true);
        // favouriteButton.playAnimation();
        // favouriteButton.setActiveImage(R.drawable.icon_heart_on);

    }

    @Override
    public void setIsFavourite(boolean isFavourite) {
        favouriteButton.setChecked(isFavourite);
    }

    @Override
    public void setCartCounterTextview(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }


    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }


//    public ShopCardSelectedFragment newInstance(ShoppingDetailModel shoppingDetailModel, Bitmap bitmapImg) {
//        ShopCardSelectedFragment fragment = null;
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(KEY_SHOP_CARD_SELECTED_DETAILS, shoppingDetailModel);
//            bundle.putParcelable(KEY_SHOP_CARD_SELECTED_IMAGE, bitmapImg);
//            fragment = new ShopCardSelectedFragment();
//            fragment.setArguments(bundle);
//            return fragment;
//        } catch (Exception e) {
//            return fragment;
//        }
//    }

    public AddToCartShopCardSelectedFragment newInstance(String productName, String price, String cutPrice, String quantity,
                                                         String img, String imgList, String productID, String about,
                                                         String sku) {
        AddToCartShopCardSelectedFragment fragment = null;
        try {


            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRODUCT_NAME, productName);
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRICE, price);
            bundle.putString(KEY_SHOP_CARD_SELECTED_CUT_PRICE, cutPrice);
            bundle.putString(KEY_SHOP_CARD_SELECTED_QUANTITY, quantity);
            bundle.putString(KEY_SHOP_CARD_SELECTED_IMAGE_LIST, imgList);
            bundle.putString(KEY_SHOP_CARD_SELECTED_IMAGE, img);
            bundle.putString(KEY_SHOP_CARD_SELECTED_ABOUT, about);
            bundle.putString(KEY_SHOP_CARD_SELECTED_SKU, sku);
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRODUCT_ID, productID);

            fragment = new AddToCartShopCardSelectedFragment();
            fragment.setArguments(bundle);
            return fragment;
        } catch (Exception e) {
            return fragment;
        }
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
                    if (utils.isTextNullOrEmpty(noOfProductsIncrement) || noOfProductsIntIncrement < 0) {
                        showToastShortTime("Product not available");
                        //  shoppingDetailModel.setItemQuantity(0);
                        noOfProductsIntIncrement = 0;
                        counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

//                        if (shoppingDetailModel != null) {
//                            shoppingDetailModel.setItemQuantity(0);
//                        }
                        return;
                    } else {
                        try {


                            noOfProductsIntIncrement = noOfProductsIntIncrement + 1;
                            counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

//                            if (shoppingDetailModel != null) {
//                                shoppingDetailModel.setItemQuantity(noOfProductsIntIncrement);
//                            }

//                            long itemQuantity = Long.valueOf(counterTextview.getText().toString());
//                            long productPrice = Long.valueOf(priceTextView.getText().toString());
//                            long totalPrice = itemQuantity * productPrice;
//                            presenter.onIncrementButtonClicked(itemQuantity,
//                                    productPrice, totalPrice, productNameLabelTextView.getText().toString(),
//                                    discountPriceTextView.getText().toString(), byteArray, imgProductCopy);
//

                            // ((BaseActivity) getActivity()).makeFlyAnimation(imgProductCopy, 0);

                            //((BaseActivity) getActivity()).addItemToCart(cartsCount);

                            //  presenter.updateItemCountInDB(String.valueOf(noOfProductsIntIncrement)
                            //        , productNameLabelTextView.getText().toString());

                        } catch (Exception e) {

                        }
                    }

                } catch (Exception e) {

                }
                break;
            case R.id.decrementImageButton:
                try {
                    String noOfProductsDecrement = counterTextview.getText().toString();
                    long noOfProductsIntDecrement = Long.valueOf(noOfProductsDecrement);
                    if (utils.isTextNullOrEmpty(noOfProductsDecrement) || noOfProductsIntDecrement < 0) {
                        // showToastShortTime("Product not available");
                        //  shoppingDetailModel.setItemQuantity(0);
                        //decrementCounter = 0;
                        noOfProductsIntDecrement = 0;
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));

//                        if (shoppingDetailModel != null) {
//                            shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
//                        }

                        return;
                    } else {
                        if (noOfProductsIntDecrement == 0) {
                            counterTextview.setText(String.valueOf(noOfProductsIntDecrement));
//                            if (shoppingDetailModel != null) {
//                                shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
//                            }

                            //   shoppingDetailModel.setItemQuantity(1);
                            // decrementCounter = 1;
                            noOfProductsIntDecrement = 0;

//                            presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
//                                    , productNameLabelTextView.getText().toString());
                            showToastShortTime("Please select atleast one quantity.");
                            return;
                        }
                        noOfProductsIntDecrement = noOfProductsIntDecrement - 1;

                        //  presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
                        //        , productNameLabelTextView.getText().toString());
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));

//                        if (shoppingDetailModel != null) {
//                            shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
//                        }

//                        long itemQuantity = Long.valueOf(counterTextview.getText().toString());
//                        long productPrice = Long.valueOf(priceTextView.getText().toString());
//                        long totalPrice = itemQuantity * productPrice;

//                        presenter.onIncrementButtonClicked(itemQuantity,
//                                productPrice, totalPrice, productNameLabelTextView.getText().toString(),
//                                discountPriceTextView.getText().toString(), byteArray, imgProductCopy);


                    }

                } catch (Exception e) {

                }
                break;

            case R.id.favouriteButton:
                try {

//                    if (isFavourite) {
//                        showSnackBarShortTime("Add item to favourites.", getView());
//                        isFavourite = false;
//
//                    } else {
//                        showSnackBarShortTime("Remove item from favourites.", getView());
//                    }
                    String quantity = counterTextview.getText().toString();

//                    if (utils.isTextNullOrEmptyOrZero(quantity)) {
//                        quantity = "1";
//                    }
                    boolean favourite = false;
                    if (favouriteButton.isChecked()) {
                        favourite = true;
                    } else {
                        favourite = false;
                    }

                    //  favouriteButton.setChecked(true);
                    favouriteButton.playAnimation();
                    favouriteButton.setEnabled(false);

                    String itemName = productNameLabelTextView.getText().toString();
                    String itemIndividualPrice = priceTextView.getText().toString();
                    String itemCutPrice = discountPriceTextView.getText().toString();
                    String availabilityInStock = availabilitStockTextView.getText().toString();
                    long totalPrice = Long.valueOf(itemIndividualPrice) * Long.valueOf(quantity);

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    String descStr = "";
                    if (!utils.isTextNullOrEmpty(about)) {
                        descStr = Html.fromHtml(about.trim()).toString();
                    } else {
                        descStr = "";
                    }

                    String jsonObjectImg = new Gson().toJson(imgList);

                    Favourites favourites;

                    if (imgList != null && imgList.size() > 0) {

                        favourites = new Favourites(itemName, itemIndividualPrice, itemCutPrice,
                                availabilityInStock, formattedDate,
                                byteArray, quantity, String.valueOf(totalPrice), imgList.get(0), producdID, "0",
                                descStr, jsonObjectImg
                        );
                    } else {

                        favourites = new Favourites(itemName, itemIndividualPrice, itemCutPrice,
                                availabilityInStock, formattedDate,
                                byteArray, quantity, String.valueOf(totalPrice), imgList.get(0), producdID, "0",
                                descStr, jsonObjectImg
                        );
                    }

                    presenter.addItemToFavourites(favourites, favourite);


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

                String formattedDate = "";
                formattedDate = getCurrentDate();

                //  latitude = sessionManager.getKeyLatitude();
                // longitude = sessionManager.getKeyLongitude();

//                byte[] byteArray = new byte[0];
//                if (bitmapImage != null) {
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                    byteArray = stream.toByteArray();
//
//                }

                AddToCart addToCart;
                String descString = "";
                if (!utils.isTextNullOrEmpty(about)) {
                    descString = Html.fromHtml(about.trim()).toString();
                } else {
                    descString = "";
                }
                String jsonObjectImg = new Gson().toJson(imgList);


                if (imgList != null && imgList.size() > 0) {

                    // String str = (String) Arrays.toString(new List[]{imgList});


                    addToCart = new AddToCart("", productName, String.valueOf(totalAmount), quantity,
                            "N", byteArray, itemCutPrice, price, formattedDate, imgList.get(0), producdID,
                            "0", descString, jsonObjectImg);

                } else {

                    addToCart = new AddToCart("", productName, String.valueOf(totalAmount), quantity,
                            "N", byteArray, itemCutPrice, price, formattedDate, imgList.get(0), producdID,
                            "0", descString, jsonObjectImg);
                }
                presenter.saveProductDetails(addToCart);

                break;

            case R.id.imgFullScreen:
                try {

                    if (imgList != null && imgList.size() > 0) {
                        ((BaseActivity) getActivity()).replaceFragment(new MultipeImageSliderViewFragment().newInstance(imgList));
                        //  multipeImageSliderViewFragment.newInstance(imgList);
                    } else {
                        showToastShortTime("No image found");
                    }

                } catch (Exception e) {

                }
                break;
        }
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
