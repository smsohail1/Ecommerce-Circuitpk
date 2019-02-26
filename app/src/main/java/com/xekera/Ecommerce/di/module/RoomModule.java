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
                //.addMigrations(MIGRATION_1_2)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            database.execSQL("ALTER TABLE favourites "
                    + " ADD COLUMN test TEXT");
        }
    };
}







