package com.xekera.Ecommerce.ui;

import android.Manifest;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.io.*;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.*;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.ui.about.AboutFragment;
import com.xekera.Ecommerce.ui.account.AccountFragment;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.contact_us.ContactUsFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dashboard.DashboardFragment;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragment;
import com.xekera.Ecommerce.ui.favourites.FavouritesFragment;
import com.xekera.Ecommerce.ui.favourites.FavouritesMVP;
import com.xekera.Ecommerce.ui.favourites.FavouritesModel;
import com.xekera.Ecommerce.ui.favourites.FavouritesPresenter;
import com.xekera.Ecommerce.ui.history.HistoryFragment;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.ui.privacy_policy.PrivacyPolicyFragment;
import com.xekera.Ecommerce.ui.search_all_products.SearchAllProductsFragment;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.ui.signup.SignupFragment;
import com.xekera.Ecommerce.ui.terms_and_condition.TermsAndConditionFragment;
import com.xekera.Ecommerce.util.*;
import de.hdodenhof.circleimageview.CircleImageView;


public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.imgBack)
    protected ImageView imgBack;
    @BindView(R.id.txtScreenTitle)
    protected TextView txtScreenTitle;
    @BindView(R.id.imgLogin)
    protected ImageView imgLogin;
    @BindView(R.id.textviewLogin)
    protected TextView textviewLogin;
    @BindView(R.id.imgShoppingCart)
    protected ImageView imgShoppingCart;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    @BindView(R.id.navigation)
    protected BottomNavigationView navigation;
    @BindView(R.id.txtAddToCartNotify)
    protected TextView txtAddToCartNotify;
    @BindView(R.id.badge2)
    protected RelativeLayout badge2;
    @BindView(R.id.fb_button)
    protected LoginButton fb_button;
    @BindView(R.id.badge_layout3)
    protected RelativeLayout badge_layout3;


    @BindView(R.id.dashboardActionBar)
    protected AppBarLayout dashboardActionBar;

    @BindView(R.id.fragmentContainer)
    protected FrameLayout fragmentContainer;

    private FragmentManager manager;
    private ActionBarDrawerToggle toggle;

    public abstract int getLayout();

    public abstract Fragment getFragment();

    /*BRIGHTNESS CONTROLLING*/
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window
    private Window window;

    @Inject
    SessionManager sessionManager;
    @Inject
    Utils utils;
    @Inject
    ToastUtil toastUtil;
    @Inject
    AppDatabase appDatabase;
    @Inject
    protected SnackUtil snackUtil;

    private int itemCounter = 0;

    private static final String IMAGE_DIRECTORY = "/Circuit.pk";
    private int GALLERY = 1, CAMERA = 2;

    public CircleImageView profilePhoto;

    boolean isEnabled = true;
    boolean isLoginBtnEnable = true;
    View toastView;

    Button btnChangePhoto;
    FavouritesModel favouritesModel;
    Button btnLogin;
    // TextView slideshow, gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
        ((App) getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        utils.setupParent(this, toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        enableHomeIcon(true);

        favouritesModel = new FavouritesModel(appDatabase, utils);

        navigationView.setNavigationItemSelectedListener(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toastView = getLayoutInflater().inflate(R.layout.activity_toast_custom_view, null);

        View headerView = navigationView.getHeaderView(0);
        btnChangePhoto = headerView.findViewById(R.id.btnChangePhoto);
        btnLogin = headerView.findViewById(R.id.btnLogin);

        //  profilePhoto = headerView.findViewById(R.id.img);
        profilePhoto = headerView.findViewById(R.id.img);

        if (sessionManager.isLoggedIn() || sessionManager.isLoginViaFacebook()) {
            btnChangePhoto.setVisibility(View.VISIBLE);
            showLogoutOption();
            btnLogin.setText("Logout");

        } else {
            btnChangePhoto.setVisibility(View.GONE);
            hideLogoutOption();
            btnLogin.setText("Login");

        }
        btnChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();

                if (!mPermissionDenied) {
                    if (isEnabled) {
                        isEnabled = false;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isEnabled = true;
                                showPictureDialog();

                            }
                        }, 400);
                    }
                }

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.isLoggedIn() || sessionManager.isLoginViaFacebook()) {
                    showLogoutDialog(BaseActivity.this, "Logout", utils.getStringFromResourceId(R.string.are_you_sure_logout));
                } else {

                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                    if (!(fragment instanceof LoginFragment)) {

                        if (isLoginBtnEnable) {
                            isLoginBtnEnable = false;
                            Menu menu = navigationView.getMenu();
                            MenuItem menuItem;

                            Menu bottomMenu = navigation.getMenu();
                            MenuItem bottomMenuItem;

                            for (int i = 0; i < menu.size(); i++) {
                                menuItem = menu.getItem(i);
                                menuItem.setChecked(false);
                            }

                            for (int i = 0; i < bottomMenu.size(); i++) {
                                bottomMenuItem = bottomMenu.getItem(i);
                                bottomMenuItem.setCheckable(false);
                                bottomMenuItem.setChecked(false);
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    replaceFragmentWithContainer(new LoginFragment());
                                    drawer.closeDrawer(GravityCompat.START);
                                    isLoginBtnEnable = true;

                                }
                            }, 250);

                        }
                    } else {
                        isLoginBtnEnable = true;
                        drawer.closeDrawer(GravityCompat.START);

                    }
                }
            }
        });

        //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // ((BaseActivity) getActivity()).addDashboardFragment(new ContinueShopFragment());

        // attaching bottom sheet behaviour - hide / show on scroll
        // CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationBehavior());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cek", "home selected");
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        enableHomeIcon(true);
                        //  popBackstack();
                        hideSoftKeyboard();
                        drawer.openDrawer(GravityCompat.START);

                    }
//                    else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                        popBackstack();
//                        hideSoftKeyboard();
//                    }
                    else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
//        imgLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                // startActivity(i);
//
//                try {
//                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                    if (!(fragment instanceof LoginFragment || fragment instanceof SignupFragment)) {
//                        addFragment(new LoginFragment());
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

