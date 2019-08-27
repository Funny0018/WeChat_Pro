package com.entity;

import java.io.Serializable;
import java.util.List;

public class bill implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fbillid;
    private String fbillNo;
    //0：未付款，1：已付款，待发货；2，已发货，待收货，3：已收货，订单完成 -1：订单取消，-2：订单删除
    private int fstate;
    private List<billProduct> billProducts;
    private billDeliveryAddress billDeliveryAddress;
    private float ftotalAmount;
    private int fdikouid;
    private float fdikouAmount;
    private float fhongbaoAmount;
    private float fproductAmount;
    private float fyunfei;
    private float fvipamount;
    private float ffinalAmount;
    private float ftotalpricevip;
    private int fuserID;
    private String fbilldate;
    private String fpaydate;
    private String fsenddate;
    private String farrivedate;
    private String fsendno;

    public bill() {
    }

    public float getFtotalpricevip() {
        return ftotalpricevip;
    }

    public void setFtotalpricevip(float ftotalpricevip) {
        this.ftotalpricevip = ftotalpricevip;
    }

    public String getFsendno() {
        return fsendno;
    }

    public void setFsendno(String fsendno) {
        this.fsendno = fsendno;
    }

    public int getFdikouid() {
        return fdikouid;
    }

    public void setFdikouid(int fdikouid) {
        this.fdikouid = fdikouid;
    }

    public bill(int fbillid, String fbillNo, int fstate, List<billProduct> billProducts, com.entity.billDeliveryAddress billDeliveryAddress, float ftotalAmount, int fdikouid, float fdikouAmount, float fhongbaoAmount, float fproductAmount, float fyunfei, float fvipamount, float ffinalAmount, float ftotalpricevip, int fuserID, String fbilldate, String fpaydate, String fsenddate, String farrivedate, String fsendno) {

        this.fbillid = fbillid;
        this.fbillNo = fbillNo;
        this.fstate = fstate;
        this.billProducts = billProducts;
        this.billDeliveryAddress = billDeliveryAddress;
        this.ftotalAmount = ftotalAmount;
        this.fdikouid = fdikouid;
        this.fdikouAmount = fdikouAmount;
        this.fhongbaoAmount = fhongbaoAmount;
        this.fproductAmount = fproductAmount;
        this.fyunfei = fyunfei;
        this.fvipamount = fvipamount;
        this.ffinalAmount = ffinalAmount;
        this.ftotalpricevip = ftotalpricevip;
        this.fuserID = fuserID;
        this.fbilldate = fbilldate;
        this.fpaydate = fpaydate;
        this.fsenddate = fsenddate;
        this.farrivedate = farrivedate;
        this.fsendno = fsendno;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFbillid() {
        return fbillid;
    }

    public void setFbillid(int fbillid) {
        this.fbillid = fbillid;
    }

    public String getFbillNo() {
        return fbillNo;
    }

    public void setFbillNo(String fbillNo) {
        this.fbillNo = fbillNo;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public List<billProduct> getBillProducts() {
        return billProducts;
    }

    public void setBillProducts(List<billProduct> billProducts) {
        this.billProducts = billProducts;
    }

    public com.entity.billDeliveryAddress getBillDeliveryAddress() {
        return billDeliveryAddress;
    }

    public void setBillDeliveryAddress(com.entity.billDeliveryAddress billDeliveryAddress) {
        this.billDeliveryAddress = billDeliveryAddress;
    }

    public float getFtotalAmount() {
        return ftotalAmount;
    }

    public void setFtotalAmount(float ftotalAmount) {
        this.ftotalAmount = ftotalAmount;
    }

    public float getFdikouAmount() {
        return fdikouAmount;
    }

    public void setFdikouAmount(float fdikouAmount) {
        this.fdikouAmount = fdikouAmount;
    }

    public float getFhongbaoAmount() {
        return fhongbaoAmount;
    }

    public void setFhongbaoAmount(float fhongbaoAmount) {
        this.fhongbaoAmount = fhongbaoAmount;
    }

    public float getFproductAmount() {
        return fproductAmount;
    }

    public void setFproductAmount(float fproductAmount) {
        this.fproductAmount = fproductAmount;
    }

    public float getFyunfei() {
        return fyunfei;
    }

    public void setFyunfei(float fyunfei) {
        this.fyunfei = fyunfei;
    }

    public float getFfinalAmount() {
        return ffinalAmount;
    }

    public void setFfinalAmount(float ffinalAmount) {
        this.ffinalAmount = ffinalAmount;
    }

    public int getFuserID() {
        return fuserID;
    }

    public void setFuserID(int fuserID) {
        this.fuserID = fuserID;
    }

    public String getFbilldate() {
        return fbilldate;
    }

    public void setFbilldate(String fbilldate) {
        this.fbilldate = fbilldate;
    }

    public String getFpaydate() {
        return fpaydate;
    }

    public void setFpaydate(String fpaydate) {
        this.fpaydate = fpaydate;
    }

    public String getFsenddate() {
        return fsenddate;
    }

    public void setFsenddate(String fsenddate) {
        this.fsenddate = fsenddate;
    }

    public String getFarrivedate() {
        return farrivedate;
    }

    public void setFarrivedate(String farrivedate) {
        this.farrivedate = farrivedate;
    }

    public float getFvipamount() {
        return fvipamount;
    }

    public void setFvipamount(float fvipamount) {
        this.fvipamount = fvipamount;
    }
}
