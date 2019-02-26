package com.xekera.Ecommerce.di.component;

import com.xekera.Ecommerce.di.module.*;
import com.xekera.Ecommerce.ui.BaseActivity;
import com.xekera.Ecommerce.ui.LoginBaseActivity;
import com.xekera.Ecommerce.ui.about.AboutFragment;
import com.xekera.Ecommerce.ui.account.AccountFragment;
import com.xekera.Ecommerce.ui.add_to_cart.AddToCartFragment;
import com.xekera.Ecommerce.ui.billing_total_amount_view.BillingTotalAmountViewFragment;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShopFragment;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingFragment;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShoppingModel;
import com.xekera.Ecommerce.ui.dasboard_shopping_details.ShopDetailsFragment;
import com.xekera.Ecommerce.ui.dashboard.DashboardFragment;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.CartFragment;
import com.xekera.Ecommerce.ui.dashboard.dashboard_screen.FragmentFavourites;
import com.xekera.Ecommerce.ui.dashboard_shopping.ShopFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.DeliveyBillingDetailsFragment;
import com.xekera.Ecommerce.ui.delivery_billing_details.stripe.StripePaymentActivity;
import com.xekera.Ecommerce.ui.favourites.FavouritesFragment;
import com.xekera.Ecommerce.ui.history.HistoryFragment;
import com.xekera.Ecommerce.ui.history.history_description.HistoryDesciptionFragment;
import com.xekera.Ecommerce.ui.login.LoginFragment;
import com.xekera.Ecommerce.ui.search_all_products.SearchAllProductsFragment;
import com.xekera.Ecommerce.ui.setting.SettingFragment;
import com.xekera.Ecommerce.ui.shop_card_selected.ShopCardSelectedFragment;
import com.xekera.Ecommerce.ui.shop_card_selected.add_to_cart_shop_details.AddToCartShopCardSelectedFragment;
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
        DeliveyBillingDetailsModule.class,
        BillingTotalAmountViewModule.class,
        HistoryModule.class,
        FavouritesModule.class,
        ContinueShoppingModule.class,
        ContinueShoppingNewModule.class,
        HistoryDesciptionModule.class,
        AddToCartShopCardSelectedModule.class,
        SearchAllProductsModule.class
})
@Singleton
public interface AppComponent {


    void inject(BaseActivity baseActivity);

    void inject(StripePaymentActivity stripePaymentActivity);

    void inject(LoginBaseActivity loginBaseActivity);

    void inject(ShopFragment shopFragment);

    void inject(ShopDetailsFragment shopDetailsFragment);

    void inject(CartFragment cartFragment);

//    void inject(HistoryDesciptionFragment historyFragment);

    void inject(FragmentFavourites fragmentFavourites);

    void inject(DashboardFragment dashboardFragment);


    void inject(LoginFragment loginFragment);

    void inject(SignupFragment signupFragment);

    void inject(ShopCardSelectedFragment shopCardSelectedFragment);

    void inject(AddToCartFragment addToCartFragment);

    void inject(DeliveyBillingDetailsFragment deliveyBillingDetailsFragment);

    void inject(BillingTotalAmountViewFragment billingTotalAmountView);

    void inject(HistoryFragment historyFragment);

    void inject(FavouritesFragment favouritesFragment);

    void inject(AccountFragment accountFragment);

    void inject(AboutFragment aboutFragment);

    void inject(SettingFragment settingFragment);

    void inject(ContinueShoppingFragment continueShoppingFragment);

    void inject(ContinueShopFragment continueShopFragment);

    void inject(HistoryDesciptionFragment historyDesciptionFragment);


    void inject(AddToCartShopCardSelectedFragment addToCartShopCardSelectedFragment);

    void inject(SearchAllProductsFragment searchAllProductsFragment);


}












