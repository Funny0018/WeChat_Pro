package com.entity;

import java.io.Serializable;

public class userCoupon implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fuserid;
    private String fname;
    private int ftype;
    private int fproductId;
    private double fprice;
    private double fminprice;
    private String fstartdate;
    private String fenddate;
    // -1：停用 0.未启用 1：正常 2:已使用 3：过期
    private int fstate;

    public userCoupon() {
    }

    public userCoupon(int fid, int fuserid, String fname, int ftype, int fproductId, double fprice, double fminprice, String fstartdate, String fenddate, int fstate) {
        this.fid = fid;
        this.fuserid = fuserid;
        this.fname = fname;
        this.ftype = ftype;
        this.fproductId = fproductId;
        this.fprice = fprice;
        this.fminprice = fminprice;
        this.fstartdate = fstartdate;
        this.fenddate = fenddate;
        this.fstate = fstate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getFminprice() {
        return fminprice;
    }

    public void setFminprice(double fminprice) {
        this.fminprice = fminprice;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFuserid() {
        return fuserid;
    }

    public void setFuserid(int fuserid) {
        this.fuserid = fuserid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getFtype() {
        return ftype;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    public int getFproductId() {
        return fproductId;
    }

    public void setFproductId(int fproductId) {
        this.fproductId = fproductId;
    }

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public String getFstartdate() {
        return fstartdate;
    }

    public void setFstartdate(String fstartdate) {
        this.fstartdate = fstartdate;
    }

    public String getFenddate() {
        return fenddate;
    }

    public void setFenddate(String fenddate) {
        this.fenddate = fenddate;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }
}
