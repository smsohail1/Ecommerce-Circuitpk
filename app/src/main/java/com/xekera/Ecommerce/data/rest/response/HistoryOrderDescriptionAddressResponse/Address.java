package com.xekera.Ecommerce.data.rest.response.HistoryOrderDescriptionAddressResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("Order_id")
    @Expose
    private Object orderId;
    @SerializedName("Cust_name")
    @Expose
    private Object custName;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("EmailAddress")
    @Expose
    private Object emailAddress;
    @SerializedName("Address")
    @Expose
    private Object address;
    @SerializedName("PhoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("CompanyName")
    @Expose
    private Object companyName;
    @SerializedName("PaymentMode")
    @Expose
    private Object paymentMode;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("AddedOn")
    @Expose
    private Object addedOn;
    @SerializedName("Order_Status")
    @Expose
    private Object orderStatus;

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getCustName() {
        return custName;
    }

    public void setCustName(Object custName) {
        this.custName = custName;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(Object emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Object paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Object addedOn) {
        this.addedOn = addedOn;
    }

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }
}
