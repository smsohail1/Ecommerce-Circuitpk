package com.xekera.Ecommerce.ui;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dashboard.BottomNavigationBehavior;
import com.xekera.Ecommerce.ui.dashboard.DashboardFragment;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.FragmentFavourites;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.HistoryFragment;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.login.LoginActivity;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.ui.signup.SignupFragment;
import com.xekera.Ecommerce.util.*;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.imgBack)
    protected ImageView imgBack;
    @BindView(R.id.txtScreenTitle)
    protected TextView txtScreenTitle;
    @BindView(R.id.imgLogin)
    protected ImageView imgLogin;
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
        navigationView.setNavigationItemSelectedListener(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // ((BaseActivity) getActivity()).addDashboardFragment(new ShopFragment());

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

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
        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                // startActivity(i);

                try {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                    if (!(fragment instanceof LoginFragment || fragment instanceof SignupFragment)) {
                        addFragment(new LoginFragment());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        badge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (!(fragment instanceof AddToCartFragment)) {
                    navigation.setSelectedItemId(R.id.navigation_cart);
                    // replaceFragmentWithContainer(new AddToCartFragment());
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


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment fragment;
            Fragment fragmentContainer = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

            switch (item.getItemId()) {
                case R.id.navigation_shop:

                    if (!(fragmentContainer instanceof ShopFragment)) {

                        fragment = new ShopFragment();
                        replaceFragmentWithContainer(fragment);
                    }
                    return true;
                case R.id.navigation_favourite:
                    //    Toast.makeText(getActivity(), "Wishlist is selected", Toast.LENGTH_SHORT).show();
                    //  ((BaseActivity) getActivity()).popBackstack();
                    //((BaseActivity) getActivity()).addDashboardFragment(new FragmentFavourites());

                    if (!(fragmentContainer instanceof FragmentFavourites)) {

                        fragment = new FragmentFavourites();
                        replaceFragmentWithContainer(fragment);
                    }
                    return true;
                case R.id.navigation_cart:

                    if (!(fragmentContainer instanceof AddToCartFragment)) {

                        fragment = new AddToCartFragment();
                        replaceFragmentWithContainer(fragment);
                    }
                    //  navigation.setSelectedItemId(R.id.navigation_History);

                    return true;

                case R.id.navigation_History:
                    if (!(fragmentContainer instanceof HistoryFragment)) {
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
        if (fragment instanceof AddToCartFragment || fragment instanceof ShopFragment ||
                fragment instanceof HistoryFragment ||
                fragment instanceof FragmentFavourites) {
            showBottomNavigation();
            if (fragment instanceof AddToCartFragment) {
                navigation.setSelectedItemId(R.id.navigation_cart);
            } else if (fragment instanceof ShopFragment) {
                navigation.setSelectedItemId(R.id.navigation_shop);
            } else if (fragment instanceof HistoryFragment) {
                navigation.setSelectedItemId(R.id.navigation_History);
            } else if (fragment instanceof FragmentFavourites) {
                navigation.setSelectedItemId(R.id.navigation_favourite);
            }

        }
    }

    public void navigateToScreen(final int menuId) {
        showBottomNavigation();
        View view = navigation.findViewById(menuId);
        view.performClick();
    }

//        if (fragment instanceof ShopDetailsFragment) {
//           // ((ShopDetailsFragment) fragment).setTitle();
//            //((ShopDetailsFragment) fragment).showBackImageIcon();
//            //((ShopDetailsFragment) fragment).hideLoginIcon();
//            //((ShopDetailsFragment) fragment).hideHumbergIcon();
//        } else if (fragment instanceof ShopFragment) {
//            //((ShopFragment) fragment).setTitle();
//            //((ShopFragment) fragment).showLoginIcon();
//            //((ShopFragment) fragment).showHumbergIcon();
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
            //((ShopFragment) fragment).showLoginIcon();
            //((ShopFragment) fragment).showHumbergIcon();
            //((ShopFragment) fragment).hideBackImageIcon();
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
            showBottomNavigation();
            //hideHumberIcon();
            popBackstack();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

        } else if (fragment instanceof DashboardFragment) {
            enableHomeIcon(true);

            if (backPressedOnce) {
                this.finish();
            }

            backPressedOnce = true;
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
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


        if (id == R.id.navShare) {
            shareTextUrl();
        }
        if (id == R.id.navLogout) {
            showLogoutDialog(this, "Logout", utils.getStringFromResourceId(R.string.are_you_sure_logout));
        }


        //enableHomeIcon(false);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Method to share either text or URL.
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Circuit.pk App");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share App link"));
    }

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
                //sessionManager.logoutUser();
                //  startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                finish();
                dialog.dismiss();
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


    @Override
    protected void onResume() {
        super.onResume();
        //  setActionItems();
//        try {
//            View headerView = navigationView.getHeaderView(0);
//            TextView txtCourierName = headerView.findViewById(R.id.txtCourierName);
//            txtCourierName.setText(sessionManager.getCourierName());
//            TextView txtCourierDetails = headerView.findViewById(R.id.txtCourierDetails);
//            txtCourierDetails.setText(sessionManager.getCourierStation() + " | " +
//                    sessionManager.getCourierRoute());
//            TextView txtAppVersion = headerView.findViewById(R.id.txtAppVersion);
//            txtAppVersion.setText("Version " + String.valueOf(BuildConfig.VERSION_NAME));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

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
}










