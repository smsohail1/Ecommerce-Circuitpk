package com.xekera.Ecommerce.di.component;

import com.xekera.Ecommerce.di.module.*;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.LoginBaseActivity;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dashboard.DashboardFragment;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.CartFragment;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.FragmentFavourites;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.HistoryFragment;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.ui.signup.SignupFragment;
import dagger.Component;

import javax.inject.Singleton;


@Component(modules = {
        AppModule.class,
        UtilModule.class,
        RetrofitModule.class,
        RoomModule.class,
        DashboardModule.class,
        LoginModule.class,
        SignupModule.class,
        ShopModule.class,
        ShopDetailsModule.class,
        ShopCardSelectedModule.class,
        AddToCartModule.class,
        DeliveyBillingDetailsModule.class
})
@Singleton
public interface AppComponent {


    void inject(BaseActivity baseActivity);

    void inject(LoginBaseActivity loginBaseActivity);

    void inject(ShopFragment shopFragment);

    void inject(ShopDetailsFragment shopDetailsFragment);

    void inject(CartFragment cartFragment);

    void inject(HistoryFragment historyFragment);

    void inject(FragmentFavourites fragmentFavourites);

    void inject(DashboardFragment dashboardFragment);


    void inject(LoginFragment loginFragment);

    void inject(SignupFragment signupFragment);

    void inject(ShopCardSelectedFragment shopCardSelectedFragment);

    void inject(AddToCartFragment addToCartFragment);

    void inject(DeliveyBillingDetailsFragment deliveyBillingDetailsFragment);


}












