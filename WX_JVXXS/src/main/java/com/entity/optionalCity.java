package com.entity;

import java.io.Serializable;

public class optionalCity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fid;
    private String fcityname;
    private int fstate;

    public optionalCity() {
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFcityname() {
        return fcityname;
    }

    public void setFcityname(String fcityname) {
        this.fcityname = fcityname;
    }

    public int getFstate() {
        return fstate;
    }

    public void setFstate(int fstate) {
        this.fstate = fstate;
    }

    public optionalCity(int fid, String fcityname, int fstate) {
        this.fid = fid;
        this.fcityname = fcityname;
        this.fstate = fstate;
    }
}
