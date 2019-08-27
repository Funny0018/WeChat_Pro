package com.entity;

import java.io.Serializable;

public class producttag implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fname;
    private int fstate;

    public producttag() {
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

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public producttag(int fid, String fname, int fstate) {

        this.fid = fid;
        this.fname = fname;
        this.fstate = fstate;
    }
}
