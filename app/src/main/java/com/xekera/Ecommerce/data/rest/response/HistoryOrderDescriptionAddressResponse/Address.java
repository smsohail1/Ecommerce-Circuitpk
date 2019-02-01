package com.xekera.Ecommerce.data.rest.response.HistoryOrderDescriptionAddressResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("Order_id")
    @Expose
    private String orderId;
    @SerializedName("Cust_name")
    @Expose
    private String custName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("PaymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("GST")
    @Expose
    private String gST;
    @SerializedName("FlatCharges")
    @Expose
    private String flatCharges;
    @SerializedName("AddedOn")
    @Expose
    private String addedOn;
    @SerializedName("Order_Status")
    @Expose
    private String orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getGST() {
        return gST;
    }

    public void setGST(String gST) {
        this.gST = gST;
    }

    public String getFlatCharges() {
        return flatCharges;
    }

    public void setFlatCharges(String flatCharges) {
        this.flatCharges = flatCharges;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


}
