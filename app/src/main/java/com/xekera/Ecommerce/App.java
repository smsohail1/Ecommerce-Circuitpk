package com.xekera.Ecommerce;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.xekera.Ecommerce.di.component.AppComponent;
import com.xekera.Ecommerce.di.component.DaggerAppComponent;
import com.xekera.Ecommerce.di.module.*;

/**
 * Created by shahrukh.malik on 09, July, 2018
 */
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
                .loginModule(new LoginModule())
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















