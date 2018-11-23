package com.xekera.Ecommerce.data.room.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "add_to_cart")
public class AddToCart {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "add_to_cart_id")
    private int id;

    @ColumnInfo(name = "order_ID")
    private String orderID;
    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "item_individual_price")
    private String itemIndividualPrice;
    @ColumnInfo(name = "item_price")
    private String itemPrice;
    @ColumnInfo(name = "item_cut_price")
    private String itemCutPrice;
    @ColumnInfo(name = "item_quantity")
    private String itemQuantity;
    @ColumnInfo(name = "address1")
    private String address1;
    @ColumnInfo(name = "address2")
    private String address2;
    @ColumnInfo(name = "latitude")
    private String latitude;
    @ColumnInfo(name = "longitude")
    private String longitude;
    @ColumnInfo(name = "created_date")
    private String createdDate;
    @ColumnInfo(name = "item_image")
    private byte[] itemImage;
    @ColumnInfo(name = "transmission_status")
    private String transmissionStatus;

    public AddToCart() {

    }

    public AddToCart(String itemName, String itemPrice, String itemQuantity, String latitude, String longitude, String createdDate, byte[] itemImage, String transmissionStatus) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.transmissionStatus = transmissionStatus;
    }

    public AddToCart(String itemName, String itemPrice, String itemQuantity, String latitude, String longitude, byte[] itemImage, String transmissionStatus) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.itemImage = itemImage;
        this.transmissionStatus = transmissionStatus;
    }

    public AddToCart(String orderID, String itemName, String itemPrice, String itemQuantity, String address1, String address2,
                     String latitude, String longitude, String transmissionStatus, byte[] itemImage, String itemCutPrice
            , String itemIndividualPrice) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.address1 = address1;
        this.address2 = address2;
        this.latitude = latitude;
        this.longitude = longitude;
        this.transmissionStatus = transmissionStatus;
        this.itemImage = itemImage;
        this.itemCutPrice = itemCutPrice;
        this.itemIndividualPrice = itemIndividualPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getTransmissionStatus() {
        return transmissionStatus;
    }

    public void setTransmissionStatus(String transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getItemCutPrice() {
        return itemCutPrice;
    }

    public void setItemCutPrice(String itemCutPrice) {
        this.itemCutPrice = itemCutPrice;
    }

    public String getItemIndividualPrice() {
        return itemIndividualPrice;
    }

    public void setItemIndividualPrice(String itemIndividualPrice) {
        this.itemIndividualPrice = itemIndividualPrice;
    }
}
