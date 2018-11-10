package com.xekera.Ecommerce.di.module;


import android.content.Context;
import com.xekera.Ecommerce.util.*;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class UtilModule {
    @Singleton
    @Provides
    public ToastUtil provideToastUtil(Context context){
        return new ToastUtil(context);
    }

    @Singleton
    @Provides
    public SnackUtil provideSnackUtil(Context context){
        return new SnackUtil(context);
    }

    @Singleton
    @Provides
    public Utils provideUtils(Context context){
        return new Utils(context);
    }

    @Singleton
    @Provides
    public LogUtil provideLogUtil(){
        return new LogUtil();
    }

    @Singleton
    @Provides
    public SessionManager provideSessionManager(Context context){
        return new SessionManager(context);
    }
}
