package com.xekera.Ecommerce.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.xekera.Ecommerce.data.room.model.Booking;

import java.util.List;


@Dao
public interface BookingDao {

//    @Insert
//    void insert(Booking booking);

    @Insert
    void insert(List<Booking> bookingList);


    @Query("SELECT * FROM booking order by created_date desc")
    List<Booking> getAllBookingDetails();

}
