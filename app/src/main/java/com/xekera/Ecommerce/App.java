package com.xekera.Ecommerce;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.xekera.Ecommerce.di.component.AppComponent;
import com.xekera.Ecommerce.di.component.DaggerAppComponent;
import com.xekera.Ecommerce.di.module.*;
import com.xekera.Ecommerce.ui.continue_shopping.ContinueShopFragment;
import io.fabric.sdk.android.Fabric;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilModule(new UtilModule())
                .retrofitModule(new RetrofitModule())
                .roomModule(new RoomModule())
                .dashboardModule(new DashboardModule())
                .loginModule(new LoginModule())
                .signupModule(new SignupModule())
                .shopModule(new ShopModule())
                .shopDetailsModule(new ShopDetailsModule())
                .shopCardSelectedModule(new ShopCardSelectedModule())
                .addToCartModule(new AddToCartModule())
                .deliveyBillingDetailsModule(new DeliveyBillingDetailsModule())
                .billingTotalAmountViewModule(new BillingTotalAmountViewModule())
                .historyModule(new HistoryModule())
                .favouritesModule(new FavouritesModule())
                .continueShoppingNewModule(new ContinueShoppingNewModule())
                .historyDesciptionModule(new HistoryDesciptionModule())
                .addToCartShopCardSelectedModule(new AddToCartShopCardSelectedModule())
                //.(new ContinueShopFragment())
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}















