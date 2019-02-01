package com.xekera.Ecommerce.ui.dashboard_shopping;

import android.content.Context;
import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.Category;
import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
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
      /*  List<DashboardItem> homeItems = new ArrayList<>();
        homeItems.add(new DashboardItem(R.string.ardino, R.drawable.arduino));
        homeItems.add(new DashboardItem(R.string.ardino_kit, R.drawable.arduino_kit));
        homeItems.add(new DashboardItem(R.string.audio_amplifier, R.drawable.
                audio_amplifier));
        homeItems.add(new DashboardItem(R.string.battery, R.drawable.battery));
        homeItems.add(new DashboardItem(R.string.ble, R.drawable.ble));
        homeItems.add(new DashboardItem(R.string.charger, R.drawable.charger));
        homeItems.add(new DashboardItem(R.string.consumer_electronics, R.drawable.consumer_electronics));
        homeItems.add(new DashboardItem(R.string.development_board, R.drawable.development_board));
        homeItems.add(new DashboardItem(R.string.discrete_and_competent, R.drawable.discrete_and_competent));
        homeItems.add(new DashboardItem(R.string.esp, R.drawable.esp));
        homeItems.add(new DashboardItem(R.string.kit, R.drawable.kit));
        homeItems.add(new DashboardItem(R.string.lcd_circuit, R.drawable.lcd_circuit));
        homeItems.add(new DashboardItem(R.string.led_module, R.drawable.led_module));
        homeItems.add(new DashboardItem(R.string.micro_sd_card, R.drawable.micro_sd_card));
        homeItems.add(new DashboardItem(R.string.module, R.drawable.module));
        homeItems.add(new DashboardItem(R.string.motor, R.drawable.motor));
        homeItems.add(new DashboardItem(R.string.motor_driver_module, R.drawable.motor_driver_module));
        homeItems.add(new DashboardItem(R.string.power_supply, R.drawable.power_supply));
        homeItems.add(new DashboardItem(R.string.programmer, R.drawable.programmer));
        homeItems.add(new DashboardItem(R.string.rectifier, R.drawable.rectifier));
        homeItems.add(new DashboardItem(R.string.rfid, R.drawable.rfid));
        homeItems.add(new DashboardItem(R.string.scr, R.drawable.scr));
        homeItems.add(new DashboardItem(R.string.sensor_module, R.drawable.sensor_module));
        homeItems.add(new DashboardItem(R.string.tool, R.drawable.tool));
        homeItems.add(new DashboardItem(R.string.transistor, R.drawable.transistor));
        homeItems.add(new DashboardItem(R.string.triacs, R.drawable.triacs));
        homeItems.add(new DashboardItem(R.string.voltage_regulator, R.drawable.voltage_regulator));
        homeItems.add(new DashboardItem(R.string.voltage_regulator_module, R.drawable.voltage_regulator_module));
        homeItems.add(new DashboardItem(R.string.wire, R.drawable.wire));
        homeItems.add(new DashboardItem(R.string.wire_less, R.drawable.wireless));


        homeAdapter = new DashboardAdapter(homeItems, this, context);
        view.setHomeRecyclerViewAdapter(homeAdapter);*/
    }

    @Override
    public void setDashboardItemsDetails(final Context context) {
        model.getDashboardItemsDetails(new INetworkListGeneral<CategoryResponse>() {
            @Override
            public void onSuccess(CategoryResponse response) {
                if (response == null) {
                    view.showToastShortTime("Error while fetch data");
                    view.hideCircularProgressBar();
                    view.getTotalCartsCounts();
                    return;
                } else {
                    List<Category> categoryList = response.getCategories();
                    if (categoryList == null) {
                        view.showToastShortTime("Error while fetch data");
                        view.hideCircularProgressBar();
                        view.getTotalCartsCounts();
                        return;
                    }
                    if (categoryList.size() > 0) {

                        homeAdapter = new DashboardAdapter(categoryList, context, new DashboardAdapter.IDashboardAdapter() {
                            @Override
                            public void onHomeItemClick(Category categories) {
                                view.showShoppingDetailPage(categories);

                            }
                        });
                        view.setHomeRecyclerViewAdapter(homeAdapter);
                        view.showData();
                        view.getTotalCartsCounts();

                    } else {
                        view.showToastShortTime("No category found");
                        view.hideCircularProgressBar();
                        view.getTotalCartsCounts();

                        return;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideCircularProgressBar();
                if (t.getMessage() != null) {
                    view.showToastShortTime(t.getMessage());
                } else {
                    view.showToastShortTime("Error while fetching category.");
                }
                view.getTotalCartsCounts();

            }
        });
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
    public void onHomeItemClick(Category categories) {
        // switch (homeItem.getNameResId()) {
        view.showShoppingDetailPage(categories);

//            case R.string.ardino: {
//                view.startDeliveryBackgroundService();
//                break;
//            }

        //}
    }

}