//        textviewLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//                    if (!(fragment instanceof LoginFragment || fragment instanceof SignupFragment)) {
//                        addFragment(new LoginFragment());
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        badge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (!(fragment instanceof AddToCartFragment)) {
                    Menu menu = navigationView.getMenu();
                    MenuItem menuItem;

                    Menu bottomMenu = navigation.getMenu();
                    MenuItem bottomMenuItem;

                    for (int i = 0; i < menu.size(); i++) {
                        menuItem = menu.getItem(i);
                        menuItem.setChecked(false);
                    }

                    for (int i = 0; i < bottomMenu.size(); i++) {
                        bottomMenuItem = bottomMenu.getItem(i);
                        bottomMenuItem.setCheckable(false);
                        bottomMenuItem.setChecked(false);
                    }

                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });
                    // showHideBottomNavigationCount(1);


                    //    navigation.setSelectedItemId(R.id.navigation_cart);
                    replaceFragmentWithContainer(new AddToCartFragment());
                }


            }
        });


        badge_layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (!(fragment instanceof SearchAllProductsFragment)) {
                    Menu menu = navigationView.getMenu();
                    MenuItem menuItem;

                    Menu bottomMenu = navigation.getMenu();
                    MenuItem bottomMenuItem;

                    for (int i = 0; i < menu.size(); i++) {
                        menuItem = menu.getItem(i);
                        menuItem.setChecked(false);
                    }

                    for (int i = 0; i < bottomMenu.size(); i++) {
                        bottomMenuItem = bottomMenu.getItem(i);
                        bottomMenuItem.setCheckable(false);
                        bottomMenuItem.setChecked(false);
                    }
                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });
                    // showHideBottomNavigationCount(1);


                    //    navigation.setSelectedItemId(R.id.navigation_cart);
                    replaceFragmentWithContainer(new SearchAllProductsFragment());
                }


            }
        });

//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//                //  onBackImagePressed();
//            }
//        });


        initializeFragment();

        // gallery = (TextView) MenuItemCompat.getActionView(navigation.getMenu().
        //       findItem(R.id.navigation_shop));

//This method will initialize the count value
        // initializeCountDrawer();

//        final Menu menu = navigation.getMenu();
//        menu.getItem(2).setTitle("Cart(" + counts + ")");

        //   View count = (View) menu.findItem(R.id.navigation_shop).getActionView();
        // Button notifCount = (Button) menu.findItem(R.id.navigation_shop).getActionView().findViewById(R.id.notif_count);
        // TextView view = (Button) menu.findItem(R.id.navigation_shop).getActionView();

        //  notifCount.setText(String.valueOf(120));
        favCounts();

    }

