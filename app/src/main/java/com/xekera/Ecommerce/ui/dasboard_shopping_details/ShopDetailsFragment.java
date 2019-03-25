package com.xekera.Ecommerce.ui.dasboard_shopping_details;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.varunest.sparkbutton.SparkButton;
import com.wang.avi.AVLoadingIndicatorView;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.response.Product;
import com.xekera.Ecommerce.data.rest.response.ProductResponse;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.ShopDetailsAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.util.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;
import static com.xekera.Ecommerce.ui.favourites.FavouritesFragment.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailsFragment extends Fragment implements ShopDetailsMVP.View, ShopDetailsAdapter.IShopDetailAdapter,
        View.OnClickListener {

    @BindView(R.id.edtSearchProduct)
    protected EditText edtSearchProduct;
    @BindView(R.id.recyclerViewProductDetails)
    protected RecyclerView recyclerViewProductDetails;
    @BindView(R.id.progressbar)
    protected ProgressBar progressbar;
    @BindView(R.id.searchParent)
    protected RelativeLayout searchParent;
    @BindView(R.id.allData)
    protected LinearLayout allData;
    @BindView(R.id.clearText)
    protected ImageView clearText;

    @Inject
    protected ShopDetailsMVP.Presenter presenter;
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

    boolean isProgressBarShowing = false;

    public static final String KEY_SHOP_NAME_DETAILS = "shop_details_name";


    ShopDetailsAdapter shopDetailsAdapter;

    List<ShoppingDetailModel> shopDetails;
    String productName = "";

    private ProgressCustomDialogController progressDialogControllerPleaseWait;

    List<Favourites> favList;
    View toastView;


    public ShopDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        productName = getArguments().getString(KEY_SHOP_NAME_DETAILS, "");


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        //presenter.getFavouritesList();

        //  ((BaseActivity) getActivity()).hideBottomNavigation();

//
//        List<String> img;
//        img = new ArrayList<>();
//        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        img.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
//        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
//        img.add("https://dzvfs5sz5rprz.cloudfront.net/media/catalog/product/cache/1/image/1200x/9df78eab33525d08d6e5fb8d27136e95/m/e/mega_shop_hidden_gsm_supported_voice_recorder_black.jpg");
//        img.add("https://dzvfs5sz5rprz.cloudfront.net/media/catalog/product/cache/1/image/1200x/9df78eab33525d08d6e5fb8d27136e95/m/e/mega_shop_usb_range_extender_black.jpg");
//        img.add("https://www.bhphotovideo.com/images/images2000x2000/kingston_dt100g3_16gb_16gb_data_traveler_100_964342.jpg");
//        img.add("https://a.pololu-files.com/picture/0J1479.1200.jpg?6d28c13f103617525228f0936ec16321");
//
//        edtSearchProduct.setText("");
//        shopDetails.clear();
//        final byte[][] byteArray = {new byte[0]};
//
//
//        try {
//            Observable.just(appDatabase)
//                    .map(new Function<AppDatabase, Boolean>() {
//                        @Override
//                        public Boolean apply(AppDatabase appDatabase) throws Exception {
//
//                            Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
//                                    R.drawable.ardino);
//
//                            if (icon != null) {
//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                                byteArray[0] = stream.toByteArray();
//
//                            }
//                            return true;
//                        }
//                    })
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<Boolean>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Boolean success) {
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } catch (Exception ex) {
//        }
//
//
//        shopDetails.add(new ShoppingDetailModel("arduino", "5000", false, 0, img,
//                byteArray[0], 30, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Resberi Pi", "10000", false, 0, img,
//                byteArray[0], 10, favList));
//
//        shopDetails.add(new ShoppingDetailModel("LED", "300", false, 0, img, byteArray[0],
//                40, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Jumper Wire", "800", false, 0, img,
//                byteArray[0], 33, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Bread Board", "200", false, 0, img,
//                byteArray[0], 54, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Telivision", "30000", false, 0,
//                img, byteArray[0], 40, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Seven Segment", "800", false, 0, img,
//                byteArray[0], 33, favList));
//
//        shopDetails.add(new ShoppingDetailModel("Capacitor", "200", false, 0,
//                img, byteArray[0], 54, favList));
//
//
//        shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this);
//        showRecylerViewProductsDetail(shopDetailsAdapter);
//
//        presenter.setActionListener(new ShopDetailsPresenter.ProductItemActionListener() {
//            @Override
//            public void onItemTap(ImageView imageView, int cartsCount) {
//                if (imageView != null) {
//                    ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
//                    ((BaseActivity) getActivity()).addItemToCart(cartsCount);
//
//                }
//            }
//        });


        try {
//            setTitle();
//            //   showBackImageIcon();
//            // hideHumbergIcon();
//            showBackImageIcon();
//            //hideActionBar();
//            hideLoginIcon();

//            setTitle();
//            hideHumbergIcon();
//            showBackImageIcon();
//            hideLoginIcon();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(productName);
    }


    public void hideLoginIcon() {
        ((BaseActivity) getActivity()).hideLoginIcon();
    }


    public void hideHumbergIcon() {
        ((BaseActivity) getActivity()).hideHumberIcon();

    }

    public void showBackImageIcon() {
        ((BaseActivity) getActivity()).showBackImageIcon();

    }


    public void hideActionBar() {
        ((BaseActivity) getActivity()).hideActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_details, container, false);

        initializeViews(v);


        return v;
    }

    private String getCurrentDateTime() {
        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT_FOUR);
            return df.format(c.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        //  ((BaseActivity) getActivity()).hideBottomNavigation();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

        recyclerViewProductDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        String dataTime = getCurrentDateTime();
        if (utils.isTextNullOrEmpty(sessionManager.getKeyRandomKey())) {
            sessionManager.setKeyRandomKey("mobile" + dataTime);
        }
        //  setTitle();
        //hideLoginIcon();
        // showBackImageIcon();

        edtSearchProduct.setText("");

        clearText.setOnClickListener(this);

        shopDetails = new ArrayList<ShoppingDetailModel>();

        isProgressBarShowing = true;
        hideData();
        if (utils.isInternetAvailable()) {
            progressbar.setVisibility(View.VISIBLE);

            //presenter.setDashboardItems(getActivity());
            if (!utils.isTextNullOrEmpty(productName)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.setProductItemsDetails(getActivity(), productName);

                    }
                }, 600);
            } else {
                showToastShortTime("Product name not available");
            }

