package com.entity;

public class billProduct {
    private int fBillProductID;
    private int fproductID;
    private int fBillID;
    private String fName;
    private String fImgUrl;
    private String fDesc;
    private String fDescDetail;
    private double fOldPrice;
    private double fPrice;
    private double fvipprice;
    private String fUnit;
    private float fcount;

    public billProduct() {
    }

    public billProduct(product product, float count) {
        this.fBillProductID = 0;
        this.fproductID = product.getfProductId();
        this.fBillID = 0;
        this.fName = product.getfName();
        this.fImgUrl = product.getfImgUrl();
        this.fDesc = product.getfDesc();
        this.fDescDetail = product.getfDescDetail();
        this.fvipprice = product.getFvipprice();
        this.fOldPrice = product.getfOldPrice();
        this.fPrice = product.getfPrice();
        this.fUnit = product.getfUnit();
        this.fcount = count;
    }

    public int getfBillProductID() {
        return fBillProductID;
    }

    public void setfBillProductID(int fBillProductID) {
        this.fBillProductID = fBillProductID;
    }

    public int getFproductID() {
        return fproductID;
    }

    public void setFproductID(int fproductID) {
        this.fproductID = fproductID;
    }

    public int getfBillID() {
        return fBillID;
    }

    public void setfBillID(int fBillID) {
        this.fBillID = fBillID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfImgUrl() {
        return fImgUrl;
    }

    public void setfImgUrl(String fImgUrl) {
        this.fImgUrl = fImgUrl;
    }

    public String getfDesc() {
        return fDesc;
    }

    public void setfDesc(String fDesc) {
        this.fDesc = fDesc;
    }

    public String getfDescDetail() {
        return fDescDetail;
    }

    public void setfDescDetail(String fDescDetail) {
        this.fDescDetail = fDescDetail;
    }

    public double getfOldPrice() {
        return fOldPrice;
    }

    public void setfOldPrice(double fOldPrice) {
        this.fOldPrice = fOldPrice;
    }

    public double getFvipprice() {
        return fvipprice;
    }

    public void setFvipprice(double fvipprice) {
        this.fvipprice = fvipprice;
    }

    public double getfPrice() {
        return fPrice;
    }

    public void setfPrice(double fPrice) {
        this.fPrice = fPrice;
    }

    public String getfUnit() {
        return fUnit;
    }

    public void setfUnit(String fUnit) {
        this.fUnit = fUnit;
    }

    public float getFcount() {
        return fcount;
    }

    public void setFcount(float fcount) {
        this.fcount = fcount;
    }

    public billProduct(int fBillProductID, int fproductID, int fBillID, String fName, String fImgUrl, String fDesc, String fDescDetail, double fOldPrice, double fPrice, double fvipprice, String fUnit, float fcount) {
        this.fBillProductID = fBillProductID;
        this.fproductID = fproductID;
        this.fBillID = fBillID;
        this.fName = fName;
        this.fImgUrl = fImgUrl;
        this.fDesc = fDesc;
        this.fDescDetail = fDescDetail;
        this.fOldPrice = fOldPrice;
        this.fPrice = fPrice;
        this.fvipprice = fvipprice;
        this.fUnit = fUnit;
        this.fcount = fcount;
    }
}
