package com.entity;

import java.io.Serializable;

public class productImgs implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fproductid;
    private String fimageurl;
    private int fstate;
    private String fcreatedate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFproductid() {
        return fproductid;
    }

    public void setFproductid(int fproductid) {
        this.fproductid = fproductid;
    }

    public String getFimageurl() {
        return fimageurl;
    }

    public void setFimageurl(String fimageurl) {
        this.fimageurl = fimageurl;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public String getFcreatedate() {
        return fcreatedate;
    }

    public void setFcreatedate(String fcreatedate) {
        this.fcreatedate = fcreatedate;
    }

    public productImgs(int fid, int fproductid, String fimageurl, int fstate, String fcreatedate) {
        this.fid = fid;
        this.fproductid = fproductid;
        this.fimageurl = fimageurl;
        this.fstate = fstate;
        this.fcreatedate = fcreatedate;
    }
}
