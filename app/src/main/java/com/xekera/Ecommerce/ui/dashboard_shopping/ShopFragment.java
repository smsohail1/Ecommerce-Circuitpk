package com.xekera.Ecommerce.ui.dashboard_shopping;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.xekera.Ecommerce.App;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;
import com.xekera.Ecommerce.util.*;

import javax.inject.Inject;
import java.util.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements ShopFragmentMVP.View {
    @BindView(R.id.recyclerViewHome)
    protected RecyclerView recyclerViewHome;
//    @BindView(R.id.viewPager)
//    protected ViewPager viewPager;
//    @BindView(R.id.indicator)
//    protected TabLayout indicator;
//
    // @BindView(R.id.slider)
    // protected SliderLayout sliderLayout;

    @Inject
    protected ShopFragmentMVP.Presenter presenter;
    @Inject
    protected Utils utils;
    @Inject
    protected ToastUtil toastUtil;
    @Inject
    protected SessionManager sessionManager;


    List<Integer> color;
    List<String> colorName;
    List<String> img;

    private ProgressCustomDialogController progressDialogControllerPleaseWait;


    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        //  requestPermissions();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        // ((BaseActivity) getActivity()).showBottomNavigation();

//        if (mPermissionDenied) {
//            // Permissions were not granted
//            showMissingPermissionError();
//            mPermissionDenied = false;
//        }
//
        try {
            //  setTitle();
            //  showHumbergIcon();
            // showLoginIcon();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showLoginIcon() {
        ((BaseActivity) getActivity()).showLoginIcon();
    }


    public void setTitle() {
        ((BaseActivity) getActivity()).setTitle(getString(R.string.shop_dashboard));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        initializeViews(v);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        // ((BaseActivity) getActivity()).addDashboardFragment(new ShopFragment());
//
//        // attaching bottom sheet behaviour - hide / show on scroll
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

//        Fragment fragment;
//        fragment = new ShopFragment();
//        addFragment(fragment);

    }


    private void addFragment(Fragment fragment) {
        // load fragment
        if (fragment != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer2, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);


//        setTitle();
//        showHumbergIcon();
//        showLoginIcon();


//        color = new ArrayList<>();
//        color.add(Color.RED);
//        color.add(Color.GREEN);
//        color.add(Color.BLUE);
//
//        colorName = new ArrayList<>();
//        colorName.add("RED");
//        colorName.add("GREEN");
//        colorName.add("BLUE");
//
//
//        img = new ArrayList<>();
//        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
//        img.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
//        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
//        presenter.setViewPagerItems(getActivity(), color, colorName, img);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewHome.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));

        presenter.setDashboardItems(getActivity());


        presenter.getTotalCounts();
        // setViewPagerItems();

    }


//    private void setViewPagerItems() {
//        HashMap<String, String> Hash_file_maps = new HashMap<>();
//
//        Hash_file_maps.put("Android CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
//        Hash_file_maps.put("Android Donut", "http://androidblog.esy.es/images/donut-2.png");
//        Hash_file_maps.put("Android Eclair", "http://androidblog.esy.es/images/eclair-3.png");
//        Hash_file_maps.put("Android Froyo", "http://androidblog.esy.es/images/froyo-4.png");
//        Hash_file_maps.put("Android GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");
//
//        Picasso picasso = Picasso.with(getActivity());
//        picasso.load("http://androidblog.esy.es/images/cupcake-1.png")
//                .placeholder(R.drawable.ic_launcher_background)
//        .resize(100, 100);
//       // defaultSliderView.setPicasso(picasso);
//
//      //  for (String name : Hash_file_maps.keySet()) {
//
//
//            TextSliderView textSliderView = new TextSliderView(getActivity());
//          //  textSliderView
//                    //.description(name)
//                 //   .image(R.drawable.ic_launcher_background)
//                    //.setScaleType(BaseSliderView.ScaleType.Fit)
//                   // .setOnSliderClickListener(this).setPicasso(picasso);
////            textSliderView.bundle(new Bundle());
////            textSliderView.getBundle()
////                    .putString("extra", name);
//        textSliderView.setPicasso(picasso);
//            sliderLayout.addSlider(textSliderView);
//       // }
//    //    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//  //      sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
////        sliderLayout.setCustomAnimation(new DescriptionAnimation());
//        sliderLayout.setDuration(9000);
//        sliderLayout.addOnPageChangeListener(this);
//
//    }
//


    public void showHumbergIcon() {
        ((BaseActivity) getActivity()).showHumberIcon();

    }


    public void hideBackImageIcon() {
        ((BaseActivity) getActivity()).hideBackImageIcon();

    }

    @Override
    public void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter) {
        recyclerViewHome.setAdapter(homeAdapter);
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
    public void showShoppingDetailPage(final DashboardItem homeItem) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
                ((BaseActivity) getActivity()).replaceFragment(shopDetailsFragment.newInstance(utils.getStringFromResourceId(homeItem.getNameResId())));

            }
        }, 200);
    }

    @Override
    public void setDashboardViewPagerAdapter(SliderAdapter sliderAdapter) {
//        viewPager.setAdapter(sliderAdapter);
//        indicator.setupWithViewPager(viewPager, true);
//
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new SliderTimer(), 5000, 5000);

    }

    @Override
    public void setCounts(int counts) {
        ((BaseActivity) getActivity()).showTotalCartsCount(counts);

    }


/*
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < color.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (Exception e) {

            }
        }
    }
*/


    // PERMISSIONS CODE
    final private int REQUEST_CODE_ASK_PERMISSIONS = 125;
    private boolean mPermissionDenied = false;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        // int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
        //int hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        // int hasCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        // int hasReceiveSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
        // int hasReadSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
        //  int hasSendSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);


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
            //permissionsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_CODE_ASK_PERMISSIONS:
                    if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                            (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                        // Permission Allowed
                        // int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
                        int hasReadPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        int hasWritePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
//                    int hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
//                    int hasCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
//                    int hasReceiveSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
//                    int hasReadSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);
//                    int hasSendSMSPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

                        if ((hasReadPermission == PackageManager.PERMISSION_GRANTED) &&
                                (hasWritePermission == PackageManager.PERMISSION_GRANTED)
                                ) {
                            //permissionsGranted();
                        }
                    } else {
                        // Permission Denied
                        mPermissionDenied = true;
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        } catch (Exception e) {

        }
    }


    private void showMissingPermissionError() {
        toastUtil.showToastLongTime("These permissions are required");
        //getActivity().finish();
    }


}


