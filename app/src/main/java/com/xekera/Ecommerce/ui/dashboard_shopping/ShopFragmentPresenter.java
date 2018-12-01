package com.xekera.Ecommerce.ui.dashboard_shopping;

import android.content.Context;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartModel;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ShopFragmentPresenter implements ShopFragmentMVP.Presenter, DashboardAdapter.IDashboardAdapter {
    private ShopFragmentMVP.View view;
    private ShopFragmentMVP.Model model;
    private DashboardAdapter homeAdapter;
    private SliderAdapter sliderAdapter;
    private SessionManager sessionManager;
    private Utils utils;

    public ShopFragmentPresenter(ShopFragmentMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(ShopFragmentMVP.View view) {
        this.view = view;
    }


    @Override
    public void setDashboardItems(Context context) {
        List<DashboardItem> homeItems = new ArrayList<>();
        homeItems.add(new DashboardItem(R.string.ardino, R.drawable.icon_booking));
        homeItems.add(new DashboardItem(R.string.capacitor, R.drawable.
                icon_clear_data));
        homeItems.add(new DashboardItem(R.string.senors, R.drawable.icon_retake));
        homeItems.add(new DashboardItem(R.string.wires, R.drawable.icon_reports));
        homeItems.add(new DashboardItem(R.string.sd_card, R.drawable.icon_fetch_data));
        homeItems.add(new DashboardItem(R.string.lcd, R.drawable.icon_dp_user));
        homeItems.add(new DashboardItem(R.string.motor_driver, R.drawable.icon_barcode));
        homeItems.add(new DashboardItem(R.string.charges, R.drawable.icon_security));
        homeItems.add(new DashboardItem(R.string.motor_wheel, R.drawable.icon_security));
        homeAdapter = new DashboardAdapter(homeItems, this, context);
        view.setHomeRecyclerViewAdapter(homeAdapter);
    }

    @Override
    public void setViewPagerItems(Context context, List<Integer> color,
                                  List<String> colorName, List<String> img
    ) {
        sliderAdapter = new SliderAdapter(context, color, colorName, img);
        view.setDashboardViewPagerAdapter(sliderAdapter);


    }

    @Override
    public void getTotalCounts() {
        model.getCartDetails(new ShopFragmentModel.IFetchCartDetailsList() {
            @Override
            public void onCartDetailsReceived(List<AddToCart> addToCarts) {
                if (addToCarts == null || addToCarts.size() == 0) {
                    view.setCounts(0);

                    return;
                } else {
                    view.setCounts(addToCarts.size());

                }
            }

            @Override
            public void onErrorReceived(Exception ex) {
                ex.printStackTrace();

                view.showToastShortTime(ex.getMessage());
            }
        });
    }

    @Override
    public void onHomeItemClick(DashboardItem homeItem) {
        // switch (homeItem.getNameResId()) {
        view.showShoppingDetailPage(homeItem);

//            case R.string.ardino: {
//                view.startDeliveryBackgroundService();
//                break;
//            }

        //}
    }

}