//            try {
//                isProgressBarShowing = true;
//
//                List<String> img;
//                img = new ArrayList<>();
//                img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//
//                edtSearchProduct.setText("");
//                shopDetails.clear();
//                final byte[][] byteArray = {new byte[0]};
//
//
//                List<String> tasksList;
//                Set<String> list = sessionManager.getIsFavouriteList();
//                tasksList = new ArrayList<String>(list);
//
//
//                shopDetails.add(new ShoppingDetailModel("Arduino", "5000", false, 0, img,
//                        byteArray[0], 30, favList, R.drawable.arduino_detail));
//
//
//                shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this, sessionManager, tasksList);
//                showRecylerViewProductsDetail(shopDetailsAdapter);
//                isProgressBarShowing = false;
//                presenter.setActionListener(new ShopDetailsPresenter.ProductItemActionListener() {
//                    @Override
//                    public void onItemTap(ImageView imageView, int cartsCount) {
//                        if (imageView != null) {
//                            ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
//                            ((BaseActivity) getActivity()).addItemToCart(cartsCount);
//
//                        }
//                    }
//                });
//
//            } catch (Exception e) {
//
//            }

            //presenter.getTotalCounts();
        } else {
            showToastShortTime("Please connect to internet.");
        }
        //((BaseActivity) getActivity()).uncheckHomeScreen();


