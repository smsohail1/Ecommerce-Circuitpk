package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryDetailsResponse {
    @SerializedName("fulldetail")
    @Expose
    private List<Fulldetail> fulldetail = null;

    public List<Fulldetail> getFulldetail() {
        return fulldetail;
    }

    public void setFulldetail(List<Fulldetail> fulldetail) {
        this.fulldetail = fulldetail;
    }


}
