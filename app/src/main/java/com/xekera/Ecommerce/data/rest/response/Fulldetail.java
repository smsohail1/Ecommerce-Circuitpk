package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderDescriptionAddressResponse.Address;

public class Fulldetail {
    @SerializedName("Products")
    @Expose
    private Products products;
    @SerializedName("Address")
    @Expose
    private Address address;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
