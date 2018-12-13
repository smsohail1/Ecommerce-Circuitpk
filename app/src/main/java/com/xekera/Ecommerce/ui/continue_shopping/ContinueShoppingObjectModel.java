package com.xekera.Ecommerce.ui.continue_shopping;

import com.xekera.Ecommerce.data.room.model.Favourites;

import java.io.Serializable;
import java.util.List;

public class ContinueShoppingObjectModel {

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
    private int Drawable;
    private String categoryLabel;

    public ContinueShoppingObjectModel() {

    }

    public ContinueShoppingObjectModel(String productName, String productPrice, boolean isFavourite, long itemQuantity,
                                       List<String> image, byte[] byteArray, long itemTotalFetchQuantity, int drawable, String categoryLabel) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.isFavourite = isFavourite;
        this.itemQuantity = itemQuantity;
        this.image = image;
        this.byteArray = byteArray;
        this.itemTotalFetchQuantity = itemTotalFetchQuantity;
        this.Drawable = Drawable;
        this.categoryLabel = categoryLabel;
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

    public int getDrawable() {
        return Drawable;
    }

    public void setDrawable(int drawable) {
        Drawable = drawable;
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


    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public long getItemTotalFetchQuantity() {
        return itemTotalFetchQuantity;
    }

    public void setItemTotalFetchQuantity(long itemTotalFetchQuantity) {
        this.itemTotalFetchQuantity = itemTotalFetchQuantity;
    }
}
