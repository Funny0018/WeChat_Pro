package com.entity;

import java.io.Serializable;

public class billDeliveryAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fbillDeliveryAddressid;
    private int fDeliveryAddressid;
    private int fbillid;
    private String fName;
    private String fMobile;
    private String fCity;
    private String fAddress;
    private String fBuildNo;

    public billDeliveryAddress() {
    }

    public billDeliveryAddress(deliveryAddress deliveryAddress) {
        this.fbillDeliveryAddressid = 0;
        this.fDeliveryAddressid = deliveryAddress.getFid();
        this.fbillid = 0;
        this.fName = deliveryAddress.getFname();
        this.fMobile = deliveryAddress.getFphone();
        this.fCity = deliveryAddress.getFcity();
        this.fAddress = deliveryAddress.getFaddress();
        this.fBuildNo = deliveryAddress.getFbuildno();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFbillDeliveryAddressid() {
        return fbillDeliveryAddressid;
    }

    public void setFbillDeliveryAddressid(int fbillDeliveryAddressid) {
        this.fbillDeliveryAddressid = fbillDeliveryAddressid;
    }

    public int getfDeliveryAddressid() {
        return fDeliveryAddressid;
    }

    public void setfDeliveryAddressid(int fDeliveryAddressid) {
        this.fDeliveryAddressid = fDeliveryAddressid;
    }

    public int getFbillid() {
        return fbillid;
    }

    public void setFbillid(int fbillid) {
        this.fbillid = fbillid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfMobile() {
        return fMobile;
    }

    public void setfMobile(String fMobile) {
        this.fMobile = fMobile;
    }

    public String getfCity() {
        return fCity;
    }

    public void setfCity(String fCity) {
        this.fCity = fCity;
    }

    public String getfAddress() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress = fAddress;
    }

    public String getfBuildNo() {
        return fBuildNo;
    }

    public void setfBuildNo(String fBuildNo) {
        this.fBuildNo = fBuildNo;
    }

    public billDeliveryAddress(int fbillDeliveryAddressid, int fDeliveryAddressid, int fbillid, String fName, String fMobile, String fCity, String fAddress, String fBuildNo) {
        this.fbillDeliveryAddressid = fbillDeliveryAddressid;
        this.fDeliveryAddressid = fDeliveryAddressid;
        this.fbillid = fbillid;
        this.fName = fName;
        this.fMobile = fMobile;
        this.fCity = fCity;
        this.fAddress = fAddress;
        this.fBuildNo = fBuildNo;
    }
}
