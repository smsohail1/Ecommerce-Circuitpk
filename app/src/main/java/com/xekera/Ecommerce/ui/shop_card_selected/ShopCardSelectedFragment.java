package com.xekera.Ecommerce.ui.shop_card_selected;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.AddToCartAdapter;
import com.xekera.Ecommerce.ui.adapter.ProductsImagesAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.home_delivery_Address.DeliveryAddressActivity;
import com.xekera.Ecommerce.util.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCardSelectedFragment extends Fragment implements ShopCardSelectedMVP.View, View.OnClickListener {

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
    @Inject
    protected AppDatabase appDatabase;

    public static final String KEY_SHOP_CARD_SELECTED_DETAILS = "shop_card_selected_details";
    public static final String KEY_SHOP_CARD_SELECTED_PRODUCT_NAME = "shop_card_selected_product_name";
    public static final String KEY_SHOP_CARD_SELECTED_PRICE = "shop_card_selected_price";
    public static final String KEY_SHOP_CARD_SELECTED_CUT_PRICE = "shop_card_selected_cut_price";
    public static final String KEY_SHOP_CARD_SELECTED_QUANTITY = "shop_card_selected_quantity";
    public static final String KEY_SHOP_CARD_SELECTED_IMAGE_LIST = "shop_card_selected_image_list";
    public static final String KEY_SHOP_CARD_SELECTED_IMAGE = "shop_card_selected_image";

    ShoppingDetailModel shoppingDetailModel;
    Bitmap bitmapImage;
    String productName = "", price = "", cutPrice = "", quantity = "";
    List<String> imgList;


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
        try {


            //  shoppingDetailModel = (ShoppingDetailModel) getArguments().getSerializable(KEY_SHOP_CARD_SELECTED_DETAILS);

            imgList = new ArrayList<>();
            productName = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRODUCT_NAME);
            price = getArguments().getString(KEY_SHOP_CARD_SELECTED_PRICE);
            cutPrice = getArguments().getString(KEY_SHOP_CARD_SELECTED_CUT_PRICE);
            quantity = getArguments().getString(KEY_SHOP_CARD_SELECTED_QUANTITY);
            imgList = getArguments().getStringArrayList(KEY_SHOP_CARD_SELECTED_IMAGE_LIST);

            bitmapImage = getArguments().getParcelable(KEY_SHOP_CARD_SELECTED_IMAGE);

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

            if (bitmapImage != null) {
                imgProduct.setImageBitmap(bitmapImage);
//                Glide.with(getActivity())
//                        .load(byteArray)
//                        .asBitmap()
//                        .placeholder(R.drawable.placeholder)
//                        .into(imgProduct);


                try {
                    Observable.just(appDatabase)
                            .map(new Function<AppDatabase, Boolean>() {
                                @Override
                                public Boolean apply(AppDatabase appDatabase) throws Exception {
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byteArray = stream.toByteArray();


                                    return true;
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Boolean success) {
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } catch (Exception ex) {
                }

            }


            // if (bitmapImage != null)
            //   imgProductCopy.setImageBitmap(bitmapImage);


            if (!utils.isTextNullOrEmpty(price)) {
                priceTextView.setText(price);
            }

            // if (shoppingDetailModel != null) {
            // discountPriceTextView.setText(shoppingDetailModel.getProductPrice());
            discountPriceTextView.setText("120");

            //}

            if (!utils.isTextNullOrEmpty(quantity)) {
                counterTextview.setText(quantity);
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
        try {


            ButterKnife.bind(this, v);
            presenter.setView(this);

            // ((BaseActivity) getActivity()).hideBottomNavigation();

            incrementImageButton.setOnClickListener(this);
            decrementImageButton.setOnClickListener(this);
            deliveryAddressImageView.setOnClickListener(this);
            favouriteButton.setOnClickListener(this);
            btnAddToCart.setOnClickListener(this);
            progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);
            recyclerViewImageDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

            //   shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shakeanimation);


            setProductDetails();
            presenter.setIsFavourite(productName);

            presenter.setMultipleImagesItems(getActivity(), imgList);

        } catch (Exception e) {

        }
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
        try {


            if (!utils.isTextNullOrEmpty(clickedUrl)) {
                Glide.with(getActivity()).load(clickedUrl)
                        .asBitmap()
//                    .fitCenter()
//                    .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imgProduct);

//                Glide.with(getActivity()).load(clickedUrl)
////                    .fitCenter()
////                    .centerCrop()
//                        .placeholder(R.drawable.placeholder)
//                        .error(R.drawable.placeholder)
//                        .into(imgProductCopy);
            } else {
                Glide.with(getActivity())
                        .load(R.drawable.placeholder)
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imgProduct);

                //  imgProduct.setImageResource(R.drawable.placeholder);

                //  imgProductCopy.setImageResource(R.drawable.placeholder);

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

    public ShopCardSelectedFragment newInstance(String productName, String price, long cutPrice, long quantity,
                                                List<String> imgList, Bitmap bitmapImg) {
        ShopCardSelectedFragment fragment = null;
        try {


            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRODUCT_NAME, productName);
            bundle.putString(KEY_SHOP_CARD_SELECTED_PRICE, price);
            bundle.putString(KEY_SHOP_CARD_SELECTED_CUT_PRICE, String.valueOf(cutPrice));
            bundle.putString(KEY_SHOP_CARD_SELECTED_QUANTITY, String.valueOf(quantity));
            bundle.putStringArrayList(KEY_SHOP_CARD_SELECTED_IMAGE_LIST, (ArrayList<String>) imgList);
            bundle.putParcelable(KEY_SHOP_CARD_SELECTED_IMAGE, bitmapImg);
            fragment = new ShopCardSelectedFragment();
            fragment.setArguments(bundle);
            return fragment;
        } catch (Exception e) {
            return fragment;
        }
    }


    public ShopCardSelectedFragment newInstance(ShoppingDetailModel shoppingDetailModel, String bitmapImg) {
        Bundle bundle = new Bundle();
        // bundle.putSerializable(KEY_SHOP_CARD_SELECTED_DETAILS, shoppingDetailModel);
        bundle.putString(KEY_SHOP_CARD_SELECTED_IMAGE, bitmapImg);
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
                    if (utils.isTextNullOrEmpty(noOfProductsIncrement) || noOfProductsIntIncrement < 0) {
                        snackUtil.showSnackBarShortTime(view, "Product not available");
                        //  shoppingDetailModel.setItemQuantity(0);
                        noOfProductsIntIncrement = 0;
                        counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(0);
                        }
                        return;
                    } else {
                        try {


                            noOfProductsIntIncrement = noOfProductsIntIncrement + 1;
                            counterTextview.setText(String.valueOf(noOfProductsIntIncrement));

                            if (shoppingDetailModel != null) {
                                shoppingDetailModel.setItemQuantity(noOfProductsIntIncrement);
                            }

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
                        if (noOfProductsIntDecrement == 0) {
                            counterTextview.setText(String.valueOf(noOfProductsIntDecrement));
                            if (shoppingDetailModel != null) {
                                shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
                            }
                            //   shoppingDetailModel.setItemQuantity(1);
                            // decrementCounter = 1;
                            noOfProductsIntDecrement = 0;

//                            presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
//                                    , productNameLabelTextView.getText().toString());
                            snackUtil.showSnackBarShortTime(view, "Please select atleast one quantity.");
                            return;
                        }
                        noOfProductsIntDecrement = noOfProductsIntDecrement - 1;

                        //  presenter.updateItemCountInDB(String.valueOf(noOfProductsIntDecrement)
                        //        , productNameLabelTextView.getText().toString());
                        counterTextview.setText(String.valueOf(noOfProductsIntDecrement));

                        if (shoppingDetailModel != null) {
                            shoppingDetailModel.setItemQuantity(noOfProductsIntDecrement);
                        }

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

                    if (utils.isTextNullOrEmptyOrZero(quantity)) {
                        quantity = "1";
                    }
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

                    String formattedDate = "";
                    formattedDate = getCurrentDate();

                    Favourites favourites = new Favourites(itemName, itemIndividualPrice, itemCutPrice,
                            availabilityInStock, formattedDate,
                            byteArray, quantity);
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
                AddToCart addToCart = new AddToCart("1", productName, String.valueOf(totalAmount), quantity,
                        "N", byteArray, itemCutPrice, price, formattedDate);
                presenter.saveProductDetails(addToCart);

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
