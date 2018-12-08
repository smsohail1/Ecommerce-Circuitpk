package com.xekera.Ecommerce.ui.dasboard_shopping_details.model;

import com.xekera.Ecommerce.data.room.model.Favourites;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ShoppingDetailModel implements Serializable {

    private String productName;
    private String productPrice;
    private boolean isFavourite;
    private long itemQuantity;
    private List<String> image;
    private long totalPrice;
    private long cutPrice;
    private byte[] byteImage;
    private byte[] byteArray;
    private long itemTotalFetchQuantity;
    private List<Favourites> favourites;

    public ShoppingDetailModel() {

    }

    public ShoppingDetailModel(String productName, String productPrice, boolean isFavourite, long itemQuantity,
                               List<String> image, byte[] byteArray, long itemTotalFetchQuantity,
                               List<Favourites> favourites) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.isFavourite = isFavourite;
        this.itemQuantity = itemQuantity;
        this.image = image;
        this.byteArray = byteArray;
        this.itemTotalFetchQuantity = itemTotalFetchQuantity;
        this.favourites = favourites;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }


    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public List<Favourites> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourites> favourites) {
        this.favourites = favourites;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public long getItemTotalPrice() {
        return totalPrice;
    }

    public void setItemTotalPrice(long itemIndividualPrice) {
        this.totalPrice = itemIndividualPrice;
    }

    public long getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(long cutPrice) {
        this.cutPrice = cutPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public long getItemTotalFetchQuantity() {
        return itemTotalFetchQuantity;
    }

    public void setItemTotalFetchQuantity(long itemTotalFetchQuantity) {
        this.itemTotalFetchQuantity = itemTotalFetchQuantity;
    }
}
