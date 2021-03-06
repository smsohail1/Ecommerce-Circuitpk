package com.xekera.Ecommerce.data.room.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import io.reactivex.annotations.NonNull;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.util.List;

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
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "product_id")
    private String product_id;
    @ColumnInfo(name = "is_email_cart")
    private String isEmailCart;
    @ColumnInfo(name = "product_desc")
    private String productDesc;
    @ColumnInfo(name = "img_arr_list")
    private String imgArrList;
    @ColumnInfo(name = "name_sku")
    private String nameSku;


    public String getIsEmailCart() {
        return isEmailCart;
    }

    public void setIsEmailCart(String isEmailCart) {
        this.isEmailCart = isEmailCart;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgArrList() {
        return imgArrList;
    }

    public void setImgArrList(String imgArrList) {
        this.imgArrList = imgArrList;
    }


    public String getNameSku() {
        return nameSku;
    }

    public void setNameSku(String nameSku) {
        this.nameSku = nameSku;
    }

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

    public AddToCart(String itemName, String itemPrice, String itemQuantity, String latitude, String longitude,
                     byte[] itemImage, String transmissionStatus) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.itemImage = itemImage;
        this.transmissionStatus = transmissionStatus;
    }

    public AddToCart(String orderID, String itemName, String itemPrice, String itemQuantity, String transmissionStatus,
                     byte[] itemImage, String itemCutPrice
            , String itemIndividualPrice, String createdDate, String image, String product_id, String isEmailCart) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.transmissionStatus = transmissionStatus;
        this.itemImage = itemImage;
        this.itemCutPrice = itemCutPrice;
        this.itemIndividualPrice = itemIndividualPrice;
        this.createdDate = createdDate;
        this.image = image;
        this.product_id = product_id;
        this.isEmailCart = isEmailCart;
    }

    public AddToCart(String orderID, String itemName, String itemPrice, String itemQuantity, String transmissionStatus,
                     byte[] itemImage, String itemCutPrice
            , String itemIndividualPrice, String createdDate, String image, String product_id, String isEmailCart,
                     String productDesc, String imgArrList) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.transmissionStatus = transmissionStatus;
        this.itemImage = itemImage;
        this.itemCutPrice = itemCutPrice;
        this.itemIndividualPrice = itemIndividualPrice;
        this.createdDate = createdDate;
        this.image = image;
        this.product_id = product_id;
        this.isEmailCart = isEmailCart;
        this.productDesc = productDesc;
        this.imgArrList = imgArrList;

    }

    public AddToCart(String orderID, String itemName, String itemPrice, String itemQuantity, String transmissionStatus,
                     byte[] itemImage, String itemCutPrice
            , String itemIndividualPrice, String createdDate, String image, String product_id, String isEmailCart,
                     String productDesc, String imgArrList, String nameSku) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.transmissionStatus = transmissionStatus;
        this.itemImage = itemImage;
        this.itemCutPrice = itemCutPrice;
        this.itemIndividualPrice = itemIndividualPrice;
        this.createdDate = createdDate;
        this.image = image;
        this.product_id = product_id;
        this.isEmailCart = isEmailCart;
        this.productDesc = productDesc;
        this.imgArrList = imgArrList;
        this.nameSku = nameSku;

    }


    public AddToCart(String orderID, String itemName, String itemPrice, String itemQuantity, String transmissionStatus,
                     byte[] itemImage, String itemCutPrice
            , String itemIndividualPrice) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
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