//    private void initializeCountDrawer() {
//        //Gravity property aligns the text
//        gallery.setGravity(Gravity.CENTER_VERTICAL);
//        gallery.setTypeface(null, Typeface.BOLD);
//        gallery.setTextColor(getResources().getColor(R.color.colorAccent));
//        gallery.setText("99+");
//
//    }

    public void setLoginActiveBtnText() {
        btnLogin.setText("Logout");
    }


    public void setLoginInActiveBtnText() {
        btnLogin.setText("Login");
    }

    public void favCounts() {
        favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
            @Override
            public void onCartDetailsReceived(List<Favourites> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    setTotalBottomNavigationCount(1, 0);
                    return;
                } else {
                    setTotalBottomNavigationCount(1, addToCarts.size());
                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment;
            Fragment fragmentContainer = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem;

            Menu bottomMenu = navigation.getMenu();
            MenuItem bottomMenuItem;

            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    // showHideBottomNavigationCount(1);
                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });

                    if (!(fragmentContainer instanceof ShopFragment)) {
                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }
                        menuItem = menu.findItem(R.id.navProduct);
                        menuItem.setChecked(true);

                        // bottomMenu = navigation.getMenu();

                        //for (int i = 0; i < bottomMenu.size(); i++) {
                        bottomMenuItem = bottomMenu.findItem(R.id.navigation_shop);
                        bottomMenuItem.setCheckable(true);
                        bottomMenuItem.setChecked(true);

                        //}

                        fragment = new ShopFragment();
                        replaceFragmentWithContainer(fragment);
                    }
                    return true;
                case R.id.navigation_favourite:
                    //    Toast.makeText(getActivity(), "Wishlist is selected", Toast.LENGTH_SHORT).show();
                    //  ((BaseActivity) getActivity()).popBackstack();
                    //((BaseActivity) getActivity()).addDashboardFragment(new FavouritesFragment());
                    // showHideBottomNavigationCount(1);
                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });
                    if (!(fragmentContainer instanceof FavouritesFragment)) {
                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }
                        menuItem = menu.findItem(R.id.nav_Favourite);
                        menuItem.setChecked(true);

                        bottomMenuItem = bottomMenu.findItem(R.id.navigation_favourite);
                        bottomMenuItem.setCheckable(true);
                        bottomMenuItem.setChecked(true);

                        fragment = new FavouritesFragment();
                        replaceFragmentWithContainer(fragment);
                    }
                    return true;
               /* case R.id.navigation_setting:

                    if (!(fragmentContainer instanceof SettingFragment)) {

                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }
                        menuItem = menu.findItem(R.id.nav_Setting);
                        menuItem.setChecked(true);

                        bottomMenuItem = bottomMenu.findItem(R.id.navigation_setting);
                        bottomMenuItem.setCheckable(true);
                        bottomMenuItem.setChecked(true);


                        fragment = new SettingFragment();
                        replaceFragmentWithContainer(fragment);
                    }

                    //  navigation.setSelectedItemId(R.id.navigation_History);

                    return true;
*/
                case R.id.navigation_User:
                    //    Toast.makeText(getActivity(), "Wishlist is selected", Toast.LENGTH_SHORT).show();
                    //  ((BaseActivity) getActivity()).popBackstack();
                    //((BaseActivity) getActivity()).addDashboardFragment(new FavouritesFragment());
                    //  showHideBottomNavigationCount(1);
                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });
                    if (!(fragmentContainer instanceof AccountFragment)
                            || !(fragmentContainer instanceof LoginFragment)
                    ) {
                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }
                        menuItem = menu.findItem(R.id.nav_Profile);
                        menuItem.setChecked(true);

                        bottomMenuItem = bottomMenu.findItem(R.id.navigation_User);
                        bottomMenuItem.setCheckable(true);
                        bottomMenuItem.setChecked(true);


                        if (sessionManager.isSignUp() ||
                                sessionManager.isLoggedIn() ||
                                sessionManager.getKeyIsFacebookLogin()) {

                            fragment = new AccountFragment();
                            replaceFragmentWithContainer(fragment);
                        } else {
                            toastUtil.showToastShortTime("Required SignUp/Login to view account.", toastView);

                            fragment = new LoginFragment();
                            replaceFragmentWithContainer(fragment);
                        }
                    }

                    return true;


                case R.id.navigation_History:
                    // showHideBottomNavigationCount(1);
                    favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                        @Override
                        public void onCartDetailsReceived(List<Favourites> addToCarts) {
                            if (addToCarts == null || addToCarts.size() == 0) {
                                setTotalBottomNavigationCount(1, 0);
                                return;
                            } else {
                                setTotalBottomNavigationCount(1, addToCarts.size());
                            }
                        }

                        @Override
                        public void onErrorReceived(Exception ex) {
                            ex.printStackTrace();

                        }
                    });
                    if (!(fragmentContainer instanceof HistoryFragment)) {
                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }
                        bottomMenuItem = bottomMenu.findItem(R.id.navigation_History);
                        bottomMenuItem.setCheckable(true);
                        bottomMenuItem.setChecked(true);

                        fragment = new HistoryFragment();
                        replaceFragmentWithContainer(fragment);
                    }

                    return true;
            }
            return false;
        }
    };

    public void addSignUpFragment() {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (!(fragment instanceof SignupFragment)) {
                addFragment(new SignupFragment());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void popBackstack() {

        getSupportFragmentManager().popBackStackImmediate();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        //fragment instanceof SettingFragment ||
        if (fragment instanceof ShopFragment ||
                fragment instanceof HistoryFragment ||
                fragment instanceof FavouritesFragment ||
                fragment instanceof AccountFragment
        ) {
            Menu bottomMenu = navigation.getMenu();
            MenuItem bottomMenuItem;
            // showBottomNavigation();
            /*if (fragment instanceof SettingFragment) {
                navigation.setSelectedItemId(R.id.navigation_setting);

                bottomMenuItem = bottomMenu.findItem(R.id.navigation_setting);
                bottomMenuItem.setCheckable(true);
                bottomMenuItem.setChecked(true);


            } else */

            if (fragment instanceof ShopFragment) {
                navigation.setSelectedItemId(R.id.navigation_shop);

                bottomMenuItem = bottomMenu.findItem(R.id.navigation_shop);
                bottomMenuItem.setCheckable(true);
                bottomMenuItem.setChecked(true);
                // showHideBottomNavigationCount(1);
                favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                    @Override
                    public void onCartDetailsReceived(List<Favourites> addToCarts) {
                        if (addToCarts == null || addToCarts.size() == 0) {
                            setTotalBottomNavigationCount(1, 0);
                            return;
                        } else {
                            setTotalBottomNavigationCount(1, addToCarts.size());
                        }
                    }

                    @Override
                    public void onErrorReceived(Exception ex) {
                        ex.printStackTrace();

                    }
                });

            } else if (fragment instanceof HistoryFragment) {
                navigation.setSelectedItemId(R.id.navigation_History);
                // showHideBottomNavigationCount(1);
                favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                    @Override
                    public void onCartDetailsReceived(List<Favourites> addToCarts) {
                        if (addToCarts == null || addToCarts.size() == 0) {
                            setTotalBottomNavigationCount(1, 0);
                            return;
                        } else {
                            setTotalBottomNavigationCount(1, addToCarts.size());
                        }
                    }

                    @Override
                    public void onErrorReceived(Exception ex) {
                        ex.printStackTrace();

                    }
                });
                bottomMenuItem = bottomMenu.findItem(R.id.navigation_History);
                bottomMenuItem.setCheckable(true);
                bottomMenuItem.setChecked(true);


            } else if (fragment instanceof FavouritesFragment) {
                navigation.setSelectedItemId(R.id.navigation_favourite);

                bottomMenuItem = bottomMenu.findItem(R.id.navigation_favourite);
                bottomMenuItem.setCheckable(true);
                bottomMenuItem.setChecked(true);

            } else if (fragment instanceof AccountFragment) {
                navigation.setSelectedItemId(R.id.navigation_User);

                bottomMenuItem = bottomMenu.findItem(R.id.navigation_User);
                bottomMenuItem.setCheckable(true);
                bottomMenuItem.setChecked(true);

            }
        } else {

            //    showHideBottomNavigationCount(1);
            favouritesModel.getTotalCountFav(new FavouritesModel.IFetchOrderDetailsList() {
                @Override
                public void onCartDetailsReceived(List<Favourites> addToCarts) {
                    if (addToCarts == null || addToCarts.size() == 0) {
                        setTotalBottomNavigationCount(1, 0);
                        return;
                    } else {
                        setTotalBottomNavigationCount(1, addToCarts.size());
                    }
                }

                @Override
                public void onErrorReceived(Exception ex) {
                    ex.printStackTrace();

                }
            });
            Menu bottomMenu = navigation.getMenu();
            MenuItem bottomMenuItem;
            bottomMenuItem = bottomMenu.findItem(R.id.navigation_shop);
            bottomMenuItem.setCheckable(false);
            bottomMenuItem.setChecked(false);

            bottomMenuItem = bottomMenu.findItem(R.id.navigation_favourite);
            bottomMenuItem.setCheckable(false);
            bottomMenuItem.setChecked(false);

          /*  bottomMenuItem = bottomMenu.findItem(R.id.navigation_setting);
            bottomMenuItem.setCheckable(false);
            bottomMenuItem.setChecked(false);
*/
            bottomMenuItem = bottomMenu.findItem(R.id.navigation_History);
            bottomMenuItem.setCheckable(false);
            bottomMenuItem.setChecked(false);

            bottomMenuItem = bottomMenu.findItem(R.id.navigation_User);
            bottomMenuItem.setCheckable(false);
            bottomMenuItem.setChecked(false);

        }
    }

    public void uncheckHomeScreen() {

        Menu bottomMenu = navigation.getMenu();
        MenuItem bottomMenuItem;
        bottomMenuItem = bottomMenu.findItem(R.id.navigation_shop);
        bottomMenuItem.setCheckable(false);
        bottomMenuItem.setChecked(false);
    }

    public void navigateToScreen(final int menuId) {
        //   showBottomNavigation();
        View view = navigation.findViewById(menuId);
        view.performClick();
    }

