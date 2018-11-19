package com.xekera.Ecommerce.ui.dashboard_shopping;


import android.content.Context;
import android.transition.Slide;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.adapter.DashboardAdapter;
import com.xekera.Ecommerce.ui.dashboard_shopping.model.DashboardItem;

import java.util.List;

public interface ShopFragmentMVP {


    interface View {
        void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter);

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showShoppingDetailPage(DashboardItem homeItem);

        void setDashboardViewPagerAdapter(SliderAdapter sliderAdapter);

    }

    interface Presenter {
        void setView(ShopFragmentMVP.View view);

        void setDashboardItems(Context context);

        void setViewPagerItems(Context context, List<Integer> color,
                               List<String> colorName,List<String> img);
    }

    interface Model {


    }
}
