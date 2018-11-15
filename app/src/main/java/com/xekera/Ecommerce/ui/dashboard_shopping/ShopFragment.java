package com.xekera.Ecommerce.ui.dashboard_shopping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.indicator)
    protected TabLayout indicator;
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
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);

        try {
            setTitle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void initializeViews(View v) {
        ButterKnife.bind(this, v);
        presenter.setView(this);

        progressDialogControllerPleaseWait = new ProgressCustomDialogController(getActivity(), R.string.please_wait);


        color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.GREEN);
        color.add(Color.BLUE);

        colorName = new ArrayList<>();
        colorName.add("RED");
        colorName.add("GREEN");
        colorName.add("BLUE");


        img = new ArrayList<>();
        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/o/e/oea-o-5mu1tcbm201606236016__46.jpg");
        img.add("https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=8b209b87443cca9d7d140ec0dd49fe21&w=1000&q=80");
        img.add("https://megaeshop.pk/media/catalog/product/cache/1/image/7dfa28859a690c9f1afbf103da25e678/1/2/12v-battery-intelligent-automatic-charging-controller-board-anti-overcharge-protection-charger-discharging-control-relay-module.jpg");
        presenter.setViewPagerItems(getActivity(), color, colorName, img);

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewHome.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));

        presenter.setDashboardItems();

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
    public void showShoppingDetailPage(DashboardItem homeItem) {

        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        ((BaseActivity) getActivity()).addFragmentWithLockedHumberIcon(shopDetailsFragment.newInstance(utils.getStringFromResourceId(homeItem.getNameResId())));

    }

    @Override
    public void setDashboardViewPagerAdapter(SliderAdapter sliderAdapter) {
        viewPager.setAdapter(sliderAdapter);
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 5000, 5000);

    }


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


}