//        if (fragment instanceof ShopDetailsFragment) {
//           // ((ShopDetailsFragment) fragment).setTitle();
//            //((ShopDetailsFragment) fragment).showBackImageIcon();
//            //((ShopDetailsFragment) fragment).hideLoginIcon();
//            //((ShopDetailsFragment) fragment).hideHumbergIcon();
//        } else if (fragment instanceof ContinueShopFragment) {
//            //((ContinueShopFragment) fragment).setTitle();
//            //((ContinueShopFragment) fragment).showLoginIcon();
//            //((ContinueShopFragment) fragment).showHumbergIcon();
//        } else if (fragment instanceof ShopCardSelectedFragment) {
//            //((ShopDetailsFragment) fragment).showBackImageIcon();
//            //((ShopDetailsFragment) fragment).hideLoginIcon();
//            //((ShopDetailsFragment) fragment).hideHumbergIcon();
//        }


    private void setActionItems() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        if (fragment instanceof DashboardFragment) {

        } else if (fragment instanceof ShopDetailsFragment) {
            //  ((ShopDetailsFragment) fragment).showBackImageIcon();
            // ((ShopDetailsFragment) fragment).hideLoginIcon();
            //((ShopDetailsFragment) fragment).hideHumbergIcon();

        } else if (fragment instanceof ShopFragment) {
            ((ShopFragment) fragment).setTitle();
            //((ContinueShopFragment) fragment).showLoginIcon();
            //((ContinueShopFragment) fragment).showHumbergIcon();
            //((ContinueShopFragment) fragment).hideBackImageIcon();
        } else if (fragment instanceof ShopCardSelectedFragment) {
            //((ShopCardSelectedFragment) fragment).showBackImageIcon();
            //((ShopCardSelectedFragment) fragment).hideLoginIcon();
            //((ShopCardSelectedFragment) fragment).hideHumbergIcon();
        }

    }


    public void popBackFromStack(Fragment fragment) {
        try {

            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.remove(fragment);
            trans.commit();
            manager.popBackStack();


        } catch (Exception e) {

        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    private void initializeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = getFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    public void initializeFragment2(Fragment fragmentNew) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer2);
        if (fragment == null) {
            fragment = fragmentNew;
            manager.beginTransaction()
                    .add(R.id.fragmentContainer2, fragment)
                    .commit();
        }
    }

    boolean backPressedOnce = false;

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragment instanceof ShopDetailsFragment) {

            //   hideBackImageIcon();
            // setTitle("Shop");
            //showLoginIcon();
            enableHomeIcon(true);
            //  showBottomNavigation();
            //hideHumberIcon();
            popBackstack();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

        } else if (fragment instanceof DashboardFragment) {
            enableHomeIcon(true);

            if (backPressedOnce) {
                this.finish();
            }

            backPressedOnce = true;
            toastUtil.showToastShortTime("Press back again to exit.", toastView);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedOnce = false;

                }
            }, 2000);

        } else if (fragment instanceof ShopFragment) {
            enableHomeIcon(true);

            if (backPressedOnce) {
                this.finish();
            }

            backPressedOnce = true;
            toastUtil.showToastShortTime("Press back again to exit.", toastView);

            // Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedOnce = false;

                }
            }, 2000);

        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            enableHomeIcon(true);
            super.onBackPressed();
            popBackstack();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            enableHomeIcon(true);

            popBackstack();
        }

