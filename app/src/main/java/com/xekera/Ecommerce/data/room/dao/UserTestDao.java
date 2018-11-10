package com.xekera.Ecommerce.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import com.xekera.Ecommerce.data.room.model.UserTest;

@Dao
public interface UserTestDao {

    @Insert
    void insert(UserTest userTest);

    @Update
    void update(UserTest userTest);

    @Delete
    void delete(UserTest booking);

}











