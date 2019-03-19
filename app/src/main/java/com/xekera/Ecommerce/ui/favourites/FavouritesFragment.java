package com.xekera.Ecommerce.ui.favourites;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.CallbackManager;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.response.fetch_favourite_response.Product;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details.AddToCartShopCardSelectedFragment;
import com.xekera.Ecommerce.util.*;
import org.json.JSONArray;

import javax.inject.Inject;
import java.io.*;
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

    @BindView(R.id.progressBarRelativeLayout)
    protected RelativeLayout progressBarRelativeLayout;


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

    View toastView;
    private ProgressCustomDialogController progressDialogControllerPleaseWait;

    private int PICK_IMAGE_REQUEST = 1;
    public static final String FACEBOOK_MESSENGER_PACKAGE = "com.facebook.orca";
    public static final String FACEBOOK_MESSENGER_LITE_PACKAGE = "com.facebook.mlite";
    public static final String FACEBOOK_APP_PACKAGE = "com.facebook.katana";
    public static final String FACEBOOK_APP_LITE_PACKAGE = "com.facebook.lite";
    public static final String WHATSAPP_PACKAGE = "com.whatsapp";
    public static final String TWITTER_PACKAGE = "com.twitter.android";
    public static final String GMAIL_PACKAGE = "com.google.android.gm";


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

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        //((BaseActivity) getActivity()).showBottomNavigation();

        recyclerViewAddToCartDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);

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


        presenter.setAddRemoveActionListener(new FavouritesPresenter.ProductAddRemoveActionListener() {
            @Override
            public void onItemTap(ImageView imageView, int cartsCount) {
                if (imageView != null) {
                    ((BaseActivity) getActivity()).makeFlyAnimation(imageView, cartsCount);
                    ((BaseActivity) getActivity()).addItemToCart(cartsCount);

                }
            }
        });

        progressBarRelativeLayout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (utils.isInternetAvailable()) {
                    //presenter.fetchFavouritesDetails();
                    if (utils.isTextNullOrEmpty(sessionManager.getusername()) ||
                            utils.isTextNullOrEmpty(sessionManager.getEmail())) {
                        showToastShortTime("First log in to view favourite.");
                    } else {
                        presenter.fetchFavouritesServer(sessionManager.getusername(), sessionManager.getEmail());
                    }
                } else {
                    showToastShortTime("Please connect to internet.");
                }


            }
        }, 600);


    }

    @Override
    public void itemsCountsBottomView(int index, long counts) {
        ((BaseActivity) getActivity()).setFavoriteBottomNavigationCount(index, counts);

    }

    @Override
    public void showToastShortTime(String message) {
        toastUtil.showToastShortTime(message, toastView);
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
        progressBarRelativeLayout.setVisibility(View.GONE);

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
    public void setAdapter(List<Product> addToCarts) {

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
    public void onCardClick(final Product favourites) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONArray json = new JSONArray(favourites.getImageJson());
                String jsonString = json.toString();

                AddToCartShopCardSelectedFragment addToCartShopCardSelectedFragment = new AddToCartShopCardSelectedFragment();
                ((BaseActivity) getActivity()).replaceFragmentForActivityTranstion(
                        addToCartShopCardSelectedFragment.newInstance(favourites.getName(),
                                favourites.getPrice(),
                                favourites.getRegularPrice(),
                                "1",
                                favourites.getImageJson().get(0)
                                , jsonString,
                                favourites.getId(),
                                favourites.getAboutProduct(), favourites.getProductSku(), favourites.getNameSku()));
            }
        }, 100);
    }

    @Override
    public void addToCartFavourites(final Favourites favourites, final int position, final ImageView img) {
//        if (isShowing) {
//            isShowing = false;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    String formattedDate = "";
//                    formattedDate = getCurrentDate();
//
//                    if (Long.valueOf(favourites.getItemQuantity()) <= 0) {
//                        showToastShortTime("Select atleast one quantity");
//                        isShowing = true;
//                        return;
//                    }
//                    long totalPrice = Long.valueOf(favourites.getItemIndividualPrice()) * Long.valueOf(favourites.getItemQuantity());
//
//                    AddToCart addToCart = new AddToCart("", favourites.getItemName(), String.valueOf(totalPrice)
//                            , favourites.getItemQuantity(),
//                            "N", favourites.getItemImage(), favourites.getItemCutPrice(),
//                            favourites.getItemIndividualPrice(),
//                            formattedDate, favourites.getImage(),
//                            favourites.getProduct_id(), favourites.getIsEmailFav(),
//                            favourites.getProductDescFav(), favourites.getImgArrListFav(),
//                            favourites.getNameSku());
//                    presenter.insertSelectedFavouritesToCart(addToCart, position, img);
//                    isShowing = true;
//                }
//            }, 100);
//        }
    }

    @Override
    public void removeFavourites(final Product favourites, final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.removeFromFavourites(favourites.getId(), position, sessionManager.getusername(), sessionManager.getEmail());
            }
        }, 50);
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
    public void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                       byte[] byteImage, ImageView imgProductCopy, Bitmap bitmap, String imgUrl,
                                       String productID, String isEmailFav, String productDesc, String imgArrList, String nameSku) {

        presenter.saveProductDetails(quantity, price, totalPrice, productName, cutPrice, byteImage, imgProductCopy, bitmap,
                imgUrl, productID, isEmailFav, productDesc, imgArrList, nameSku);
    }

    @Override
    public void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                       byte[] byteImage, ImageView imgProductCopy, String imgUrl, String productID,
                                       String isEmailFav, String productDesc, String imgArrList, String nameSku) {

        presenter.saveProductDecrementDetails(quantity, price, totalPrice, productName, cutPrice, byteImage, imgProductCopy,
                imgUrl, productID, isEmailFav, productDesc, imgArrList, nameSku);

    }

    @Override
    public void removeItemFromCart(Favourites favourites) {
        presenter.removeItem(favourites);

    }

    @Override
    public void onClickButtonMessenger() {
        onMessengerButtonClicked();
    }

    boolean isShareButtonClick = true;

    @Override
    public void onClickShareButton(final Product favourites) {
        if (isShareButtonClick) {
            isShareButtonClick = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showShareDialog(getActivity(), favourites);
                    isShareButtonClick = true;
                }
            }, 300);
        }
        //  shareOnFacebookMessenger(favourites, "https://pbs.twimg.com/profile_images/664342624082526208/VH-iVYvv_400x400.jpg");
        //  shareOnWhatsApp(favourites, "https://pbs.twimg.com/profile_images/664342624082526208/VH-iVYvv_400x400.jpg");
        //  shareViaFacebook(favourites, "https://pbs.twimg.com/profile_images/664342624082526208/VH-iVYvv_400x400.jpg");

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
    public int getCartCount() {
        return ((BaseActivity) getActivity()).countsForActionBar();
    }

    @Override
    public void addToCartServer(String product_id, String itemQuantity, String itemPrice, String last_id, String discountPrice,
                                ImageView imgProductCopy, int position, String nameSku) {
        if (utils.isInternetAvailable()) {
            if (utils.isTextNullOrEmptyOrZero(itemQuantity)) {
                showToastShortTime("Please atleast select one quantity");
            } else {

                presenter.getAllProducts(product_id, itemQuantity, itemPrice, discountPrice,
                        sessionManager.getKeyRandomKey(), imgProductCopy, position,
                        sessionManager.getusername(), sessionManager.getEmail(), nameSku);
//                presenter.addToCartApi(product_id, itemQuantity, itemPrice, discountPrice,
//                        sessionManager.getKeyRandomKey(), imgProductCopy, position,
//                        sessionManager.getusername(), sessionManager.getEmail()
//                );
            }
        } else {
            showToastShortTime("Please connect to internet.");
        }
    }

    private void showShareDialog(Context context, final Product favourites) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_share);

        ImageView imgWhatsApp = dialog.findViewById(R.id.imgWhatsApp);
        ImageView imgFacebook = dialog.findViewById(R.id.imgFacebook);
        ImageView imgMessenger = dialog.findViewById(R.id.imgMessenger);
        ImageView imgTwitter = dialog.findViewById(R.id.imgTwitter);
        ImageView imgGmail = dialog.findViewById(R.id.imgGmail);

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnTwitter(favourites, favourites.getName());
            }
        });
        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnGmail(favourites, favourites.getImageJson().get(0));
            }
        });


        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /// selectImage();
                shareOnWhatsApp(favourites, favourites.getImageJson().get(0));
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_APP_PACKAGE, pm);
                if (isInstalled) {
                    shareViaFacebook(favourites, favourites.getImageJson().get(0));
                } else {
                    shareViaFacebookLite(favourites, favourites.getImageJson().get(0));

                }
            }
        });

        imgMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_MESSENGER_PACKAGE, pm);
                if (isInstalled) {
                    shareOnFacebookMessenger(favourites, favourites.getImageJson().get(0));
                } else {
                    shareOnFacebookMessengerLite(favourites, favourites.getImageJson().get(0));
                }
            }
        });

        dialog.show();
    }


    private void shareOnFacebookMessenger(Product favourites, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());


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

    private void shareOnFacebookMessengerLite(Product favourites, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());


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

    private void shareViaFacebook(Product favourites, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        shareIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());


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

    private void shareViaFacebookLite(Product favourites, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        shareIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());


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

    private void shareOnGmail(Product favourites, String url) {

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

        String productDescription = "Product Name: " + favourites.getName() + "\n" +
                "New Price: " + favourites.getPrice() + "\n" +
                "Old Price: " + favourites.getRegularPrice() + "\n" +
                "Website: " + "https://circuit.pk/product/" + favourites.getNameSku() + "\n\n";

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

    private void shareOnWhatsApp(Product favourites, String url) {
        Intent whatsappIntent = new Intent();
        whatsappIntent.setAction(Intent.ACTION_SEND);

        // whatsappIntent.setType("image/*");
        whatsappIntent.setType("text/plain");

        whatsappIntent.setPackage(WHATSAPP_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());

        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Whatsapp have not been installed.", toastView);
        }

    }

    private void shareOnTwitter(Product favourites, String url) {
        Intent twitter = new Intent();
        twitter.setAction(Intent.ACTION_SEND);

        twitter.setType("text/plain");

        twitter.setPackage(TWITTER_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        twitter.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getName() + "\n" +
                        "New Price: " + favourites.getPrice() + "\n" +
                        "Old Price: " + favourites.getRegularPrice() + "\n" +
                        "Website: " + "https://circuit.pk/product/" + favourites.getNameSku());

        twitter.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // sendIntent.putExtra(Intent.EXTRA_STREAM, uri);


        try {
            startActivity(twitter);
        } catch (android.content.ActivityNotFoundException ex) {
            toastUtil.showToastShortTime("Twitter have not been installed.", toastView);
        }

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


    @Override
    public void hideLoadingProgressDialog() {
        progressBarRelativeLayout.setVisibility(View.GONE);
    }


    @Override
    public void setCountZero(int counts) {
        ((BaseActivity) getActivity()).addItemToCart(0);

    }

    @Override
    public void setDecrementCount(int counts) {
        ((BaseActivity) getActivity()).removeItemToCart(counts);

    }


    private void onMessengerButtonClicked() {
        //callbackManager = CallbackManager.Factory.create();

        //  selectImage();
    }


    private int GALLERY = 1, REQUEST_CAMERA = 2;
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    CallbackManager callbackManager;


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select profile Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    // Opens Camera to take a picture
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data); //image is chosen from gallery
            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data); // image is captured using device camera
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                thumbnail, "Image Description", null);
        Uri uri = Uri.parse(path);
        shareOnWhatsApp(uri);
        // shareToMessenger(uri);
    }

    private void shareToMessenger(Uri imagePath) {
        // Create the parameters for what we want to send to Messenger.
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(imagePath, "image/*")
                        .setMetaData("fddffdfdfdf")
                        .build();

        // Shares the content to Messenger. If Messenger is not installed
        // or Messenger needs to be upgraded, this will redirect
        // the user to the play store.
        MessengerUtils.shareToMessenger(
                getActivity(),
                REQUEST_CODE_SHARE_TO_MESSENGER,
                shareToMessengerParams);
    }

}

