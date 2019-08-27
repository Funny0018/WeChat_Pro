package com.entity;

import java.io.Serializable;

public class shareLeader implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fcreatetime;
    private String fendtime;
    private int fuserid;
    private String fjoinuserid;
    private int ffirstuserid;
    private int fsecenduserid;
    private int fthirduserid;
    private int fstate;

    public shareLeader() {
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

    public String getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(String fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getFendtime() {
        return fendtime;
    }

    public void setFendtime(String fendtime) {
        this.fendtime = fendtime;
    }

    public int getFuserid() {
        return fuserid;
    }

    public void setFuserid(int fuserid) {
        this.fuserid = fuserid;
    }

    public String getFjoinuserid() {
        return fjoinuserid;
    }

    public void setFjoinuserid(String fjoinuserid) {
        this.fjoinuserid = fjoinuserid;
    }

    public int getFfirstuserid() {
        return ffirstuserid;
    }

    public void setFfirstuserid(int ffirstuserid) {
        this.ffirstuserid = ffirstuserid;
    }

    public int getFsecenduserid() {
        return fsecenduserid;
    }

    public void setFsecenduserid(int fsecenduserid) {
        this.fsecenduserid = fsecenduserid;
    }

    public int getFthirduserid() {
        return fthirduserid;
    }

    public void setFthirduserid(int fthirduserid) {
        this.fthirduserid = fthirduserid;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public shareLeader(int fid, String fcreatetime, String fendtime, int fuserid, String fjoinuserid, int ffirstuserid, int fsecenduserid, int fthirduserid, int fstate) {
        this.fid = fid;
        this.fcreatetime = fcreatetime;
        this.fendtime = fendtime;
        this.fuserid = fuserid;
        this.fjoinuserid = fjoinuserid;
        this.ffirstuserid = ffirstuserid;
        this.fsecenduserid = fsecenduserid;
        this.fthirduserid = fthirduserid;
        this.fstate = fstate;
    }
}
