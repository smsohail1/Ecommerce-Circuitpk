package com.xekera.Ecommerce.data.rest.response.add_remove_cart_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRemoveCartResponse {
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
