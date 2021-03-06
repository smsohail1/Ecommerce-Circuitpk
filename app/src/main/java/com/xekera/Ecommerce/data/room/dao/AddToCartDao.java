package com.xekera.Ecommerce.data.room.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.xekera.Ecommerce.data.room.model.AddToCart;
import com.xekera.Ecommerce.data.room.model.Favourites;

import java.util.List;

@Dao
public interface AddToCartDao {
    @Insert
    void insert(AddToCart addToCart);


    @Insert
    void insert(List<AddToCart> deliveryDetails);


    @Update
    void update(AddToCart addToCart);

    @Query("SELECT * FROM add_to_cart order by item_name desc")
    List<AddToCart> getAllCartDetails();


    @Query("SELECT * FROM add_to_cart order by created_date desc")
    List<AddToCart> getAllCartOrderDetails();


    @Query("SELECT * FROM add_to_cart order by created_date desc")
    List<AddToCart> getAllCartDetailsWithRespectToDateDesc();

    @Query("SELECT * FROM add_to_cart")
    List<AddToCart> getAllCartCount();


    @Query("SELECT * FROM add_to_cart")
    List<AddToCart> getAllCartCountItems();

    @Query("DELETE FROM add_to_cart where item_name =:itemName")
    void deleteItem(String itemName);


    @Query("SELECT * FROM add_to_cart where item_name =:itemName")
    List<AddToCart> getCartDetailsByProductName(String itemName);


    @Query("SELECT * FROM add_to_cart where item_name = :itemName")
    List<AddToCart> getCartsByName(String itemName);


    @Query("UPDATE add_to_cart SET item_quantity = :quantity , item_price = :itemPrice , item_cut_price = :cutPrice where item_name = :itemName")
    void updateItemCount(String quantity, String itemPrice, String itemName, String cutPrice);

    @Query("UPDATE add_to_cart SET item_quantity = :quantity , item_price = :itemPrice , item_cut_price = :cutPrice , created_date = :createdDate where item_name = :itemName")
    void updateItemCountWithDate(String quantity, String itemPrice, String itemName, String cutPrice, String createdDate);

    @Query("UPDATE add_to_cart SET order_ID = :orderId")
    void updateWithOrderId(String orderId);


//    @Query("SELECT sum(item_price) FROM add_to_cart")
//    List<AddToCart> getSubTotalCartDetails();

}
