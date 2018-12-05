package com.xekera.Ecommerce.di.module;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import com.xekera.Ecommerce.data.room.AppDatabase;
import com.xekera.Ecommerce.util.AppConstants;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RoomModule {

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppConstants.DATABASE_NAME)
                .allowMainThreadQueries() //temporary, should be done on seperate thread
                //.fallbackToDestructiveMigration()
                .build();
    }

}







