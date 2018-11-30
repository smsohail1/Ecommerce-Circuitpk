package com.xekera.Ecommerce.ui.dasboard_shopping_details.model;

import java.io.Serializable;
import java.util.List;

public class ShoppingDetailModel implements Serializable {

    private String productName;
    private String productPrice;
    private boolean isFavourite;
    private long itemQuantity;
    private List<String> image;


    public ShoppingDetailModel() {

    }

    public ShoppingDetailModel(String productName, String productPrice, boolean isFavourite, long itemQuantity, List<String> image) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.isFavourite = isFavourite;
        this.itemQuantity = itemQuantity;
        this.image = image;
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

}
