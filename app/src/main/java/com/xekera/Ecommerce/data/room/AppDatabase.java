package com.xekera.Ecommerce.data.room;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.xekera.Ecommerce.data.room.dao.AddToCartDao;
import com.xekera.Ecommerce.data.room.dao.BookingDao;
import com.xekera.Ecommerce.data.room.dao.FavouritesDao;
import com.xekera.Ecommerce.data.room.dao.UserTestDao;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Booking;
import com.xekera.Ecommerce.data.room.model.Favourites;
import com.xekera.Ecommerce.data.room.model.UserTest;

@Database(entities = {
        UserTest.class,
        AddToCart.class,
        Booking.class,
        Favourites.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserTestDao getUserTestDao();

    public abstract AddToCartDao getAddToCartDao();

    public abstract BookingDao getBookingDao();

    public abstract FavouritesDao getFavouritesDao();

}










