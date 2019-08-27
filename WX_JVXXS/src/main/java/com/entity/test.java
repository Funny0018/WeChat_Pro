package com.entity;

import java.io.Serializable;

public class test implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fid;
    private String fname;

    public test() {
    }

    @Override
    public String toString() {
        return "test{" +
                "fid='" + fid + '\'' +
                ", fname='" + fname + '\'' +
                '}';
    }

    public test(String fid, String fname) {
        this.fid = fid;
        this.fname = fname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
