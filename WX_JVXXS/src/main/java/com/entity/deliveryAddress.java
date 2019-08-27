package com.entity;

import java.io.Serializable;

public class deliveryAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fname;
    private String fphone;
    private String fcity;
    private String faddress;
    private String fbuildno;
    private int fuserid;
    private int fisdefault;

    public deliveryAddress() {
    }

    public deliveryAddress(int fid, String fname, String fphone, String fcity, String faddress, String fbuildno, int fuserid, int fisdefault) {
        this.fid = fid;
        this.fname = fname;
        this.fphone = fphone;
        this.fcity = fcity;
        this.faddress = faddress;
        this.fbuildno = fbuildno;
        this.fuserid = fuserid;
        this.fisdefault = fisdefault;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFcity() {
        return fcity;
    }

    public void setFcity(String fcity) {
        this.fcity = fcity;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getFbuildno() {
        return fbuildno;
    }

    public void setFbuildno(String fbuildno) {
        this.fbuildno = fbuildno;
    }

    public int getFuserid() {
        return fuserid;
    }

    public void setFuserid(int fuserid) {
        this.fuserid = fuserid;
    }

    public int getFisdefault() {
        return fisdefault;
    }

    public void setFisdefault(int fisdefault) {
        this.fisdefault = fisdefault;
    }
}
