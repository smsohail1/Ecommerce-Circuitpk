package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("pro_list")
    @Expose
    private List<ProList> proList = null;

    public List<ProList> getProList() {
        return proList;
    }

    public void setProList(List<ProList> proList) {
        this.proList = proList;
    }

}
