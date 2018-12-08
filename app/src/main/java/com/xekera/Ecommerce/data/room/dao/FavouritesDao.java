package com.xekera.Ecommerce.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.xekera.Ecommerce.data.room.model.Favourites;

import java.util.List;

@Dao
public interface FavouritesDao {

    @Insert
    void insert(Favourites favourites);

    @Insert
    void insert(List<Favourites> favouritesList);


    @Query("SELECT * FROM favourites order by created_date desc")
    List<Favourites> getAllFavouritesDetails();

    @Query("SELECT * FROM favourites")
    List<Favourites> getAllFavourites();


    @Query("SELECT * FROM favourites")
    List<Favourites> getFavouritesCounts();

    @Query("SELECT * FROM favourites where item_name = :itemName")
    List<Favourites> getFavouritesByName(String itemName);


    @Query("DELETE FROM favourites where item_name = :itemName")
    void deleteItem(String itemName);


}
