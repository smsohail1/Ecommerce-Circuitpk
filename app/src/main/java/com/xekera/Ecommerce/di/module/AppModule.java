package com.xekera.Ecommerce.di.module;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    public Context provideContext(){
        return application;
    }

}
