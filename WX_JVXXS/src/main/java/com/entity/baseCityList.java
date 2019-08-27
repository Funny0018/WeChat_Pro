package com.entity;

import java.io.Serializable;

public class baseCityList implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String ffullname;
    private String fname;
    private String flat;
    private String flng;

    public baseCityList() {
    }

    public baseCityList(int fid, String ffullname, String fname, String flat, String flng) {
        this.fid = fid;
        this.ffullname = ffullname;
        this.fname = fname;
        this.flat = flat;
        this.flng = flng;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFfullname() {
        return ffullname;
    }

    public void setFfullname(String ffullname) {
        this.ffullname = ffullname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFlng() {
        return flng;
    }

    public void setFlng(String flng) {
        this.flng = flng;
    }
}
