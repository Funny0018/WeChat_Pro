package com.entity;

import java.io.Serializable;

public class productVideo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private int fproductid;
    private String fvideourl;
    private int fstate;
    private String fcreatedate;

    public productVideo(){};

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

    public String getFvideourl() {
        return fvideourl;
    }

    public void setFvideourl(String fvideourl) {
        this.fvideourl = fvideourl;
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

    public productVideo(int fid, int fproductid, String fvideourl, int fstate, String fcreatedate) {

        this.fid = fid;
        this.fproductid = fproductid;
        this.fvideourl = fvideourl;
        this.fstate = fstate;
        this.fcreatedate = fcreatedate;
    }
}
