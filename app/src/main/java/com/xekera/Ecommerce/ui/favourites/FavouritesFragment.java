package com.xekera.Ecommerce.ui.favourites;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.FavoritesAdapter;
import com.xekera.Ecommerce.ui.adapter.HistoryAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsPresenter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.model.ShoppingDetailModel;
import com.xekera.Ecommerce.util.*;

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

    private int PICK_IMAGE_REQUEST = 1;
    public static final String FACEBOOK_MESSENGER_PACKAGE = "com.facebook.orca";
    public static final String FACEBOOK_MESSENGER_LITE_PACKAGE = "com.facebook.mlite";
    public static final String FACEBOOK_APP_PACKAGE = "com.facebook.katana";
    public static final String FACEBOOK_APP_LITE_PACKAGE = "com.facebook.lite";
    public static final String WHATSAPP_PACKAGE = "com.whatsapp";


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

                presenter.fetchFavouritesDetails();

            }
        }, 600);


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

                    if (Long.valueOf(favourites.getItemQuantity()) <= 0) {
                        showToastShortTime("Select atleast one quantity");
                        isShowing = true;
                        return;
                    }
                    long totalPrice = Long.valueOf(favourites.getItemIndividualPrice()) * Long.valueOf(favourites.getItemQuantity());

                    AddToCart addToCart = new AddToCart("15", favourites.getItemName(), String.valueOf(totalPrice)
                            , favourites.getItemQuantity(),
                            "N", favourites.getItemImage(), favourites.getItemCutPrice(),
                            favourites.getItemIndividualPrice(),
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

    @Override
    public void onIncrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice,
                                       byte[] byteImage, ImageView imgProductCopy, Bitmap bitmap) {

        presenter.saveProductDetails(quantity, price, totalPrice, productName, cutPrice, byteImage, imgProductCopy, bitmap);
    }

    @Override
    public void onDecrementButtonClick(long quantity, String price, String totalPrice, String productName, long cutPrice, byte[] byteImage, ImageView imgProductCopy) {

        presenter.saveProductDecrementDetails(quantity, price, totalPrice, productName, cutPrice, byteImage, imgProductCopy);

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
    public void onClickShareButton(final Favourites favourites, Bitmap bitmapAdd) {
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

    private void showShareDialog(Context context, final Favourites favourites) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_share);

        ImageView imgWhatsApp = dialog.findViewById(R.id.imgWhatsApp);
        ImageView imgFacebook = dialog.findViewById(R.id.imgFacebook);
        ImageView imgMessenger = dialog.findViewById(R.id.imgMessenger);

        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnWhatsApp(favourites, "https://store-cdn.arduino.cc/usa/catalog/product/cache/1/image/520x330/604a3538c15e081937dbfbd20aa60aad/a/0/a000066_featured_4.jpg");
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_APP_PACKAGE, pm);
                if (isInstalled) {
                    shareViaFacebook(favourites, "https://store-cdn.arduino.cc/usa/catalog/product/cache/1/image/520x330/604a3538c15e081937dbfbd20aa60aad/a/0/a000066_featured_4.jpg");
                } else {
                    shareViaFacebookLite(favourites, "https://store-cdn.arduino.cc/usa/catalog/product/cache/1/image/520x330/604a3538c15e081937dbfbd20aa60aad/a/0/a000066_featured_4.jpg");

                }
            }
        });

        imgMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getActivity().getPackageManager();
                boolean isInstalled = isPackageInstalled(FACEBOOK_MESSENGER_PACKAGE, pm);
                if (isInstalled) {
                    shareOnFacebookMessenger(favourites, "https://store-cdn.arduino.cc/usa/catalog/product/cache/1/image/520x330/604a3538c15e081937dbfbd20aa60aad/a/0/a000066_featured_4.jpg");
                } else {
                    shareOnFacebookMessengerLite(favourites, "https://store-cdn.arduino.cc/usa/catalog/product/cache/1/image/520x330/604a3538c15e081937dbfbd20aa60aad/a/0/a000066_featured_4.jpg");
                }
            }
        });

        dialog.show();
    }


    private void shareOnFacebookMessenger(Favourites favourites, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getItemName() + "\n" +
                        "New Price: " + favourites.getItemIndividualPrice() + "\n" +
                        "Old Price: " + favourites.getItemCutPrice() + "\n" +
                        "Website: " + "https://circuit.pk/");


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

    private void shareOnFacebookMessengerLite(Favourites favourites, String url) {

        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        sendIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getItemName() + "\n" +
                        "New Price: " + favourites.getItemIndividualPrice() + "\n" +
                        "Old Price: " + favourites.getItemCutPrice() + "\n" +
                        "Website: " + "https://circuit.pk/");


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

    private void shareViaFacebook(Favourites favourites, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        shareIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getItemName() + "\n" +
                        "New Price: " + favourites.getItemIndividualPrice() + "\n" +
                        "Old Price: " + favourites.getItemCutPrice() + "\n" +
                        "Website: " + "https://circuit.pk/");


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

    private void shareViaFacebookLite(Favourites favourites, String url) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

//        shareIntent.putExtra(Intent.EXTRA_TITLE,
//                "Circuit.pk"
//        );

        shareIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getItemName() + "\n" +
                        "New Price: " + favourites.getItemIndividualPrice() + "\n" +
                        "Old Price: " + favourites.getItemCutPrice() + "\n" +
                        "Website: " + "https://circuit.pk/");


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


    private void shareOnWhatsApp(Favourites favourites, String url) {
        Intent whatsappIntent = new Intent();
        whatsappIntent.setAction(Intent.ACTION_SEND);

        // whatsappIntent.setType("image/*");
        whatsappIntent.setType("text/plain");

        whatsappIntent.setPackage(WHATSAPP_PACKAGE);
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);


        whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                url + "\n\n" +
                        "Product Name: " + favourites.getItemName() + "\n" +
                        "New Price: " + favourites.getItemIndividualPrice() + "\n" +
                        "Old Price: " + favourites.getItemCutPrice() + "\n" +
                        "Website: " + "https://circuit.pk/");

        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

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


    /*private int GALLERY = 1, REQUEST_CAMERA = 2;
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
         callbackManager.onActivityResult(requestCode, resultCode, data);
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
        shareToMessenger(uri);
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
*/
}