//        shopDetails.add(new ShoppingDetailModel("arduino", "5000", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Resberi Pi", "10000", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("LED", "300", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Jumper Wire", "800", false, 1));
//
//        shopDetails.add(new ShoppingDetailModel("Bread Board", "200", false, 1));
//
//        shopDetails.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        shopDetails.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");

        // shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this);
        //showRecylerViewProductsDetail(shopDetailsAdapter);


        //  presenter.setRecylerViewItems(getActivity(), shopDetails);
        //shopDetailsAdapter = new ShopDetailsAdapter();


        edtSearchProduct.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                try {

                    if (!isProgressBarShowing) {
                        String text = edtSearchProduct.getText().toString().toLowerCase(Locale.getDefault()).trim();

                        shopDetailsAdapter.filter(text);
                    }

                } catch (Exception ex) {

                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

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

    @Override
    public void showSnackBarLongTime(String message, View view) {
        snackUtil.showSnackBarLongTime(view, message);
    }

    @Override
    public void showRecylerViewProductsDetail(ShopDetailsAdapter shopDetailsAdapter) {
        recyclerViewProductDetails.setAdapter(shopDetailsAdapter);


    }

    @Override
    public void addToCart(Product productItems, ImageView imgProductCopy) {
        presenter.addToCartApi(productItems.getId(), "1", productItems.getPrice(), productItems.getRegularPrice(),
                sessionManager.getKeyRandomKey(), imgProductCopy
        );

    }

    @Override
    public void setCountZero(int counts) {
        ((BaseActivity) getActivity()).addItemToCart(0);

    }

    @Override
    public void setDecrementCount(int counts) {
        ((BaseActivity) getActivity()).removeItemToCart(counts);

    }

    @Override
    public void showSnackBarShortTime(String message) {
        showSnackBarShortTime(message, getView());
    }

    @Override
    public void setFavouriteButtonStatus(boolean status, int position) {
        shopDetailsAdapter.removeAll(status, position);
    }

    @Override
    public void setFavouriteList(List<Favourites> favourites) {
        favList = favourites;
        setUI(favList);
    }

    @Override
    public void setUI(List<Favourites> favList) {

        try {
          /*  isProgressBarShowing = true;

            List<String> img;
            img = new ArrayList<>();
            img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
            img.add("https://core-electronics.com.au/media/catalog/product/cache/1/image/fe1bcd18654db18f328c2faaaf3c690a/a/r/arduinouno-tri-org_2.jpg");
            // img.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
            img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
            img.add("https://cdn.shopify.com/s/files/1/0243/7593/products/DSC_5527_HR_web_grande.jpg?v=1476222056");
            img.add("https://cdn.instructables.com/FVR/H9FV/J1WW2UOV/FVRH9FVJ1WW2UOV.RECT600.jpg");
            img.add("https://cdn.solarbotics.com/products/photos/fd9395aa7662bc7c459a4ddcb586f24e/ARDX-IMG_4690.jpg");
            img.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSRL9InyGqcA_0QjvAy5XDWOR2jgV0ldNqAt1Yvk4FZvzbYHNM-7g");

            edtSearchProduct.setText("");
            shopDetails.clear();
            final byte[][] byteArray = {new byte[0]};


            try {
                Observable.just(appDatabase)
                        .map(new Function<AppDatabase, Boolean>() {
                            @Override
                            public Boolean apply(AppDatabase appDatabase) throws Exception {

                                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                                        R.drawable.ardino);

                                if (icon != null) {
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                    icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                    byteArray[0] = stream.toByteArray();

                                }
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

            List<String> tasksList;
            Set<String> list = sessionManager.getIsFavouriteList();
            tasksList = new ArrayList<String>(list);


            shopDetails.add(new ShoppingDetailModel("Arduino", "5000", false, 0, img,
                    byteArray[0], 30, favList, R.drawable.arduino_detail));

            shopDetails.add(new ShoppingDetailModel("Sensor Module", "10000", false, 0, img,
                    byteArray[0], 10, favList, R.drawable.detail_sensor_module));

            shopDetails.add(new ShoppingDetailModel("Battery", "300", false, 0, img, byteArray[0],
                    40, favList, R.drawable.details_battery));

            shopDetails.add(new ShoppingDetailModel("Jumper Wire", "600", false, 0, img,
                    byteArray[0], 33, favList, R.drawable.detail_wire));

            shopDetails.add(new ShoppingDetailModel("Rectifier", "500", false, 0, img,
                    byteArray[0], 54, favList, R.drawable.details_rectifier));

            //  shopDetails.add(new ShoppingDetailModel("Telivision", "30000", false, 0,
            //        img, byteArray[0], 40, favList));

            //shopDetails.add(new ShoppingDetailModel("Seven Segment", "800", false, 0, img,
            //      byteArray[0], 33, favList));

            //shopDetails.add(new ShoppingDetailModel("Capacitor", "200", false, 0,
            //      img, byteArray[0], 54, favList));


            shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), shopDetails, this, sessionManager, tasksList);
            showRecylerViewProductsDetail(shopDetailsAdapter);
            isProgressBarShowing = false;
            presenter.setActionListener(new ShopDetailsPresenter.ProductItemActionListener() {
                @Override
                public void onItemTap(ImageView imageView, int cartsCount) {
                    if (imageView != null) {
                        ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
                        ((BaseActivity) getActivity()).addItemToCart(cartsCount);

                    }
                }
            });
*/
        } catch (Exception e) {

        }

    }


    @Override
    public void InternetError() {
        toastUtil.showToastShortTime("Please connect to internet", toastView);
    }

    @Override
    public void onFavouriteBtnClickOnSever(String productId, String name) {
        presenter.addToFavouritesServer(productId, sessionManager.getusername(), sessionManager.getEmail(), name);

    }

    @Override
    public void showError(String errorMsg) {
        showToastShortTime(errorMsg);

    }


    @Override
    public void setIsFavourites(boolean isFavourites, int position) {
        shopDetailsAdapter.setIsfavourite(isFavourites, position);
    }


    public ShopDetailsFragment newInstance(String ProductName) {
        ShopDetailsFragment fragment = null;
        try {

            Bundle bundle = new Bundle();
            bundle.putString(KEY_SHOP_NAME_DETAILS, ProductName);
            fragment = new ShopDetailsFragment();
            fragment.setArguments(bundle);

            return fragment;
        } catch (Exception e) {

        }
        return fragment;
    }

    @Override
    public void onAddButtonClick(Product productItems) {

    }

    @Override
    public void onViewDetailsButtonClick(Product productItems) {

    }


    @Override
    public void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                       ImageView imgProductCopy, Bitmap bitmap, String imgUrl, String productID,
                                       String isEmailSent, String productDesc, List<String> imgArrList, String nameSku) {

        presenter.saveProductDetails(quantity, price, totalPrice, productName, cutPrice, imgProductCopy, bitmap, imgUrl,
                productID, isEmailSent, productDesc, imgArrList, nameSku);
    }

    @Override
    public void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                       ImageView imgProductCopy, Bitmap bitmapAdd, String imgUrl, String productID,
                                       String isEmailSent, String productDesc, List<String> imgArrList, String nameSku) {

        presenter.saveProductDecrementDetails(quantity, price, totalPrice, productName, cutPrice, imgProductCopy, bitmapAdd,
                imgUrl, productID, isEmailSent, productDesc, imgArrList, nameSku);

    }

    @Override
    public void onFavouriteButtonClick(Product productItems, int position, Bitmap bitmap, String quantity, String imgUrl,
                                       String productID, String isEmailFav, String productDesc, List<String> imgArrList,
                                       String nameSku) {

        presenter.isAlreadyAddedInFavourites(productItems, position, bitmap, quantity, imgUrl, productID,
                isEmailFav, productDesc, imgArrList, nameSku);

     /*   if (!productItems.isFavourite()) {
            presenter.removeItem(productItems.getProductName(), position);
        } else {
            //    showSnackBarShortTime("Remove item from favourites.", getView());

            byte[] bmp = bitmapToByteArray(bitmap);

            long totalPrice = Long.valueOf(productItems.getProductPrice()) * Long.valueOf(productItems.getItemQuantity());

            String formattedDate = "";
            formattedDate = getCurrentDate();
            Favourites favourites;
            if (productItems.getItemQuantity() == 0) {
                favourites = new Favourites(productItems.getProductName(), productItems.getProductPrice(),
                        String.valueOf(productItems.getCutPrice()), "In Stock", formattedDate,
                        bmp, "0", String.valueOf(totalPrice));
            } else {
                favourites = new Favourites(productItems.getProductName(), productItems.getProductPrice(),
                        String.valueOf(productItems.getCutPrice()), "In Stock", formattedDate,
                        bmp, String.valueOf(productItems.getItemQuantity()), String.valueOf(totalPrice));
            }

            presenter.addItemToFavourites(favourites, true);
        }
*/
    }

    private byte[] bitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
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

