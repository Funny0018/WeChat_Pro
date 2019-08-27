package com.entity;

import java.io.Serializable;

public class producttype implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ftypeid;
    private String fname;
    private int fstate;
    private int fparentid;

    public producttype(int ftypeid, String fname, int fstate, int fparentid) {
        this.ftypeid = ftypeid;
        this.fname = fname;
        this.fstate = fstate;
        this.fparentid = fparentid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFtypeid() {
        return ftypeid;
    }

    public void setFtypeid(int ftypeid) {
        this.ftypeid = ftypeid;
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

    public int getFparentid() {
        return fparentid;
    }

    public void setFparentid(int fparentid) {
        this.fparentid = fparentid;
    }
}
