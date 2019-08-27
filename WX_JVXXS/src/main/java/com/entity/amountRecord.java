package com.entity;

import java.io.Serializable;

public class amountRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fdate;
    private int fuserid;
    private double famount;
    // 0：退款，1:零售单付款，2：会员办理，3:会员充值,4:会员抵扣
    private int fstate;
    private String fbillno;
    private String fwxno;
    private String fattech;
    private String fbody;
    private String fbilltype;
    private double fpayamount;

    public amountRecord() {
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
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

    public String getFwxno() {
        return fwxno;
    }

    public void setFwxno(String fwxno) {
        this.fwxno = fwxno;
    }

    public String getFattech() {
        return fattech;
    }

    public void setFattech(String fattech) {
        this.fattech = fattech;
    }

    public String getFbody() {
        return fbody;
    }

    public void setFbody(String fbody) {
        this.fbody = fbody;
    }

    public String getFbilltype() {
        return fbilltype;
    }

    public void setFbilltype(String fbilltype) {
        this.fbilltype = fbilltype;
    }

    public double getFpayamount() {
        return fpayamount;
    }

    public void setFpayamount(double fpayamount) {
        this.fpayamount = fpayamount;
    }

    public amountRecord(int fid, String fdate, int fuserid, double famount, int fstate, String fbillno, String fwxno, String fattech, String fbody, String fbilltype, double fpayamount) {
        this.fid = fid;
        this.fdate = fdate;
        this.fuserid = fuserid;
        this.famount = famount;
        this.fstate = fstate;
        this.fbillno = fbillno;
        this.fwxno = fwxno;
        this.fattech = fattech;
        this.fbody = fbody;
        this.fbilltype = fbilltype;
        this.fpayamount = fpayamount;
    }
}
