package com.entity;

import java.io.Serializable;

public class vipBill implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fstate;
    private String fbillno;
    private int fuserid;
    private double famount;
    private String fdate;
    private int fviptype;
    private int frechargetype;
    private String ftype;
    private String fpaydate;

    public vipBill(){}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public String getFbillno() {
        return fbillno;
    }

    public void setFbillno(String fbillno) {
        this.fbillno = fbillno;
    }

    public int getFuserid() {
        return fuserid;
    }

    public void setFuserid(int fuserid) {
        this.fuserid = fuserid;
    }

    public double getFamount() {
        return famount;
    }

    public void setFamount(double famount) {
        this.famount = famount;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public int getFviptype() {
        return fviptype;
    }

    public void setFviptype(int fviptype) {
        this.fviptype = fviptype;
    }

    public int getFrechargetype() {
        return frechargetype;
    }

    public void setFrechargetype(int frechargetype) {
        this.frechargetype = frechargetype;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFpaydate() {
        return fpaydate;
    }

    public void setFpaydate(String fpaydate) {
        this.fpaydate = fpaydate;
    }

    public vipBill(int fid, int fstate, String fbillno, int fuserid, double famount, String fdate, int fviptype, int frechargetype, String ftype, String fpaydate) {

        this.fid = fid;
        this.fstate = fstate;
        this.fbillno = fbillno;
        this.fuserid = fuserid;
        this.famount = famount;
        this.fdate = fdate;
        this.fviptype = fviptype;
        this.frechargetype = frechargetype;
        this.ftype = ftype;
        this.fpaydate = fpaydate;
    }
}
