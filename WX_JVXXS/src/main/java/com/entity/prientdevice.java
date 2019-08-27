package com.entity;

import java.io.Serializable;

public class prientdevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fuuid;
    private String fname;

    public prientdevice() {
    }

    public prientdevice(int fid, String fuuid, String fname) {
        this.fid = fid;
        this.fuuid = fuuid;
        this.fname = fname;
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

    public String getFuuid() {
        return fuuid;
    }

    public void setFuuid(String fuuid) {
        this.fuuid = fuuid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