//        else if (fragment instanceof ShopCardSelectedFragment) {
//            enableHomeIcon(false);
//            popBackstack();
//            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//
//
//        }
//
        else {
            if (backPressedOnce) {
                this.finish();
            }

            backPressedOnce = true;
            toastUtil.showToastShortTime("Press back again to exit.", toastView);
            // Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedOnce = false;

                }
            }, 2000);
        }
    }


    private void onBackImagePressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            // hideBackImageIcon();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            enableHomeIcon(true);
            super.onBackPressed();
            // hideBackImageIcon();
            popBackstack();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            //hideBackImageIcon();
            enableHomeIcon(true);
            popBackstack();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);


        }
    }

    public Toolbar getToolbar() {

        return toolbar;
    }

    public void setTitle(String title) {
        txtScreenTitle.setText(title);
    }

    public void hideLoginIcon() {
        imgLogin.setVisibility(View.GONE);
    }

    public void hideBottomNavigation() {
        navigation.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        navigation.setVisibility(View.VISIBLE);

    }

    public void hideShoppingCartIcon() {
        imgShoppingCart.setVisibility(View.GONE);
    }


    public void hideBackImageIcon() {
        imgBack.setVisibility(View.GONE);
    }


    public void showBackImageIcon() {
        imgBack.setVisibility(View.VISIBLE);
    }

    public void setCartsCounts(long counts) {
        final Menu menu = navigation.getMenu();
        menu.getItem(2).setTitle("Cart(" + counts + ")");

    }

    public void updateCartCounts() {
        if (txtAddToCartNotify.getText().toString().equalsIgnoreCase("0")) {
            txtAddToCartNotify.setText("0");
        } else {
            long counts = Long.valueOf(txtAddToCartNotify.getText().toString()) - 1;
            txtAddToCartNotify.setText(String.valueOf(counts));

        }

    }

    public void setCartsCounts(long counts, int position, String navigationName) {
        final Menu menu = navigation.getMenu();
        menu.getItem(position).setTitle(navigationName + "(" + counts + ")");

    }

    public void hideHumberIcon() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void showHumberIcon() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void showLoginIcon() {
        imgLogin.setVisibility(View.VISIBLE);
    }


    public void showShoppingCartIcon() {
        imgShoppingCart.setVisibility(View.VISIBLE);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
        //  drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void showActionBar() {
        getSupportActionBar().show();
    }

    public void shakeCartTextview(Animation shake) {
        imgShoppingCart.setAnimation(shake);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    public void addFragment(Fragment fragment) {
        manager = getSupportFragmentManager();

        if (fragment != null) {
            try {
                //  Fragment fragmentContainer = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                //  if (!(fragmentContainer instanceof DeliveyBillingDetailsFragment)) {

                manager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();


                //   }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        enableHomeIcon(true);
    }


    public int countsForActionBar() {
        return Integer.valueOf(txtAddToCartNotify.getText().toString()) + 1;
    }

    public void countsForActionBarWithoutReturn() {
        int cartCounts = Integer.valueOf(txtAddToCartNotify.getText().toString()) + 1;
        txtAddToCartNotify.setText(String.valueOf(cartCounts));
    }

    private void replaceFragmentWithContainer(Fragment fragment) {

        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        enableHomeIcon(true);

    }


    private void addFragmentWithOutAnimationContainer(Fragment fragment) {

        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        enableHomeIcon(true);

    }

    public void replaceFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        disbaleHomeIcon(true);
    }


    //With bundle for activity transition
    public void replaceFragmentForActivityTranstion(Fragment fragment) {
        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();


        }
        disbaleHomeIcon(true);
    }

    public void addTargetFragmentWithLockedHumberIcon(Fragment fragment) {
//        manager = getSupportFragmentManager();
//        if (fragment != null) {
//            ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
//            ShopCardSelectedFragment shopCardSelectedFragment = new ShopCardSelectedFragment();
//            shopCardSelectedFragment.setTargetFragment(shopDetailsFragment, 34);
//            manager.beginTransaction()
//                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//                    .add(R.id.fragmentContainer, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
//        disbaleHomeIcon(false);
        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        fragment.setTargetFragment(shopDetailsFragment, 55);

        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .add(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        disbaleHomeIcon(true);
    }


    public void addDashboardFragment(Fragment fragment) {
        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        enableHomeIcon(true);
    }

    public void addFragment(Fragment fragment, String tag) {
        manager = getSupportFragmentManager();
        if (fragment != null) {
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
        enableHomeIcon(false);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.navFuelCalculator) {
//            addFragment(new FuelCalculatorFragment());
//            drawer.closeDrawer(GravityCompat.START);
//            enableHomeIcon(false);
//        }

        Fragment fragmentContainer = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        Menu bottomMenu = navigation.getMenu();
        MenuItem menuItemBottom;

        if (id == R.id.navShare) {
            shareTextUrl();
        } else if (id == R.id.navProduct) {
            if (!(fragmentContainer instanceof ShopFragment)) {

                // replaceFragmentWithContainer(new ContinueShopFragment());
                navigateToScreen(R.id.navigation_shop);
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");


                return true;

            }
        } else if (id == R.id.nav_Favourite) {
            if (!(fragmentContainer instanceof FavouritesFragment)) {
                navigateToScreen(R.id.navigation_favourite);
//                replaceFragmentWithContainer(new FavouritesFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");

                return true;

            }
        } else if (id == R.id.nav_Profile) {
            if (!(fragmentContainer instanceof AccountFragment)) {
                drawer.closeDrawer(GravityCompat.START);

                if (sessionManager.isSignUp() ||
                        sessionManager.isLoggedIn() ||
                        sessionManager.getKeyIsFacebookLogin()) {
                    navigateToScreen(R.id.navigation_User);

                } else {
                    bottomMenu = navigation.getMenu();

                    for (int i = 0; i < bottomMenu.size(); i++) {
                        menuItemBottom = bottomMenu.getItem(i);
                        menuItemBottom.setCheckable(false);
                        menuItemBottom.setChecked(false);
                    }
                    toastUtil.showToastShortTime("Required SignUp/Login to view account.", toastView);
                    replaceFragmentWithContainer(new LoginFragment());
                }

//                replaceFragmentWithContainer(new FavouritesFragment());
                // setNavigationBackground("#E10915");

                return true;

            }
        }
        /*else if (id == R.id.nav_Setting) {
            if (!(fragmentContainer instanceof SettingFragment)) {
                navigateToScreen(R.id.navigation_setting);
                drawer.closeDrawer(GravityCompat.START);
                //  setNavigationBackground("#E10915");

                return true;

            }
        } */
        else if (id == R.id.nav_become_a_seller) {


            Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
            becomeASellerIntent.setData(Uri.parse(AppConstants.URL_BECOME_A_SELLER));
            startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            //drawer.closeDrawer(GravityCompat.START);

            return true;

        } else if (id == R.id.nav_follow_us) {

            showFollowUsDialog(BaseActivity.this);


            return true;

        }

        /*else if (id == R.id.nav_login) {
            if (!(fragmentContainer instanceof LoginFragment)) {


                bottomMenu = navigation.getMenu();

                for (int i = 0; i < bottomMenu.size(); i++) {
                    menuItemBottom = bottomMenu.getItem(i);
                    menuItemBottom.setCheckable(false);
                }

                //   menuItemBottom = bottomMenu.findItem(R.id.navigation_shop);
                // menuItem1.setChecked(false);
                //   menuItemBottom.setCheckable(false);
               // menuItemBottom.setChecked(false);

                replaceFragmentWithContainer(new LoginFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");

                return true;

            }
        } */

        else if (id == R.id.navAbout) {
            if (!(fragmentContainer instanceof AboutFragment)) {

                bottomMenu = navigation.getMenu();

                for (int i = 0; i < bottomMenu.size(); i++) {
                    menuItemBottom = bottomMenu.getItem(i);
                    menuItemBottom.setCheckable(false);
                    menuItemBottom.setChecked(false);
                }
                replaceFragmentWithContainer(new AboutFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");
                return true;

            }
        } else if (id == R.id.navPrivacyPolicy) {
            if (!(fragmentContainer instanceof PrivacyPolicyFragment)) {

                bottomMenu = navigation.getMenu();

                for (int i = 0; i < bottomMenu.size(); i++) {
                    menuItemBottom = bottomMenu.getItem(i);
                    menuItemBottom.setCheckable(false);
                    menuItemBottom.setChecked(false);
                }
                replaceFragmentWithContainer(new PrivacyPolicyFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");
                return true;

            }
        } else if (id == R.id.navTermsAndCondition) {
            if (!(fragmentContainer instanceof TermsAndConditionFragment)) {

                bottomMenu = navigation.getMenu();

                for (int i = 0; i < bottomMenu.size(); i++) {
                    menuItemBottom = bottomMenu.getItem(i);
                    menuItemBottom.setCheckable(false);
                    menuItemBottom.setChecked(false);
                }
                replaceFragmentWithContainer(new TermsAndConditionFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");
                return true;

            }
        } else if (id == R.id.navContactUs) {
            if (!(fragmentContainer instanceof ContactUsFragment)) {

                bottomMenu = navigation.getMenu();

                for (int i = 0; i < bottomMenu.size(); i++) {
                    menuItemBottom = bottomMenu.getItem(i);
                    menuItemBottom.setCheckable(false);
                    menuItemBottom.setChecked(false);
                }
                replaceFragmentWithContainer(new ContactUsFragment());
                drawer.closeDrawer(GravityCompat.START);
                // setNavigationBackground("#E10915");
                return true;

            }
        } else if (id == R.id.navLogout) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showLogoutDialog(BaseActivity.this, "Logout", utils.getStringFromResourceId(R.string.are_you_sure_logout));

                }
            }, 200);

        }


        //enableHomeIcon(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Method to share either text or URL.
    private void shareTextUrl() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_compnay_share);

//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
//                b, "Title", null);
//        Uri imageUri = Uri.parse(path);

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/*");
        share.setType("text/plain");

        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.

        String Weblink = "https://circuit.pk/";
        String mobileAppLink = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
        String socialMediaLinks = "Social Media Pages:\n" + "Facebook: " + AppConstants.URL_CIRCUIT_PK_FACEBOOK_PAGE_URL + "\n" +
                "Twitter: " + AppConstants.URL_CIRCUIT_PK_TWITTER_PAGE_URL + "\n" +
                "Google Plus: " + AppConstants.URL_CIRCUIT_PK_GOOGLE_PLUS_PAGE_URL + "\n" +
                "Pinterest: " + AppConstants.URL_CIRCUIT_PK_PINTEREST_PAGE_URL + "\n" +
                "Youtube:" + AppConstants.URL_CIRCUIT_PK_YOUTUBE_PAGE_URL;

        String textDesc = "I am using circuit.pk app.Here, you can purchase electronic components in low cost.\n\n" +
                "Below are the links of Website and mobile app.\n";
        String linkDesc = textDesc + "Website: " + Weblink + "\n" + "Mobile App: " + mobileAppLink + "\n\n" + socialMediaLinks;

        share.putExtra(Intent.EXTRA_SUBJECT, "Circuit.pk App");
        share.putExtra(Intent.EXTRA_TEXT, linkDesc);
        share.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(b));

        startActivity(Intent.createChooser(share, "Share link"));
    }


    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
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

    boolean isEnable = true;

    private void showLogoutDialog(Context context, String title, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_cancel_dialog);

        Button cancel = dialog.findViewById(R.id.cancel);
        Button submit = dialog.findViewById(R.id.submit);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);

        txtMessage.setText("" + message);
        txtTitle.setText("" + title);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (sessionManager.getKeyIsFacebookLogin() || sessionManager.isSignUp() || sessionManager.isLoggedIn()) {
                if (sessionManager.getKeyIsFacebookLogin()) {
                    if (utils.isInternetAvailable()) {
                        if (isEnable) {
                            isEnable = false;
                            fb_button.performClick();
                            //LoginManager.getInstance().logOut();
                            callFacebook(dialog);

                        }
                    } else {
                        toastUtil.showToastShortTime("Please connect to internet.", toastView);
                    }

                } else if (sessionManager.isLoggedIn()) {
                    if (isEnable) {
                        isEnable = false;

                        Menu menu = navigationView.getMenu();
                        MenuItem menuItem;

                        for (int i = 0; i < menu.size(); i++) {
                            menuItem = menu.getItem(i);
                            menuItem.setChecked(false);
                        }

                        sessionManager.clearAll();
                        hideLogoutOption();
                        toastUtil.showToastShortTime("Logout Successfully.", toastView);
                        setUserDetails();
                        dialog.dismiss();
                        isEnable = true;
                        //   finish();

                    }
                } else {
                    isEnable = true;
                    toastUtil.showToastShortTime("Please login first.", toastView);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void callFacebook(final Dialog dialog) {

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                try {


                    if (currentAccessToken == null) {
                        dialog.dismiss();
                        isEnable = true;
                        sessionManager.clearAll();
                        setUserDetails();
                        hideLogoutOption();
                        toastUtil.showToastShortTime("Logout Successfully.", toastView);

                    }
                } catch (Exception ex) {

                }
            }
        };
        accessTokenTracker.startTracking();

    }

    private void showFollowUsDialog(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = dialog.getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_follow_us);


        ImageView imgFacebook = dialog.findViewById(R.id.imgFacebook);
        ImageView imgTwitter = dialog.findViewById(R.id.imgTwitter);
        ImageView imgGPlus = dialog.findViewById(R.id.imgGPlus);
        ImageView imgPinterest = dialog.findViewById(R.id.imgPinterest);
        ImageView imgYoutube = dialog.findViewById(R.id.imgYoutube);

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
                becomeASellerIntent.setData(Uri.parse(AppConstants.URL_CIRCUIT_PK_FACEBOOK_PAGE_URL));
                startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            }
        });

        imgTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
                becomeASellerIntent.setData(Uri.parse(AppConstants.URL_CIRCUIT_PK_TWITTER_PAGE_URL));
                startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            }
        });
        imgGPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
                becomeASellerIntent.setData(Uri.parse(AppConstants.URL_CIRCUIT_PK_GOOGLE_PLUS_PAGE_URL));
                startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            }
        });
        imgPinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
                becomeASellerIntent.setData(Uri.parse(AppConstants.URL_CIRCUIT_PK_PINTEREST_PAGE_URL));
                startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            }
        });
        imgYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becomeASellerIntent = new Intent(Intent.ACTION_VIEW);
                becomeASellerIntent.setData(Uri.parse(AppConstants.URL_CIRCUIT_PK_YOUTUBE_PAGE_URL));
                startActivity(Intent.createChooser(becomeASellerIntent, "Choose browser"));

            }
        });


        dialog.show();
    }

    public void enableHomeIcon(boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (b) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        }
    }

    public void disbaleHomeIcon(boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (b) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        }
    }


    private void setNavigationBackground(String color) {
        navigation.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserDetails();
    }

    public void hideLogoutOption() {
        Menu menu = navigationView.getMenu();
        MenuItem menuItem;


        menuItem = menu.getItem(10);
        menuItem.setVisible(false);

    }

    public void showLogoutOption() {
        Menu menu = navigationView.getMenu();
        MenuItem menuItem;


        menuItem = menu.getItem(10);
        menuItem.setVisible(true);
    }

    public void setUserDetails() {
        try {
            View headerView = navigationView.getHeaderView(0);
            TextView txtName = headerView.findViewById(R.id.txtUserName);
            // TextView txtEmail = headerView.findViewById(R.id.txtUserEmail);
            TextView txtPhoneNo = headerView.findViewById(R.id.txtUserPhoneNo);
            CircleImageView circleImageView = headerView.findViewById(R.id.img);
            ImageView imgFacebook = headerView.findViewById(R.id.imgFacebook);

            if (sessionManager.getKeyIsFacebookLogin()) {
                if (!utils.isTextNullOrEmpty(sessionManager.getusername())) {
                    txtName.setText(sessionManager.getusername());
                    txtName.setVisibility(View.VISIBLE);

                } else {
                    txtName.setVisibility(View.GONE);
                }

                if (!utils.isTextNullOrEmpty(sessionManager.getphoneno())) {
                    txtPhoneNo.setText(sessionManager.getphoneno());
                    txtPhoneNo.setVisibility(View.VISIBLE);

                } else {
                    txtPhoneNo.setVisibility(View.GONE);

                }


                if (sessionManager.getKeyIsFacebookLogin()) {
                    btnChangePhoto.setVisibility(View.VISIBLE);
                } else {
                    btnChangePhoto.setVisibility(View.GONE);

                }
                imgFacebook.setVisibility(View.VISIBLE);


                if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
                    Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
                    circleImageView.setImageBitmap(img);
                } else if (!utils.isTextNullOrEmpty(sessionManager.getKeyPicture())) {
                    //  Bitmap img = stringToBitMap(sessionManager.getKeyPicture());
                    // circleImageView.setImageBitmap(sessionManager.getKeyPicture());
//                    Glide.with(getApplicationContext()).load(sessionManager.getKeyPicture())
//                            .placeholder(R.drawable.icon_user_profile)
//                            .error(R.drawable.icon_user_profile)
//                            .into(circleImageView);
                    Picasso.with(getApplicationContext()).load(sessionManager.getKeyPicture()).into(circleImageView);


                    circleImageView.setVisibility(View.VISIBLE);

                } else {
                    circleImageView.setVisibility(View.VISIBLE);

                    circleImageView.setImageResource(R.drawable.icon_user_profile);

                }


//            if (!utils.isTextNullOrEmpty(sessionManager.getEmail())) {
//                txtEmail.setText(sessionManager.getEmail());
//                txtEmail.setVisibility(View.VISIBLE);
//
//            } else {
//                txtEmail.setVisibility(View.GONE);
//
//            }

            } else {
                if (!utils.isTextNullOrEmpty(sessionManager.getusername())) {
                    txtName.setText(sessionManager.getusername());
                    txtName.setVisibility(View.VISIBLE);

                } else {
                    txtName.setVisibility(View.GONE);
                }

                if (!utils.isTextNullOrEmpty(sessionManager.getphoneno())) {
                    txtPhoneNo.setText(sessionManager.getphoneno());
                    txtPhoneNo.setVisibility(View.VISIBLE);

                } else {
                    txtPhoneNo.setVisibility(View.GONE);

                }


                if (sessionManager.isLoggedIn() || sessionManager.isSignUp()) {
                    if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
                        Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
                        circleImageView.setImageBitmap(img);

                        circleImageView.setVisibility(View.VISIBLE);

                    } else {
                        circleImageView.setVisibility(View.VISIBLE);

                        circleImageView.setImageResource(R.drawable.icon_user_profile);

                    }
                    btnChangePhoto.setVisibility(View.VISIBLE);
                } else {
                    btnChangePhoto.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);

                    circleImageView.setImageResource(R.drawable.icon_user_profile);
                }

                imgFacebook.setVisibility(View.GONE);

//                if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
//                    Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
//                    circleImageView.setImageBitmap(img);
//
//                    circleImageView.setVisibility(View.VISIBLE);
//
//                } else {
//                    circleImageView.setVisibility(View.VISIBLE);
//
//                    circleImageView.setImageResource(R.drawable.icon_user_profile);
//
//                }


//            if (!utils.isTextNullOrEmpty(sessionManager.getEmail())) {
//                txtEmail.setText(sessionManager.getEmail());
//                txtEmail.setVisibility(View.VISIBLE);
//
//            } else {
//                txtEmail.setVisibility(View.GONE);
//
//            }

            }

            if (sessionManager.isLoggedIn() || sessionManager.isLoginViaFacebook()) {
                btnLogin.setText("Logout");

            } else {
                btnLogin.setText("Login");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void setSignUpDetails() {
        try {
            View headerView = navigationView.getHeaderView(0);
            TextView txtName = headerView.findViewById(R.id.txtUserName);
            // TextView txtEmail = headerView.findViewById(R.id.txtUserEmail);
            TextView txtPhoneNo = headerView.findViewById(R.id.txtUserPhoneNo);
            CircleImageView circleImageView = headerView.findViewById(R.id.img);
            ImageView imgFacebook = headerView.findViewById(R.id.imgFacebook);

            if (sessionManager.getKeyIsFacebookLogin()) {
                if (!utils.isTextNullOrEmpty(sessionManager.getusername())) {
                    txtName.setText(sessionManager.getusername());
                    txtName.setVisibility(View.VISIBLE);

                } else {
                    txtName.setVisibility(View.GONE);
                }

                if (!utils.isTextNullOrEmpty(sessionManager.getphoneno())) {
                    txtPhoneNo.setText(sessionManager.getphoneno());
                    txtPhoneNo.setVisibility(View.VISIBLE);

                } else {
                    txtPhoneNo.setVisibility(View.GONE);

                }
                if (sessionManager.getKeyIsFacebookLogin()) {
                    btnChangePhoto.setVisibility(View.VISIBLE);
                } else {
                    btnChangePhoto.setVisibility(View.GONE);

                }
                imgFacebook.setVisibility(View.VISIBLE);

                if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
                    Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
                    circleImageView.setImageBitmap(img);
                } else if (!utils.isTextNullOrEmpty(sessionManager.getKeyPicture())) {
                    //  Bitmap img = stringToBitMap(sessionManager.getKeyPicture());
                    // circleImageView.setImageBitmap(sessionManager.getKeyPicture());
//                    Glide.with(getApplicationContext()).load(sessionManager.getKeyPicture())
//                            .placeholder(R.drawable.icon_user_profile)
//                            .error(R.drawable.icon_user_profile)
//                            .into(circleImageView);
                    Picasso.with(getApplicationContext()).load(sessionManager.getKeyPicture()).into(circleImageView);


                    circleImageView.setVisibility(View.VISIBLE);

                } else {
                    circleImageView.setVisibility(View.VISIBLE);

                    circleImageView.setImageResource(R.drawable.icon_user_profile);

                }


//            if (!utils.isTextNullOrEmpty(sessionManager.getEmail())) {
//                txtEmail.setText(sessionManager.getEmail());
//                txtEmail.setVisibility(View.VISIBLE);
//
//            } else {
//                txtEmail.setVisibility(View.GONE);
//
//            }

            } else {
                if (!utils.isTextNullOrEmpty(sessionManager.getusername())) {
                    txtName.setText(sessionManager.getusername());
                    txtName.setVisibility(View.VISIBLE);

                } else {
                    txtName.setVisibility(View.GONE);
                }

                if (!utils.isTextNullOrEmpty(sessionManager.getphoneno())) {
                    txtPhoneNo.setText(sessionManager.getphoneno());
                    txtPhoneNo.setVisibility(View.VISIBLE);

                } else {
                    txtPhoneNo.setVisibility(View.GONE);

                }

                imgFacebook.setVisibility(View.GONE);

                circleImageView.setVisibility(View.VISIBLE);

                if (sessionManager.isLoggedIn() || sessionManager.isSignUp()) {
                    btnChangePhoto.setVisibility(View.VISIBLE);
                } else {
                    btnChangePhoto.setVisibility(View.GONE);

                }
                //circleImageView.setImageResource(R.drawable.icon_user_profile);

//                if (!utils.isTextNullOrEmpty(sessionManager.getTakePhoto())) {
//                    Bitmap img = stringToBitMap(sessionManager.getTakePhoto());
//                    circleImageView.setImageBitmap(img);
//
//                    circleImageView.setVisibility(View.VISIBLE);
//
//                }
//                else {
//                    circleImageView.setVisibility(View.VISIBLE);
//
//                    circleImageView.setImageResource(R.drawable.icon_user_profile);
//
//                }


//            if (!utils.isTextNullOrEmpty(sessionManager.getEmail())) {
//                txtEmail.setText(sessionManager.getEmail());
//                txtEmail.setVisibility(View.VISIBLE);
//
//            } else {
//                txtEmail.setVisibility(View.GONE);
//
//            }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void setTotalBottomNavigationCount(int index, long counts) {
        final Menu menu = navigation.getMenu();
        if (counts == 0) {
            menu.getItem(index).setTitle("Favorite");

        } else {
            menu.getItem(index).setTitle("Favorite(" + counts + ")");
        }
    }

    public void showHideBottomNavigationCount(int index) {
        //  final Menu menu = navigation.getMenu();
        // menu.getItem(index).setTitle("Favorite");
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


//    public void setUserDetails(String firstName, String lastName, String imgURL) {
//        try {
//            View headerView = navigationView.getHeaderView(0);
//            TextView txtName = headerView.findViewById(R.id.txtUserName);
//            // TextView txtEmail = headerView.findViewById(R.id.txtUserEmail);
//            TextView txtPhoneNo = headerView.findViewById(R.id.txtUserPhoneNo);
//
//            if (!utils.isTextNullOrEmpty(firstName)) {
//                txtName.setText(firstName);
//                txtName.setVisibility(View.VISIBLE);
//
//            } else {
//                txtName.setVisibility(View.GONE);
//            }
//
//            if (!utils.isTextNullOrEmpty(sessionManager.getphoneno())) {
//                txtPhoneNo.setText(sessionManager.getphoneno());
//                txtPhoneNo.setVisibility(View.VISIBLE);
//
//            } else {
//                txtPhoneNo.setVisibility(View.GONE);
//
//            }
//
////            if (!utils.isTextNullOrEmpty(sessionManager.getEmail())) {
////                txtEmail.setText(sessionManager.getEmail());
////                txtEmail.setVisibility(View.VISIBLE);
////
////            } else {
////                txtEmail.setVisibility(View.GONE);
////
////            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    public ImageView getImgLogin() {
        return imgLogin;
    }

    public ImageView getImgShoppingCart() {
        return imgShoppingCart;
    }

    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void makeFlyAnimation(ImageView targetView, final int cartsCount) {

        //RelativeLayout destView = (RelativeLayout) findViewById(R.id.cartRelativeLayout);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(700).setDestView(badge2).setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // addItemToCart(cartsCount);
                //  snackUtil.showSnackBarLongTimeIsShown(findViewById(android.R.id.content), "Item added to cart successfully.");
                // Toast.makeText(getApplicationContext(), "Continue Shopping...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();


    }

    public void addItemToCart(int cartsCount) {
        //   txtAddToCartNotify.setText(String.valueOf(++itemCounter));
        // toastUtil.showToastShortTimeForDismiss("Item added to cart successfully");
        txtAddToCartNotify.setText(String.valueOf(cartsCount));
//        Toast toast = new Toast(getApplicationContext());
//        toast.setText("Item added to cart successfully");
//        toast.setDuration(Toast.LENGTH_SHORT);
//        if (!toast.getView().isShown()) {
//            toast.show();
//        }


    }

    public void showTotalCartsCount(int cartsCount) {
        txtAddToCartNotify.setText(String.valueOf(cartsCount));

    }


    public void removeItemToCart(int cartsCount) {
        txtAddToCartNotify.setText(String.valueOf(cartsCount));
    }


    public void AnimateCartTextview() {
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cart_shake_animation);
        imgShoppingCart.setAnimation(shake);
    }

    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int hasReadPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if ((hasReadPermission != PackageManager.PERMISSION_GRANTED) ||
                (hasWritePermission != PackageManager.PERMISSION_GRANTED) ||
                (camera != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
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
                    int camera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                    int hasReadPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int hasWritePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if ((hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                            (hasWritePermission == PackageManager.PERMISSION_GRANTED) &&
                            (camera == PackageManager.PERMISSION_GRANTED)
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

    private void showPictureDialog() {
        try {

            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Select photo from gallery",
                    "Capture photo from camera"};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    choosePhotoFromGallary();
                                    break;
                                case 1:
                                    takePhotoFromCamera();
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        } catch (Exception e) {

        }
    }

    public void choosePhotoFromGallary() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        } catch (Exception e) {

        }
    }

    private void takePhotoFromCamera() {
        try {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;

                    Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(contentURI),
                            null, options);

                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    //  String path = saveImage(bitmap);

                    profilePhoto.setImageBitmap(bitmap);
                    toastUtil.showToastShortTime("Profile picture updated", toastView);
                    String strImg = bitMapToString(bitmap);
                    sessionManager.setTakePhoto(strImg);

                } catch (IOException e) {
                    e.printStackTrace();
                    toastUtil.showToastShortTime("Error while updating profile picture", toastView);

                }
            }

        } else if (requestCode == CAMERA) {
            try {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");


                ByteArrayOutputStream out = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 90, out);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

                profilePhoto.setImageBitmap(decoded);

                String strImg = bitMapToString(decoded);
                sessionManager.setTakePhoto(strImg);

                //   saveImage(thumbnail);
                toastUtil.showToastShortTime("Profile picture updated", toastView);
            } catch (Exception e) {
                toastUtil.showToastShortTime("Error while updating profile picture", toastView);
            }
        }


    }

    public String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public String saveImage(Bitmap myBitmap) {
        try {


            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            try {
                File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();

                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {

        }
        return "";

    }

}










