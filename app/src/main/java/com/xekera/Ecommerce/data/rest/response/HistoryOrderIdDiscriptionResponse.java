package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xekera.Ecommerce.data.rest.response.HistoryProductResponse.Product;

import java.util.List;

public class HistoryOrderIdDiscriptionResponse {
    @SerializedName("Product")
    @Expose
    private List<Product> product = null;
    @SerializedName("Address")
    @Expose
    private Address address;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
