package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xekera.Ecommerce.data.rest.response.Product;

import java.util.List;

public class ProductResponse {
    @SerializedName("Product")
    @Expose
    private List<Product> product = null;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
