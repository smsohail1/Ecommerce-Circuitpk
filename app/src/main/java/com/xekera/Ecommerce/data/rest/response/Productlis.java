package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productlis {
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("About_product")
    @Expose
    private Object aboutProduct;
    @SerializedName("type")
    @Expose
    private Object type;
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

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAboutProduct() {
        return aboutProduct;
    }

    public void setAboutProduct(Object aboutProduct) {
        this.aboutProduct = aboutProduct;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
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
