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
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "product_id")
    private String product_id;
    @ColumnInfo(name = "is_email_fav")
    private String isEmailFav;
    @ColumnInfo(name = "product_desc_fav")
    private String productDescFav;
    @ColumnInfo(name = "img_list_fav")
    private String imgArrListFav;
    @ColumnInfo(name = "name_sku")
    private String nameSku;

    public String getProductDescFav() {
        return productDescFav;
    }

    public void setProductDescFav(String productDescFav) {
        this.productDescFav = productDescFav;
    }

    public String getImgArrListFav() {
        return imgArrListFav;
    }

    public void setImgArrListFav(String imgArrListFav) {
        this.imgArrListFav = imgArrListFav;
    }

    public String getIsEmailFav() {
        return isEmailFav;
    }

    public void setIsEmailFav(String isEmailFav) {
        this.isEmailFav = isEmailFav;
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

    public String getNameSku() {
        return nameSku;
    }

    public void setNameSku(String nameSku) {
        this.nameSku = nameSku;
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
                      String itemStockStatus, String createdDate, byte[] itemImage, String itemQuantity,
                      String itemTotalPrice, String image, String product_id, String isEmailFav, String productDescFav,
                      String imgArrListFav, String nameSku) {
        this.itemName = itemName;
        this.itemIndividualPrice = itemIndividualPrice;
        this.itemCutPrice = itemCutPrice;
        this.itemStockStatus = itemStockStatus;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
        this.image = image;
        this.product_id = product_id;
        this.isEmailFav = isEmailFav;
        this.productDescFav = productDescFav;
        this.imgArrListFav = imgArrListFav;
        this.nameSku = nameSku;
    }

    public Favourites(String itemName, String itemIndividualPrice, String itemCutPrice,
                      String itemStockStatus, String createdDate, byte[] itemImage, String itemQuantity,
                      String itemTotalPrice, String image, String product_id, String isEmailFav, String productDescFav,
                      String imgArrListFav) {
        this.itemName = itemName;
        this.itemIndividualPrice = itemIndividualPrice;
        this.itemCutPrice = itemCutPrice;
        this.itemStockStatus = itemStockStatus;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
        this.image = image;
        this.product_id = product_id;
        this.isEmailFav = isEmailFav;
        this.productDescFav = productDescFav;
        this.imgArrListFav = imgArrListFav;
    }


    public Favourites(String itemName, String itemIndividualPrice, String itemCutPrice,
                      String itemStockStatus, String createdDate, byte[] itemImage, String itemQuantity,
                      String itemTotalPrice, String image, String product_id, String isEmailFav) {
        this.itemName = itemName;
        this.itemIndividualPrice = itemIndividualPrice;
        this.itemCutPrice = itemCutPrice;
        this.itemStockStatus = itemStockStatus;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
        this.image = image;
        this.product_id = product_id;
        this.isEmailFav = isEmailFav;
    }


}
