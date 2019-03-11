package com.xekera.Ecommerce.data.rest.response.delete_item_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteItemCartResponse {

    @SerializedName("meesage")
    @Expose
    private String meesage;

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }
}
