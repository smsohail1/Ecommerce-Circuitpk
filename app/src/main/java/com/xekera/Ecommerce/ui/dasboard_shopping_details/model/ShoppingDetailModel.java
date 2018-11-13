package com.xekera.Ecommerce.ui.dasboard_shopping_details.model;

public class ShoppingDetailModel {

    private String productName;
    private String productPrice;


    public ShoppingDetailModel() {

    }

    public ShoppingDetailModel(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
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

}
