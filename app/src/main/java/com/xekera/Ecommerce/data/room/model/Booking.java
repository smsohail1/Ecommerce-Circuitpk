package com.xekera.Ecommerce.data.room.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity(tableName = "booking")
public class Booking {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "booking_id")
    private int id;
    @ColumnInfo(name = "order_ID")
    private String orderID;
    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "item_individual_price")
    private String itemIndividualPrice;
    @ColumnInfo(name = "item_price")
    private String itemPrice;
    @ColumnInfo(name = "item_cut_price")
    private String itemCutPrice;
    @ColumnInfo(name = "item_quantity")
    private String itemQuantity;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "company_name")
    private String companyName;
    @ColumnInfo(name = "phone_no")
    private String phoneNo;
    @ColumnInfo(name = "email_address")
    private String emailAddress;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "street_address1")
    private String streetAddress1;
    @ColumnInfo(name = "street_address2")
    private String streetAddress2;
    @ColumnInfo(name = "town_city")
    private String townCity;
    @ColumnInfo(name = "state_country")
    private String stateCountry;
    @ColumnInfo(name = "postal_code")
    private String postalCode;
    @ColumnInfo(name = "payment_type")
    private String paymentType;
    @ColumnInfo(name = "order_notes")
    private String orderNotes;
    @ColumnInfo(name = "flat_charges")
    private String flatCharges;
    @ColumnInfo(name = "created_date")
    private String createdDate;
    @ColumnInfo(name = "item_image")
    private byte[] itemImage;
    @ColumnInfo(name = "transmission_status")
    private String transmissionStatus;


    public Booking(String orderID, String itemName, String itemIndividualPrice, String itemPrice,
                   String itemCutPrice, String itemQuantity, String firstName, String lastName,
                   String companyName, String phoneNo, String emailAddress, String country,
                   String streetAddress1, String streetAddress2, String townCity, String stateCountry, String postalCode,
                   String paymentType, String orderNotes, String flatCharges, String createdDate, byte[] itemImage, String transmissionStatus) {
        this.orderID = orderID;
        this.itemName = itemName;
        this.itemIndividualPrice = itemIndividualPrice;
        this.itemPrice = itemPrice;
        this.itemCutPrice = itemCutPrice;
        this.itemQuantity = itemQuantity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.country = country;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.townCity = townCity;
        this.stateCountry = stateCountry;
        this.postalCode = postalCode;
        this.paymentType = paymentType;
        this.orderNotes = orderNotes;
        this.flatCharges = flatCharges;
        this.createdDate = createdDate;
        this.itemImage = itemImage;
        this.transmissionStatus = transmissionStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIndividualPrice() {
        return itemIndividualPrice;
    }

    public void setItemIndividualPrice(String itemIndividualPrice) {
        this.itemIndividualPrice = itemIndividualPrice;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCutPrice() {
        return itemCutPrice;
    }

    public void setItemCutPrice(String itemCutPrice) {
        this.itemCutPrice = itemCutPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getStateCountry() {
        return stateCountry;
    }

    public void setStateCountry(String stateCountry) {
        this.stateCountry = stateCountry;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getTransmissionStatus() {
        return transmissionStatus;
    }

    public String getFlatCharges() {
        return flatCharges;
    }

    public void setFlatCharges(String flatCharges) {
        this.flatCharges = flatCharges;
    }

    public void setTransmissionStatus(String transmissionStatus) {
        this.transmissionStatus = transmissionStatus;
    }

}
