package com.entity;

import java.io.Serializable;

public class prientTask implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fbillno;
    private String ftaskno;
    private String fbindstate;
    private String fbindmassage;
    private String fstatestate;
    private String fstatemassage;
    private String fprientstate;
    private String fprientmassage;
    private String fprientdate;

    public prientTask() {

    }

    public prientTask(int fid, String fbillno, String ftaskno, String fbindstate, String fbindmassage, String fstatestate, String fstatemassage, String fprientstate, String fprientmassage, String fprientdate) {
        this.fid = fid;
        this.fbillno = fbillno;
        this.ftaskno = ftaskno;
        this.fbindstate = fbindstate;
        this.fbindmassage = fbindmassage;
        this.fstatestate = fstatestate;
        this.fstatemassage = fstatemassage;
        this.fprientstate = fprientstate;
        this.fprientmassage = fprientmassage;
        this.fprientdate = fprientdate;
    }

    public String getFprientdate() {
        return fprientdate;
    }

    public void setFprientdate(String fprientdate) {
        this.fprientdate = fprientdate;
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

    public String getFbillno() {
        return fbillno;
    }

    public void setFbillno(String fbillno) {
        this.fbillno = fbillno;
    }

    public String getFtaskno() {
        return ftaskno;
    }

    public void setFtaskno(String ftaskno) {
        this.ftaskno = ftaskno;
    }

    public String getFbindstate() {
        return fbindstate;
    }

    public void setFbindstate(String fbindstate) {
        this.fbindstate = fbindstate;
    }

    public String getFbindmassage() {
        return fbindmassage;
    }

    public void setFbindmassage(String fbindmassage) {
        this.fbindmassage = fbindmassage;
    }

    public String getFstatestate() {
        return fstatestate;
    }

    public void setFstatestate(String fstatestate) {
        this.fstatestate = fstatestate;
    }

    public String getFstatemassage() {
        return fstatemassage;
    }

    public void setFstatemassage(String fstatemassage) {
        this.fstatemassage = fstatemassage;
    }

    public String getFprientstate() {
        return fprientstate;
    }

    public void setFprientstate(String fprientstate) {
        this.fprientstate = fprientstate;
    }

    public String getFprientmassage() {
        return fprientmassage;
    }

    public void setFprientmassage(String fprientmassage) {
        this.fprientmassage = fprientmassage;
    }

}
