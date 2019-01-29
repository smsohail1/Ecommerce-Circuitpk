package com.xekera.Ecommerce.data.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryOrderIdResponse {
    @SerializedName("order_list")
    @Expose
    private List<OrderList> orderList = null;

    public List<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderList> orderList) {
        this.orderList = orderList;
    }


}
