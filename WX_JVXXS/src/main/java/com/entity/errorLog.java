package com.entity;

import java.io.Serializable;

public class errorLog  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fdate;
    private String fphonemodel;
    private String fuserid;
    private String fflag;
    private String ferrormsg;

    public errorLog(int fid, String fdate, String fphonemodel, String fuserid, String fflag, String ferrormsg) {
        this.fid = fid;
        this.fdate = fdate;
        this.fphonemodel = fphonemodel;
        this.fuserid = fuserid;
        this.fflag = fflag;
        this.ferrormsg = ferrormsg;
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

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getFphonemodel() {
        return fphonemodel;
    }

    public void setFphonemodel(String fphonemodel) {
        this.fphonemodel = fphonemodel;
    }

    public String getFuserid() {
        return fuserid;
    }

    public void setFuserid(String fuserid) {
        this.fuserid = fuserid;
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag;
    }

    public String getFerrormsg() {
        return ferrormsg;
    }

    public void setFerrormsg(String ferrormsg) {
        this.ferrormsg = ferrormsg;
    }
}
