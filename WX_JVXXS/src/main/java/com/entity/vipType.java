package com.entity;

import java.io.Serializable;

public class vipType implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fname;
    private String ffullname;
    private String fdiscount;
    private double foldprice;
    private double fprice;
    private int fstate;
    private String ftime;
    private int forder;

    public vipType(){}

    public int getForder() {
        return forder;
    }

    public void setForder(int forder) {
        this.forder = forder;
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

    public String getFfullname() {
        return ffullname;
    }

    public void setFfullname(String ffullname) {
        this.ffullname = ffullname;
    }

    public String getFdiscount() {
        return fdiscount;
    }

    public void setFdiscount(String fdiscount) {
        this.fdiscount = fdiscount;
    }

    public double getFoldprice() {
        return foldprice;
    }

    public void setFoldprice(double foldprice) {
        this.foldprice = foldprice;
    }

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public vipType(int fid, String fname, String ffullname, String fdiscount, double foldprice, double fprice, int fstate, String ftime, int forder) {
        this.fid = fid;
        this.fname = fname;
        this.ffullname = ffullname;
        this.fdiscount = fdiscount;
        this.foldprice = foldprice;
        this.fprice = fprice;
        this.fstate = fstate;
        this.ftime = ftime;
        this.forder = forder;
    }
}
