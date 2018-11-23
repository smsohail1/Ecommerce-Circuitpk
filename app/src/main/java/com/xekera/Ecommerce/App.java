package com.xekera.Ecommerce;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.xekera.Ecommerce.di.component.AppComponent;
import com.xekera.Ecommerce.di.component.DaggerAppComponent;
import com.xekera.Ecommerce.di.module.*;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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















