package com.entity;

import java.io.Serializable;

public class backuser implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fname;
    private String fpassword;
    private String flogintime;
    private int fstate;

    public backuser(int fid, String fname, String fpassword, String flogintime, int fstate) {
        this.fid = fid;
        this.fname = fname;
        this.fpassword = fpassword;
        this.flogintime = flogintime;
        this.fstate = fstate;
    }

    public backuser() {
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

    public String getFpassword() {
        return fpassword;
    }

    public void setFpassword(String fpassword) {
        this.fpassword = fpassword;
    }

    public String getFlogintime() {
        return flogintime;
    }

    public void setFlogintime(String flogintime) {
        this.flogintime = flogintime;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }
}
