package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xekera.Ecommerce.data.rest.response.HistoryOrderDescriptionAddressResponse.Address;

public class Fulldetail {
    @SerializedName("Productlis")
    @Expose
    private Productlis productlis;
    @SerializedName("Address")
    @Expose
    private Address address;

    public Productlis getProductlis() {
        return productlis;
    }

    public void setProductlis(Productlis productlis) {
        this.productlis = productlis;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
