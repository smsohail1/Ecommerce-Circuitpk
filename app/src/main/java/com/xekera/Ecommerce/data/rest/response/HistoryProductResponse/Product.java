package com.xekera.Ecommerce.data.rest.response.HistoryProductResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("About_product")
    @Expose
    private String aboutProduct;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image_json")
    @Expose
    private String imageJson;
    @SerializedName("Product_Sku")
    @Expose
    private String productSku;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Regular_price")
    @Expose
    private String regularPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutProduct() {
        return aboutProduct;
    }

    public void setAboutProduct(String aboutProduct) {
        this.aboutProduct = aboutProduct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageJson() {
        return imageJson;
    }

    public void setImageJson(String imageJson) {
        this.imageJson = imageJson;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

}
