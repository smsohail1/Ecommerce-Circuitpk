package com.xekera.Ecommerce.ui.dashboard;

import com.xekera.Ecommerce.R;
import com.xekera.Ecommerce.util.SessionManager;
import com.xekera.Ecommerce.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DashboardPresenter implements DashboardMVP.Presenter {
    private DashboardMVP.View view;
    private DashboardMVP.Model model;
    private SessionManager sessionManager;
    private Utils utils;

    public DashboardPresenter(DashboardMVP.Model model, SessionManager sessionManager, Utils utils) {
        this.model = model;
        this.sessionManager = sessionManager;
        this.utils = utils;
    }

    @Override
    public void setView(DashboardMVP.View view) {
        this.view = view;
    }


    @Override
    public void setDashboardItems() {
//        List<DashboardItem> homeItems = new ArrayList<>();
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_booking));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_booking));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_retake));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_reports));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_fetch_data));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_dp_user));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_barcode));
//        homeItems.add(new DashboardItem(R.string.app_name, R.drawable.icon_security));
      //  homeAdapter = new DashboardAdapter(homeItems, this);
        //view.setHomeRecyclerViewAdapter(homeAdapter);
    }

//    @Override
//    public void onHomeItemClick(DashboardItem homeItem) {
//        switch (homeItem.getNameResId()) {
//
//
//
////            case R.string.sync: {
////                view.startDeliveryBackgroundService();
////                break;
////            }
////            case R.string.dp_user:{
////                view.showDPUserLoginPopup();
////                break;
////            }
//       }
//    }

    private void receivedNull(String apiJsonNodeName) {
        view.hideProgressDialogPleaseWait();
        view.showToastLongTime(apiJsonNodeName + " is null");
    }
}