//    @Override
//    public void onIncrementButtonClick(ShoppingDetailModel productItems) {
//
//    }


//    @Override
//    public void onCardClick(final ShoppingDetailModel productItems, final Bitmap bitmapImg) {
//        utils.hideSoftKeyboard(edtSearchProduct);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                ShopCardSelectedFragment shopCardSelectedFragment = new ShopCardSelectedFragment();
//                ((BaseActivity) getActivity()).replaceFragment(shopCardSelectedFragment.newInstance(productItems, bitmapImg));
//            }
//        }, 200);
//
//    }


    @Override
    public void onCardClick(final String productName, final String price, final long cutPrice, final long quantity,
                            final List<String> imgList, final Bitmap bitmapImg, final String about, final String sku,
                            final String productID, final String nameSku) {
        utils.hideSoftKeyboard(edtSearchProduct);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ShopCardSelectedFragment shopCardSelectedFragment = new ShopCardSelectedFragment();

                ((BaseActivity) getActivity()).replaceFragmentForActivityTranstion(shopCardSelectedFragment.newInstance(productName,
                        price, cutPrice, quantity, imgList, bitmapImg, about, sku, productID, nameSku));
            }
        }, 200);

    }

    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = android.util.Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    boolean isShareButtonClick = true;

    @Override
    public void shareItemsDetails(final Product productItems, Bitmap bitmapImg) {

        try {

            if (isShareButtonClick) {
                isShareButtonClick = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showShareDialog(getActivity(), productItems);
                        isShareButtonClick = true;
                    }
                }, 300);
            }

