package com.xekera.Ecommerce.data.room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.xekera.Ecommerce.data.room.dao.UserTestDao;
import com.xekera.Ecommerce.data.room.model.UserTest;

/**
 * Created by shahrukh.malik on 02, April, 2018
 */


@Database(entities = {

        UserTest.class,
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserTestDao getUserTestDao();

}










