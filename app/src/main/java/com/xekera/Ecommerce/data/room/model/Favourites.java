package com.xekera.Ecommerce.data.room.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "favourites")
public class Favourites {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "favourites_id")
    private int id;
    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "item_individual_price")
    private String itemIndividualPrice;

    @ColumnInfo(name = "item_total_price")
    private String itemTotalPrice;
    @ColumnInfo(name = "item_quantity")
    private String itemQuantity;
    @ColumnInfo(name = "item_cut_price")
    private String itemCutPrice;
    @ColumnInfo(name = "item_stock_status")
    private String itemStockStatus;
    @ColumnInfo(name = "created_date")
    private String createdDate;
    @ColumnInfo(name = "item_image")
    private byte[] itemImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIndividualPrice() {
        return itemIndividualPrice;
    }

    public void setItemIndividualPrice(String itemIndividualPrice) {
        this.itemIndividualPrice = itemIndividualPrice;
    }

    public String getItemStockStatus() {
        return itemStockStatus;
    }

    public void setItemStockStatus(String itemStockStatus) {
        this.itemStockStatus = itemStockStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemCutPrice() {
        return itemCutPrice;
    }

    public void setItemCutPrice(String itemCutPrice) {
        this.itemCutPrice = itemCutPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(String itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }


    public Favourites() {

    }

//    public Favourites(String itemName, String itemIndividualPrice, String itemCutPrice,
//                      String itemStockStatus, String createdDate, byte[] itemImage, String itemQuantity) {
//        this.itemName = itemName;
//        this.itemIndividualPrice = itemIndividualPrice;
//        this.itemCutPrice = itemCutPrice;
//        this.itemStockStatus = itemStockStatus;
//        this.createdDate = createdDate;
//        this.itemImage = itemImage;
//        this.itemQuantity = itemQuantity;
//    }

    public Favourites(String itemName, String itemIndividualPrice, String itemCutPrice,
                      String itemStockStatus, String createdDate, byte[] itemImage, String itemQuantity, String itemTotalPrice) {
        this.itemName = itemName;
        this.itemIndividualPrice = itemIndividualPrice;
        this.itemCutPrice = itemCutPrice;
        this.itemStockStatus = itemStockStatus;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
    }


}