//                Intent share = new Intent(Intent.ACTION_SEND);
//                // share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                share.setType("image/jpeg");
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, "title");
//                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//                Uri uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        values);
//
//                OutputStream outstream;
//                try {
//                    outstream = getActivity().getContentResolver().openOutputStream(uri);
//                    bitmapImg.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
//                    outstream.close();
//                } catch (Exception e) {
//                    System.err.println(e.toString());
//                }
//
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//                share.putExtra(Intent.EXTRA_TEXT, "Product Name: " + productItems.getProductName() + "\n" +
//                        "Price: " + productItems.getProductPrice());
//                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                startActivity(Intent.createChooser(share, "Share with friends"));

            //  shareOnFacebook();
            // shareLinks();

            // shareOnFacebookMessenger();

        } catch (Exception e) {
            Log.d("error", e.getMessage());
            //showMissingPermissionError();
        }

    }


    private void showShareDialog(Context context, final Product product) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_share);

        ImageView imgWhatsApp = dialog.findViewById(R.id.imgWhatsApp);
        ImageView imgFacebook = dialog.findViewById(R.id.imgFacebook);
        ImageView imgMessenger = dialog.findViewById(R.id.imgMessenger);
        ImageView imgGmail = dialog.findViewById(R.id.imgGmail);

        ImageView imgTwitter = dialog.findViewById(R.id.imgTwitter);

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnTwitter(product, product.getImageJson().get(0));
            }
        });

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnGmail(product, product.getImageJson().get(0));
            }
        });

        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// selectImage();
                shareOnWhatsApp(product, product.getImageJson().get(0));
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_APP_PACKAGE, pm);
                if (isInstalled) {
                    shareViaFacebook(product, product.getImageJson().get(0));
                } else {
                    shareViaFacebookLite(product, product.getImageJson().get(0));

                }
            }
        });

        imgMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_MESSENGER_PACKAGE, pm);
                if (isInstalled) {
                    shareOnFacebookMessenger(product, product.getImageJson().get(0));
                } else {
                    shareOnFacebookMessengerLite(product, product.getImageJson().get(0));
                }
            }
        });

        dialog.show();
    }

    private void shareOnFacebookMessenger(Product product, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        sendIntent.setPackage(FACEBOOK_MESSENGER_PACKAGE);

        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Please install facebook messenger", toastView);
        }

    }

    private void shareOnFacebookMessengerLite(Product product, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        sendIntent.setPackage(FACEBOOK_MESSENGER_LITE_PACKAGE);

        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Please install facebook messenger", toastView);
        }

    }

    private void shareViaFacebook(Product product, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        shareIntent.setPackage(FACEBOOK_APP_PACKAGE);
        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Please install Facebook app.", toastView);
        }
    }

    private void shareViaFacebookLite(Product product, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        shareIntent.setPackage(FACEBOOK_APP_LITE_PACKAGE);
        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Please install Facebook app.", toastView);
        }
    }


    private void shareOnWhatsApp(Product product, String url) {
        Intent whatsappIntent = new Intent();
        whatsappIntent.setAction(Intent.ACTION_SEND);

        // whatsappIntent.setType("image/*");
        whatsappIntent.setType("text/plain");

        whatsappIntent.setPackage(WHATSAPP_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Whatsapp have not been installed.", toastView);
        }

    }

    private void shareOnTwitter(Product product, String url) {
        Intent twitter = new Intent();
        twitter.setAction(Intent.ACTION_SEND);

        twitter.setType("text/plain");

        twitter.setPackage(TWITTER_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            twitter.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());

        } else {
            twitter.putExtra(Intent.EXTRA_TEXT,
                    url + "\n\n" +
                            "Product Name: " + product.getName() + "\n" +
                            "New Price: " + product.getPrice() + "\n" +
                            "Old Price: " + product.getRegularPrice() + "\n" +
                            "Website: " + "https://circuit.pk/product/" + product.getNameSku());
        }

        twitter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(twitter);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Twitter have not been installed.", toastView);
        }

    }

    private void shareOnGmail(Product product, String url) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_compnay_share);

        Intent gmail = new Intent();
        gmail.setAction(Intent.ACTION_SEND);
        gmail.setType("image/*");
        gmail.setType("text/plain");

        gmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        gmail.setPackage(GMAIL_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");

//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        /*gmail.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + product.getName() + "\n" +
                        "New Price: " + product.getPrice() + "\n" +
                        "Old Price: " + product.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + product.getNameSku());*/


        String productDescription = "";
        if (utils.isTextNullOrEmptyOrZero(product.getRegularPrice())) {
            productDescription = "Product Name: " + product.getName() + "\n" +
                    "New Price: " + product.getPrice() + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.getNameSku() + "\n\n";

        } else {
            productDescription = "Product Name: " + product.getName() + "\n" +
                    "New Price: " + product.getPrice() + "\n" +
                    "Old Price: " + product.getRegularPrice() + "\n" +
                    "Website: " + "https://circuit.pk/product/" + product.getNameSku() + "\n\n";
        }

        gmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String Weblink = "https://circuit.pk/";
        String mobileAppLink = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
        String socialMediaLinks = "Social Media Pages:\n" + "Facebook: " + AppConstants.URL_CIRCUIT_PK_FACEBOOK_PAGE_URL + "\n" +
                "Twitter: " + AppConstants.URL_CIRCUIT_PK_TWITTER_PAGE_URL + "\n" +
                "Google Plus: " + AppConstants.URL_CIRCUIT_PK_GOOGLE_PLUS_PAGE_URL + "\n" +
                "Pinterest: " + AppConstants.URL_CIRCUIT_PK_PINTEREST_PAGE_URL + "\n" +
                "Youtube:" + AppConstants.URL_CIRCUIT_PK_YOUTUBE_PAGE_URL;


        String textDesc = "I am using circuit.pk app.Here, you can purchase electronic components in low cost.\n\n" +
                "Below are the links of Website and mobile app.\n";
        String linkDesc = productDescription + textDesc + "Website: " + Weblink + "\n" + "Mobile App: " + mobileAppLink + "\n\n" + socialMediaLinks;

        gmail.putExtra(Intent.EXTRA_TEXT, linkDesc);
        gmail.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(b));
        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(gmail);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Gmail have not been installed.", toastView);
        }

    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 60, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void shareOnWhatsApp(Uri imagePath) {
        Intent whatsappIntent = new Intent();
        whatsappIntent.setAction(Intent.ACTION_SEND);

        // whatsappIntent.setType("image/*");
        whatsappIntent.setType("text/plain");

        whatsappIntent.setPackage(WHATSAPP_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                "Website: " + "https://circuit.pk/");


        // if (imagePath != null) {
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        whatsappIntent.setType("image/*");
        // }


        // whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Whatsapp have not been installed.", toastView);
        }

    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {

        boolean found = true;

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }


    public static final String FACEBOOK_MESSENGER_PACKAGE = "com.facebook.orca";
    public static final String GOOGLE_PLAY_STORE_URI = "http://play.google.com/store/apps/details?id=";

   /* protected void shareOnFacebookMessenger(Uri uri) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE,
                "Check this app on: "
                        + GOOGLE_PLAY_STORE_URI
                        + "circit.pk"
        );


        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("image/*");

        sendIntent.setPackage(FACEBOOK_MESSENGER_PACKAGE);

        try {
            startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            showToastShortTime("Please install facebook messenger");
        }

    }
*/

    private int PICK_IMAGE_REQUEST = 1;
    private ShareDialog shareDialog;

    private void shareOnFacebook() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void showData() {
        searchParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
        searchParent.setVisibility(View.GONE);
    }

    @Override
    public void showAllData() {
        allData.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAllData() {
        allData.setVisibility(View.GONE);
    }

    @Override
    public void setAdapterItems(List<Product> products) {

//        String[][] separated = new String[0][0];
//        int p = 0;
//        for (int k = 0; k < products.size(); k++) {
//            // for (int j = 0; j < 1; j++) {
//
//            if (products.get(k).getImageJson().contains(",")) {
//                separated[k][p] = String.valueOf(products.get(k).getImageJson().split(","));
//            } else {
//                separated[k][p] = String.valueOf(products.get(k).getImageJson());
//            }
//
//            //}
//        }
//        Log.d("kkjk1", separated[0][0]);
//        Log.d("kkjk2", separated[1][0]);
//        Log.d("kkjk3", separated[2][0]);

        shopDetailsAdapter = new ShopDetailsAdapter(getActivity(), products, this, utils, sessionManager);
        showRecylerViewProductsDetail(shopDetailsAdapter);
        hideCircularProgressBar();
        showAllData();
        showData();
        isProgressBarShowing = false;
        presenter.setActionListener(new ShopDetailsPresenter.ProductItemActionListener() {
            @Override
            public void onItemTap(ImageView imageView, int cartsCount) {
                if (imageView != null) {
                    ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
                    ((BaseActivity) getActivity()).addItemToCart(cartsCount);

                }
            }
        });
    }

    @Override
    public int getCartCount() {
        return ((BaseActivity) getActivity()).countsForActionBar();
    }

    @Override
    public void addToFavoriteList(String name) {
        shopDetailsAdapter.addFavList(name);
    }

    @Override
    public void getFavourites(ProductResponse response) {
        presenter.getFavouritesList(response);

    }

    @Override
    public void getFavCounts() {
        ((BaseActivity) getActivity()).favCounts();

    }

    @Override
    public void hideCircularProgressBar() {
        progressbar.setVisibility(View.GONE);

    }

    @Override
    public void showCircularProgressBar() {
        progressbar.setVisibility(View.VISIBLE);

    }


    public void shareLinks() {
        shareDialog = new ShareDialog(this);  // intialize facebook shareDialog.

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setQuote("test")
                    .setContentTitle("Androidlift")
                    .setContentDescription("Androidlift blog")
                    .setContentUrl(Uri.parse("http://androidlift.info"))
                    .build();

            shareDialog.show(linkContent);
        }
    }


    @Override
    public void removeItemFromCart(Product shoppingDetailModel) {
        // showSnackBarShortTime("Please select atleast one item", getView());
        presenter.removeItem(shoppingDetailModel);

    }

    @Override
    public void getIsFavourites(String productName, int position) {
        presenter.getFavouritesListByProductName(productName, position);
    }


    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if ((hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        } else {
            mPermissionDenied = false;
            //permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                        (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission Allowed
                    int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if ((hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED)
                    ) {
                        //permissionsGranted();
                        mPermissionDenied = false;
                    } else {
                        mPermissionDenied = true;
                    }
                } else {
                    // showMissingPermissionError();
                    // Permission Denied
                    mPermissionDenied = true;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /**
     * Displays a dialog with error message explaining that the phone permission is missing.
     */
    private void showMissingPermissionError() {
        toastUtil.showToastLongTime("Please enable Read/Write Storage permission.");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearText:
                edtSearchProduct.setText("");
                shopDetailsAdapter.filter("");
                break;
        }
    }


//    public void notifyAdapter(ShoppingDetailModel shoppingDetailModel) {

//    }


}
