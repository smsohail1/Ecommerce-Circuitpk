package com.xekera.Ecommerce.ui.continue_shopping;


//import android.content.Context;
//import com.xekera.Ecommerce.data.rest.INetworkListGeneral;

import com.xekera.Ecommerce.data.rest.response.Category;
import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
import com.xekera.Ecommerce.data.rest.response.add_to_cart_response.AddToCartResponse;
import com.xekera.Ecommerce.ui.adapter.SliderAdapter;
//import com.xekera.Ecommerce.ui.continue_shopping.adapter.DashboardAdapter;

import android.content.Context;
import com.xekera.Ecommerce.data.rest.INetworkListGeneral;
import com.xekera.Ecommerce.data.rest.response.CategoryResponse;
import com.xekera.Ecommerce.ui.continue_shopping.adapter.DashboardAdapter;

import java.util.List;

public interface ShopFragmentMVP {


    interface View {
        void setHomeRecyclerViewAdapter(DashboardAdapter homeAdapter);

        void showProgressDialogPleaseWait();

        void hideProgressDialogPleaseWait();

        void showToastShortTime(String message);

        void showToastLongTime(String message);

        void showShoppingDetailPage(Category homeItem);

        void setDashboardViewPagerAdapter(SliderAdapter sliderAdapter);

        void setCounts(int counts);

        void hideCircularProgressBar();

        void showCircularProgressBar();

        void getTotalCartsCounts();

        void showData();


    }

    interface Presenter {
        void setView(ShopFragmentMVP.View view);

        void setDashboardItems(Context context);

        void setDashboardItemsDetails(Context context);

        void setViewPagerItems(Context context, List<Integer> color,
                               List<String> colorName, List<String> img);

        void getTotalCounts();

    }

    interface Model {
        void getCartDetails(ShopFragmentModel.IFetchCartDetailsList iFetchCartDetailsList);

        void getDashboardItemsDetails(INetworkListGeneral<CategoryResponse> iNetworkListGeneral);

        void fetchCarts(String randomKey, INetworkListGeneral<AddToCartResponse> iNetworkListGeneral);

    }
}
